package nl.tudelft.instrumentation.fuzzing;

import java.lang.reflect.Array;
import java.util.*;

import com.github.javaparser.printer.concretesyntaxmodel.CsmConditional.Condition;

import org.checkerframework.checker.units.qual.C;

/**
 * You should write your own solution using this class.
 */
public class FuzzingLab {
        private static final int N_BRANCHES_PER_LINE = 2;
        private static final float FALSE_BRANCH_PENALTY = 1.2f;

        static Random r = new Random();
        static List<String> currentTrace;
        static int traceLength = 10;
        static boolean isFinished = false;
        static boolean justAfterReset = false;

        static HashMap<Integer, Branch> branch_line_numbers = new HashMap<Integer, Branch>();
        static HashSet<Integer> completed_branches = new HashSet<Integer>();
        static HashSet<String> error_codes = new HashSet<>();
        static HashSet<List<String>> completed_traces = new HashSet<List<String>>();

        static Trace longest_trace = new Trace(new ArrayList<String>());
        private static Trace currentTraceTrace;

        static void initialize(String[] inputSymbols) {
                // Initialise a random trace from the input symbols of the problem.
                currentTrace = generateRandomTrace(inputSymbols);
        }

        /**
         * Calculates the distance to a branche given a condition.
         * 
         * @param condition the given (partial) condition of a branch.
         * @return a floating number containing the score of a condition.
         */
        static float distanceBranch(MyVar condition) {
                switch (condition.type) {
                        case BOOL:
                                if (condition.value)
                                        return 0.0f;
                                else
                                        return 1.0f;
                        case UNARY:
                                switch (condition.operator) {
                                        case "!": // !p1 : d = 1 - d(p1)
                                                float d_left = distanceBranch(condition.left);
                                                return 1 - d_left / (d_left + 1);
                                        default:
                                                System.out.println("Unknown unary operaor: " + condition.operator);
                                                break;
                                }
                                break;
                        case BINARY:
                                float d_left, d_right;

                                switch (condition.operator) {
                                        case "&&": // p1 & p2 : d = d(p1) + d(p2)
                                                d_left = distanceBranch(condition.left);
                                                d_right = distanceBranch(condition.right);
                                                return (d_left / (d_left + 1)) + (d_right / (d_right + 1));
                                        case "||": // p1 | p2 : d = min(d(p1), d(p2))
                                                d_left = distanceBranch(condition.left);
                                                d_right = distanceBranch(condition.right);
                                                return Math.min(d_left / (d_left + 1), d_right / (d_right + 1));
                                        case "XOR": // p1 XOR p2 : d = min(d(p1) + d(!p2), d(!p1) + d(p2))
                                                d_left = distanceBranch(condition.left);
                                                d_right = distanceBranch(condition.right);
                                                return Math.min(d_left / (d_left + 1) + 1 - d_right / (d_right + 1),
                                                                d_right / (d_right + 1) + 1 - d_left / (d_left + 1));
                                        case "==": // a == b : d = abs(a-b)
                                                switch (condition.left.type) {
                                                        case INT:
                                                                return Math.abs(condition.left.int_value
                                                                                - condition.right.int_value);
                                                        case STRING:
                                                                for (int i = 0; i < condition.left.str_value
                                                                                .length(); i++) {
                                                                        if (condition.left.str_value.charAt(
                                                                                        i) != condition.right.str_value
                                                                                                        .charAt(i))
                                                                                return Math.abs(condition.left.str_value
                                                                                                .charAt(i)
                                                                                                - condition.right.str_value
                                                                                                                .charAt(i));
                                                                }
                                                                return Math.abs(condition.left.str_value.charAt(0) - condition.right.str_value.charAt(0));
                                                        default:
                                                                break;
                                                }
                                        case "!=": // a != b : d = {0 if a !=b, 1 otherwise}
                                                switch (condition.left.type) {
                                                        case INT:
                                                                if (condition.left.int_value != condition.right.int_value)
                                                                        return 0.0f;
                                                                else
                                                                        return 1.0f;
                                                        case STRING:
                                                                if (!condition.left.str_value
                                                                                .equals(condition.right.str_value))
                                                                        return 0.0f;
                                                                else
                                                                        return 1.0f;
                                                        default:
                                                                break;
                                                }
                                        case "<": // a < b : d = {0 if a < b; a-b + K otherwise}
                                                if (condition.left.int_value < condition.right.int_value)
                                                        return 0.0f;
                                                else
                                                        return condition.left.int_value - condition.right.int_value
                                                                        + 0.5f;
                                        case "<=": // a <= b : d = {0 if a <= b; a-b otherwise}
                                                if (condition.left.int_value <= condition.right.int_value)
                                                        return 0.0f;
                                                else
                                                        return condition.left.int_value - condition.right.int_value;
                                        case ">": // a < b : d = {0 if a > b; b-a + K otherwise}
                                                if (condition.left.int_value > condition.right.int_value)
                                                        return 0.0f;
                                                else
                                                        return condition.right.int_value - condition.left.int_value
                                                                        + 0.5f;
                                        case ">=": // a <= b : d = {0 if a >= b; b-a otherwise}
                                                if (condition.left.int_value >= condition.right.int_value)
                                                        return 0.0f;
                                                else
                                                        return condition.right.int_value - condition.left.int_value;
                                        default:
                                                System.out.println("Unknown binary operaor: " + condition.operator);
                                                break;
                                }
                                break;
                        default:
                                break;
                }
                return 999.0f;
        }

        /**
         * Processes the newly encountered branch
         * 
         * @param condition of the if statement
         * @param value     of the resulting condition
         * @param line_nr   where the branch was encountered
         */
        static void encounteredNewBranch(MyVar condition, boolean value, int line_nr) {

                if (!completed_branches.contains(line_nr)) {
                        justAfterReset = false;

                        float branchDistance = distanceBranch(condition);
                        branchDistance = branchDistance / (branchDistance + 1.0f);

                        Branch t_branch = new Branch(condition, value);

                        if (branch_line_numbers.containsKey(line_nr)) {
                                t_branch = branch_line_numbers.get(line_nr);

                                if (value && !t_branch.isExecutedOnTrue) {
                                        t_branch.branchDistanceTrue = branchDistance;
                                        t_branch.branchDistanceFalse = 1 - branchDistance;
                                        t_branch.isExecutedOnTrue = true;
                                } else if (!value && !t_branch.isExecutedOnFalse) {
                                        t_branch.isExecutedOnFalse = true;
                                }

                                if (t_branch.isExecutedOnTrue && t_branch.isExecutedOnFalse) {
                                        branch_line_numbers.remove(line_nr);
                                        completed_branches.add(line_nr);
                                } else {
                                        branch_line_numbers.replace(line_nr, t_branch);
                                }
                        } else {
                                if (value) {
                                        t_branch.branchDistanceTrue = branchDistance;
                                        t_branch.branchDistanceFalse = 1.0f - branchDistance;
                                } else {
                                        t_branch.branchDistanceTrue = 1.0f;
                                        t_branch.branchDistanceFalse = 1.0f;
                                }

                                branch_line_numbers.put(line_nr, t_branch);

                                // if new globaly it will also be new to local trace
                                currentTraceTrace.d_branch_sum += t_branch.branchDistanceTrue;
                                currentTraceTrace.d_branch_sum += t_branch.branchDistanceFalse;
                        }

                }

                // Add new branch to count
                currentTraceTrace.number_of_branches += 1;
        }

        /**
         * Method for fuzzing new inputs for a program.
         *
         * @param inputSymbols the inputSymbols to fuzz from.
         * @return a fuzzed sequence
         */
        static List<String> fuzz(String[] inputSymbols) {
                /*
                 * Add here your code for fuzzing a new sequence for the RERS problem.
                 * You can guide your fuzzer to fuzz "smart" input sequences to cover
                 * more branches. Right now we just generate a complete random sequence
                 * using the given input symbols. Please change it to your own code.
                 */
                return generateRandomTrace(inputSymbols);
        }

        /**
         * Generate a random trace from an array of symbols.
         * 
         * @param symbols the symbols from which a trace should be generated from.
         * @return a random trace that is generated from the given symbols.
         */
        static List<String> generateRandomTrace(String[] symbols) {
                ArrayList<String> trace = new ArrayList<>();
                for (int i = 0; i < traceLength; i++) {
                        trace.add(symbols[r.nextInt(symbols.length)]);
                }

                if (completed_traces.contains(trace))
                        return generateRandomTrace(symbols);
                
                return trace;
        }

        /**
         * Update the current trace using the collected information.
         * 
         * @param symbols        the symbols from which a trace should be generated
         *                       from.
         * @param currentTrace2  the current trace used in the previous round.
         * @param closest_branch the unexplored branch that is closest to ours.
         * @return the new trace to be used for the next round of fuzzing.
         */
        static List<String> updateTrace(String[] symbols, List<String> currentTrace2, Branch closest_branch) {
                ArrayList<String> new_trace = new ArrayList<>(currentTrace2);

                List<String> condition_contains_symbol = new ArrayList<String>();
                int changed_at = -1;

                // Get a list of all symbols within the condition
                for (int i = 0; i < symbols.length; i++) {
                        if (closest_branch.condition.toString().contains(symbols[i])) {
                                condition_contains_symbol.add(symbols[i]);
                        }
                }

                if (condition_contains_symbol.isEmpty()) {
                        new_trace.set(r.nextInt(traceLength), symbols[r.nextInt(symbols.length)]);
                        return new_trace;
                } else {
                        int chosen_symbol = r.nextInt(condition_contains_symbol.size());

                        for (int i = 0; i < traceLength; i++) {
                                if (new_trace.get(i) == condition_contains_symbol.get(chosen_symbol)) {
                                        new_trace.set(i, symbols[r.nextInt(symbols.length)]);
                                        changed_at = i;
                                        break;
                                }
                        }

                        if (changed_at == -1) {
                                new_trace.set(r.nextInt(traceLength), symbols[r.nextInt(symbols.length)]);
                        }
                }
                if (completed_traces.contains(new_trace))
                        updateTrace(DistanceTracker.inputSymbols, currentTrace, closest_branch);

                return new_trace;
        }

        /**
         * Calculates the closest not yet fully explored branch.
         * 
         * @return the closest branch.
         */
        static Branch getClosestBranch() {
                Branch closest_branch = (Branch) branch_line_numbers.values().toArray()[0];

                for (Branch branch : branch_line_numbers.values()) {

                        if (branch.isExecutedOnTrue && branch.isExecutedOnFalse) {
                                continue;
                        }

                        // We wish to prefer branches of which the true value has not yet been explored
                        if ((branch.branchDistanceTrue + branch.branchDistanceFalse
                                        * FALSE_BRANCH_PENALTY) < (closest_branch.branchDistanceTrue
                                                        + closest_branch.branchDistanceFalse * FALSE_BRANCH_PENALTY)) {
                                closest_branch = branch;
                        }
                }

                return closest_branch;
        }

        /**
         * Counts the number of paths not yet explored
         */
        static int getNumUntraveledPaths() {
                int n_paths_untraveled = 0;

                for (Map.Entry<Integer, Branch> branch : branch_line_numbers.entrySet()) {

                        if (!branch.getValue().isExecutedOnTrue || !branch.getValue().isExecutedOnFalse) {
                                n_paths_untraveled += 1;
                        }
                }

                return n_paths_untraveled;
        }

        static void run() {
                initialize(DistanceTracker.inputSymbols);
                completed_traces.add(currentTrace);
                currentTraceTrace = new Trace(currentTrace);
                DistanceTracker.runNextFuzzedSequence(currentTrace.toArray(new String[0]));

                int round = 1;
                int last_numm_branches = -1;

                // Place here your code to guide your fuzzer with its search.
                while (!isFinished) {
                        if (branch_line_numbers.size() != 0) {
                                Branch closest_branch = getClosestBranch();
                                currentTrace = updateTrace(DistanceTracker.inputSymbols, currentTrace, closest_branch);
                        } else {
                                currentTrace = generateRandomTrace(DistanceTracker.inputSymbols);
                                currentTraceTrace = new Trace(currentTrace);
                        }
                        // add trace to count the number of branches it encounters
                        currentTraceTrace = new Trace(currentTrace);
                        completed_traces.add(currentTrace);

                        // after 8000 rounds reset the already completed traces hashset so it doesn't grow too large
                        if (round % 8000 == 0) {
                                int t_np = getNumUntraveledPaths();

                                if (t_np == last_numm_branches) {
                                        printNrUniqueBranches();
                                        System.out.println("\nnr of branches: " + branch_line_numbers.size());
                                        System.out.println("nr of completed_branches: " + (completed_branches.size() * N_BRANCHES_PER_LINE));
                                        System.out.println("nr of completed_traces: " + completed_traces.size());
                                        resetFuzzer();
                                        round = 1;
                                }
                                last_numm_branches = t_np;
                        }

                        // after 2000 rounds reset the already completed traces hashset so it doesn't grow too large
                        if (round % 2002 == 0) {
                                completed_traces.clear();
                        }

                        try {
                                DistanceTracker.runNextFuzzedSequence(currentTrace.toArray(new String[0]));
                                Thread.sleep(0);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                        updateLongestTrace();

                        if (!justAfterReset && getNumUntraveledPaths() == 0) {
                                isFinished = true;
                        }
                        round++;
                        printExitInfo();
                }

                printExitInfo();
                System.exit(0);
        }

        private static void resetFuzzer() {
                System.out.println("resetting fuzzer...");
                currentTrace = generateRandomTrace(DistanceTracker.inputSymbols);
                currentTraceTrace = new Trace(currentTrace);
                // branch_line_numbers.clear();
                HashMap<Integer, Branch> t_b_l_nr = new HashMap<Integer, Branch>();
                for (Map.Entry<Integer, Branch> entry : branch_line_numbers.entrySet()) {
                        Branch t_b = entry.getValue();
                        t_b.branchDistanceTrue = 99.0f;
                        t_b.branchDistanceFalse = 99.0f;
                        t_b_l_nr.put(entry.getKey(), t_b);
                }
                branch_line_numbers = t_b_l_nr;
                justAfterReset = true;
        }

        /**
         * How many unique branches were you able to visit on the problems you tested?
         */
        private static void printNrUniqueBranches() {
                int n_branches = (completed_branches.size() * N_BRANCHES_PER_LINE) + branch_line_numbers.values().size();
                System.out.println("number of total encountered branches: " + n_branches);
        }

        /**
         * Print the list of unique error codes found and their number
         */
        private static void printUniqueErrorCodes() {
                System.out.println("The error codes found are:");
                error_codes.forEach((val) -> {
                        System.out.println(val);
                });
                System.out.println("Number of total error codes is: " + error_codes.size());
        }

        /**
         * Using which set of input traces did you manage to visit the greatest number
         * of branches?
         */
        private static void updateLongestTrace() {
                if (currentTraceTrace.number_of_branches > longest_trace.number_of_branches) {
                        longest_trace = currentTraceTrace;
                }
        }

        /**
         * List five input traces that achieved the lowest sum of branch distances.
         */
        private static void printLowBranchDistSumTraces() {
                // HashMap<Float, List<String>> distance_sum_traces = new HashMap<Float,
                // List<String>>();

                // for (Trace trace : traces) {
                // float branch_distance_total = 0;

                // for (Branch branch : trace.branches.values()) {
                // branch_distance_total += branch.branchDistanceTrue;
                // branch_distance_total += branch.branchDistanceFalse;
                // }

                // distance_sum_traces.put(branch_distance_total, trace.trace);
                // }
                // List<Float> d_sums = new ArrayList<Float>(distance_sum_traces.keySet());
                // Collections.sort(d_sums);

                // System.out.println("5 lowest scoring traces when compared their branch
                // distances");
                // int max_sum_traces = 0;
                // if (distance_sum_traces.size() >= 5)
                // max_sum_traces = 5;
                // else
                // max_sum_traces = distance_sum_traces.size();

                // for (int i = 0; i < max_sum_traces; i++) {
                // System.out.println(i + ": " + distance_sum_traces.get(d_sums.get(i)));
                // }
        }

        /**
         * Print all the necessary information at the end of execution
         */
        public static void printExitInfo() {
                System.out.println("number of untraveled paths: " + getNumUntraveledPaths());
                printNrUniqueBranches();
                System.out.println("longest trace is " + longest_trace.trace + " with "
                                + longest_trace.number_of_branches + " unique branch encounters");
                printUniqueErrorCodes();
                printLowBranchDistSumTraces();
        }

        /**
         * Method that is used for catching the output from standard out.
         * You should write your own logic here.
         * 
         * @param out the string that has been outputted in the standard out.
         */
        public static void output(String out) {
                if (out.contains("error")) {
                        error_codes.add(out);
                }
                // System.out.println(out);
        }

        /**
         * Method that is used for catching the output from standard out.
         * You should write your own logic here.
         * 
         * @param out the string that has been outputted in the standard out.
         */
        public static void debug(String out) {
                System.out.println(out);
        }
}