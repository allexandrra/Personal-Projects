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
        static int nrTotalPermutations = 50;
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

                // the method is run only for branches not fully encountered (both true and false branches)
                if (!completed_branches.contains(line_nr)) {

                        // calculate and normalize branch distance for current branch
                        float branchDistance = distanceBranch(condition);
                        branchDistance = branchDistance / (branchDistance + 1.0f);

                        // define a new branch
                        Branch t_branch = new Branch(condition, value, !value);

                        // if branch was already encountered check whether the true ale the false branch was added
                        // if true, then update the branch distancec for both true and false branches
                        // if false, mark the false branch as encountered
                        if (branch_line_numbers.containsKey(line_nr)) {
                                t_branch = branch_line_numbers.get(line_nr);

                                if (value && !t_branch.isExecutedOnTrue) {
                                        t_branch.branchDistanceTrue = branchDistance;
                                        t_branch.branchDistanceFalse = 1 - branchDistance;
                                        t_branch.isExecutedOnTrue = true;
                                } else if (!value && !t_branch.isExecutedOnFalse) {
                                        t_branch.isExecutedOnFalse = true;
                                }

                                // if both branched were encountered, then add them to completed
                                if (t_branch.isExecutedOnTrue && t_branch.isExecutedOnFalse) {
                                        branch_line_numbers.remove(line_nr);
                                        completed_branches.add(line_nr);
                                } else {
                                        branch_line_numbers.replace(line_nr, t_branch);
                                }
                        } else {
                                // if thr true branch is the first one found, add branch distances, if not, add maximum values (1)
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
         * Changes a symbol inside the current trace with a random one
         * @param inputSymbols
         * @param trace
         * @return
         */
        static List<String> changeSymbol(String[] inputSymbols, List<String> trace) {
                List<String> permutedTrace = new ArrayList<String>(trace);

                if (permutedTrace.isEmpty()) {
                        System.out.println("List was not instantiated!");
                        return null;
                }

                int position = r.nextInt(trace.size());
                boolean isChanged = false;

                while (!isChanged) {
                        int symbolAt = r.nextInt(traceLength);
                        if (position != symbolAt) {
                                permutedTrace.set(position, inputSymbols[symbolAt]);
                                isChanged = true;
                        }
                }

                return permutedTrace;
        }

        /**
         * Adds a new random symbol at the end of the current trace
         * @param inputSymbols
         * @param trace
         * @return
         */
        static List<String> addSymbol(String[] inputSymbols, List<String> trace) {
                List<String> permutedTrace = new ArrayList<String>(trace);

                if (permutedTrace.isEmpty()) {
                        System.out.println("List was not instantiated!");
                        return null;
                }

                int symbolAt = r.nextInt(traceLength);
                permutedTrace.add(inputSymbols[symbolAt]);

                return permutedTrace;
        }

        /**
         * Deletes a randon symbol from the current trace
         * @param inputSymbols
         * @param trace
         * @return
         */
        static List<String> deleteSymbol(String[] inputSymbols, List<String> trace) {
                List<String> permutedTrace = new ArrayList<String>(trace);

                if (permutedTrace.isEmpty()) {
                        System.out.println("List was not instantiated!");
                        return null;
                }

                if (trace.size() == 1)
                        return trace;

                int symbolAt = r.nextInt(trace.size());
                permutedTrace.remove(symbolAt);

                return permutedTrace;
        }

        /**
         * Makes a list of permutations for choosing a new trace starting with the current trace
         * @param inputSymbols
         * @param trace
         * @param nrPermutations
         * @return
         */
        static List<List<String>> permuteTrace(String[] inputSymbols, List<String> trace, int nrPermutations) {
                List<List<String>> permutedTraces = new ArrayList<>();
                List<String> permutedTrace = new ArrayList<String>();

                for (int i = 0; i < nrPermutations; i++) {
                        int permutationType = r.nextInt(3);
                        switch (permutationType) {
                                case 0:
                                        permutedTrace = changeSymbol(inputSymbols, trace);
                                        break;
                                case 1:
                                        permutedTrace = addSymbol(inputSymbols, trace);
                                        break;
                                case 2:
                                        permutedTrace = deleteSymbol(inputSymbols, trace);
                                        break;
                                default:
                                        System.out.println("Incorrect permutation type.");
                                        break;
                        }

                        permutedTraces.add(permutedTrace);
                }

                return permutedTraces;
        }

        /**
         * Method for fuzzing new inputs for a program.
         *
         * @param inputSymbols the inputSymbols to fuzz from.
         * @return a fuzzed sequence
         */

        static List<String> fuzz(String[] inputSymbols, List<String> trace) {
                /*
                 * A total of 10 permutations are generated from the current trace.
                 * For every permuted trace, the branch distance is calculated and compared with
                 * the one of the current trace.
                 * If there is one sum lower, that will become the next trace, if there are
                 * none, a random one is selected.
                 */
                int nrPermutations = 10;
                float initialBranchDistanceSum = 0.0f;
                int minBranchDistance = -1;
                float[] permutedBranchDistances = new float[nrPermutations];
                List<List<String>> permutedTraces;

                // the sum of branch distances for all permuted branches
                for (int i = 0; i < nrPermutations; i++)
                        permutedBranchDistances[i] = 0.0f;

                for (Map.Entry<Integer, Branch> branch : branch_line_numbers.entrySet()) {
                        if (branch.getValue().isExecutedOnTrue) {
                                initialBranchDistanceSum += branch.getValue().branchDistanceTrue;
                        }
                }

                permutedTraces = permuteTrace(inputSymbols, trace, nrPermutations);

                for (int i = 0; i < nrPermutations; i++) {
                        if (!branch_line_numbers.isEmpty()) {
                                branch_line_numbers.clear();
                        }
                        if (completed_traces.contains(permutedTraces.get(i)))
                                continue;

                        DistanceTracker.runNextFuzzedSequence(permutedTraces.get(i).toArray(new String[0]));
                        for (Map.Entry<Integer, Branch> branch : branch_line_numbers.entrySet()) {
                                if (branch.getValue().isExecutedOnTrue) {
                                        permutedBranchDistances[i] += branch.getValue().branchDistanceTrue;
                                }
                        }
                }

                for (int i = 0; i < nrPermutations; i++) {
                        if (permutedBranchDistances[i] != 0.0f && permutedBranchDistances[i] < initialBranchDistanceSum)
                                minBranchDistance = i;
                }

                if (minBranchDistance == -1) {
                        minBranchDistance = r.nextInt(nrPermutations);
                }

                return permutedTraces.get(minBranchDistance);
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
                        generateRandomTrace(symbols);

                return trace;
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

                updateLongestTrace();
                int round = 0;

                // Place here your code to guide your fuzzer with its search.
                while (!isFinished) {
                        // branch search using hill climbing
                        Map<Integer, Branch> aux_branch_line_numbers = new HashMap<>();
                        HashSet<Integer> aux_completed_branches = new HashSet<>();

                        // after nrTotalPermutations permutations reset currentTrace to a random one
                        if (round < nrTotalPermutations) {
                                // save current state and retake it after getting the new trace from the permuted traces
                                branch_line_numbers.forEach((k, v) -> {
                                        Branch br = v.clone();
                                        aux_branch_line_numbers.put(k, br);
                                });
                                completed_branches.forEach((val) -> {
                                        aux_completed_branches.add(val);
                                });

                                currentTrace = fuzz(DistanceTracker.inputSymbols, currentTrace);

                                branch_line_numbers.clear();
                                branch_line_numbers.putAll(aux_branch_line_numbers);
                                completed_branches.clear();
                                completed_branches.addAll(aux_completed_branches);
                        } else {
                                currentTrace = generateRandomTrace(DistanceTracker.inputSymbols);
                                round = 0;
                        }

                        // add trace to count the number of branches it encounters
                        currentTraceTrace = new Trace(currentTrace);
                        completed_traces.add(currentTrace);

                        try {
                                DistanceTracker.runNextFuzzedSequence(currentTrace.toArray(new String[0]));
                                Thread.sleep(0);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                        updateLongestTrace();

                        if (getNumUntraveledPaths() == 0) {
                                isFinished = true;
                        }
                        round++;
                        printExitInfo();
                }

                printExitInfo();
                System.exit(0);
        }

        /**
         * How many unique branches were you able to visit on the problems you tested?
         */
        private static void printNrUniqueBranches() {
                int n_branches = (completed_branches.size() * N_BRANCHES_PER_LINE)
                                + branch_line_numbers.values().size();
                System.out.println("number of total encountered branches: " + n_branches);
        }

        /**
         * How many unique error codes were found and which are they?
         */
        private static void printUniqueErrorCodes() {
                System.out.println("The uniques error codes found:");
                error_codes.forEach((error) -> {
                        System.out.println(error);
                });
                System.out.println("In total unique error codes: " + error_codes.size());
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
         * Print all required information about a fuzzer run
         */
        public static void printExitInfo() {
                System.out.println("number of untraveled paths: " + getNumUntraveledPaths());
                printNrUniqueBranches();
                System.out.println("longest trace is " + longest_trace.trace + " with "
                                + longest_trace.number_of_branches + " unique branch encounters");
                printUniqueErrorCodes();
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
                //System.out.println(out);
        }

        public static void debug(String out) {
                System.out.println(out);
        }
}