package nl.tudelft.instrumentation.symbolic;

import java.util.*;
import com.microsoft.z3.*;

import org.checkerframework.common.reflection.qual.NewInstance;

import nl.tudelft.instrumentation.fuzzing.DistanceTracker;

import java.util.Random;
import java.beans.Expression;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * You should write your solution using this class.
 */
public class SymbolicExecutionLab {

    static Random r = new Random();
    static Boolean isFinished = false;
    static List<String> currentTrace;
    static int traceLength = 10;
    static int count = 0;
    static Boolean newBranch = false;
    static HashMap<Integer, Branch> branch_line_numbers = new HashMap<Integer, Branch>(); // retains branches covered and their status
    static HashMap<List<String>, Integer> traces = new HashMap<>(); // retains all uniwue traces given by the solver
    static BoolExpr new_branch; // new branch generated
    static HashSet<String> error_codes = new HashSet<>(); // list of error codes found


    /**
     * Initialize the first set of inputs to a randomized list
     * @param inputSymbols
     */
    static void initialize(String[] inputSymbols) {
        // Initialise a random trace from the input symbols of the problem.
        currentTrace = generateRandomTrace(inputSymbols);
    }

    /**
     * Create a new Z3 variable and add it to the model
     * @param name
     * @param value
     * @param s
     * @return
     */
    static MyVar createVar(String name, Expr value, Sort s) {
        /**
         * Create var, assign value and add to path constraint.
         * We show how to do it for creating new symbols, please
         * add similar steps to the functions below in order to
         * obtain a path constraint.
         */
        Context c = PathTracker.ctx;
        Expr z3var = c.mkConst(c.mkSymbol(name + "_" + PathTracker.z3counter++), s);
        PathTracker.z3model = c.mkAnd(c.mkEq(z3var, value), PathTracker.z3model);
        return new MyVar(z3var, name);
    }

    /**
     * Create input by creating a new var and adding it in the inputs list
     * @param name
     * @param value
     * @param s
     * @return
     */
    static MyVar createInput(String name, Expr value, Sort s) {
        Context c = PathTracker.ctx;
        BoolExpr constraint = c.mkFalse();

        for (String input : PathTracker.inputSymbols) {
            constraint = c.mkOr(c.mkEq(value, c.mkString(input)), constraint);
        }

        PathTracker.z3model = c.mkAnd(constraint, PathTracker.z3model);

        MyVar newVar = createVar(name, value, s);
        PathTracker.inputs.add(newVar);

        return newVar;
    }

    static MyVar createBoolExpr(BoolExpr var, String operator) {
        if (operator.equals("!")) {
            return new MyVar(PathTracker.ctx.mkNot(var));
        }

        System.out.println("createBoolExpr_U: unknown operater " + operator);
        return new MyVar(PathTracker.ctx.mkFalse());
    }

    static MyVar createBoolExpr(BoolExpr left_var, BoolExpr right_var, String operator) {
        switch (operator) {
            case "&":
            case "&&":
                return new MyVar(PathTracker.ctx.mkAnd(left_var, right_var));
            case "|":
            case "||":
                return new MyVar(PathTracker.ctx.mkOr(left_var, right_var));
            default:
                System.out.println(
                        "createBoolExpr_B: unknown operater " + operator + " in " + left_var + " and " + right_var);
                return new MyVar(PathTracker.ctx.mkFalse());
        }
    }

    static MyVar createIntExpr(IntExpr var, String operator) {
        if (operator.equals("+")) {
            return new MyVar(var);
        }

        if (operator.equals("-")) {
            return new MyVar(PathTracker.ctx.mkUnaryMinus(var));
        }

        System.out.println("createIntExpr_U: unknown operater " + operator + " in " + var);
        return new MyVar(PathTracker.ctx.mkFalse());
    }

    static MyVar createIntExpr(IntExpr left_var, IntExpr right_var, String operator) {
        switch (operator) {
            case "+":
                return new MyVar(PathTracker.ctx.mkAdd(left_var, right_var));
            case "-":
                return new MyVar(PathTracker.ctx.mkSub(left_var, right_var));
            case "/":
                return new MyVar(PathTracker.ctx.mkDiv(left_var, right_var));
            case "*":
                return new MyVar(PathTracker.ctx.mkMul(left_var, right_var));
            case "%":
                return new MyVar(PathTracker.ctx.mkMod(left_var, right_var));
            case "^":
                return new MyVar(PathTracker.ctx.mkPower(left_var, right_var));
            case "==":
                return new MyVar(PathTracker.ctx.mkEq(left_var, right_var));
            case "<":
                return new MyVar(PathTracker.ctx.mkLt(left_var, right_var));
            case "<=":
                return new MyVar(PathTracker.ctx.mkLe(left_var, right_var));
            case ">":
                return new MyVar(PathTracker.ctx.mkGt(left_var, right_var));
            case ">=":
                return new MyVar(PathTracker.ctx.mkGe(left_var, right_var));
            default:
                System.out.println("createIntExpr_B: unknown operater " + operator + " in " + left_var + " and " + right_var);
                return new MyVar(PathTracker.ctx.mkFalse());
        }
    }

    static MyVar createStringExpr(SeqExpr left_var, SeqExpr right_var, String operator) {
        // We only support String.equals
        if(operator.equals("==")) {
            return new MyVar(PathTracker.ctx.mkEq(left_var, right_var));
        }

        System.out.println("createStringExpr: unknown operater " + operator + " in " + left_var + " and " + right_var);
        return new MyVar(PathTracker.ctx.mkFalse());
    }

    static void assign(MyVar var, String name, Expr value, Sort s) {
        MyVar newVar = createVar(name, value, s);
        
        var.z3var = newVar.z3var;
        var.name = newVar.name;
    }

    /**
     * Method called when a new branch has been found.
     * If the branch is satisfiable, then it is added to the list o branches with 
     * the appropriate status depending on value and the z3branches variable is updated.
     * @param condition
     * @param value
     * @param line_nr
     */
    static void encounteredNewBranch(MyVar condition, boolean value, int line_nr) {
        new_branch = PathTracker.ctx.mkEq(condition.z3var, PathTracker.ctx.mkBool(value));

        // Call the solver
        PathTracker.solve(new_branch, false);

        if (newBranch) {
            Context c = PathTracker.ctx;
            PathTracker.z3branches = c.mkAnd(new_branch, PathTracker.z3branches);
            newBranch = false;

            if(branch_line_numbers.containsKey(line_nr)) { 
                if(value && !branch_line_numbers.get(line_nr).isSatisfiableOnTrue) {
                    branch_line_numbers.get(line_nr).isSatisfiableOnTrue = true;
                } else if (!value && !branch_line_numbers.get(line_nr).isSatisfiableOnFalse) {
                    branch_line_numbers.get(line_nr).isSatisfiableOnFalse = true;
                }
            } else {
                Branch newBranch = new Branch(condition, value, !value);
                branch_line_numbers.put(line_nr, newBranch);
            }
        }
    }

    /**
     * Method called when a nea branch is found satisfiable and the solver generates 
     * a new list of inputs, which are further saven inside a list of unique traces.
     * @param new_inputs
     */
    static void newSatisfiableInput(LinkedList<String> new_inputs) {
        // Hurray! found a new branch using these new inputs!
        LinkedList<String> parsedNewInputs = new LinkedList<>();

        for (String input:new_inputs) {
            parsedNewInputs.add(input.split("\"")[1]);
        }

        if(!traces.containsKey(parsedNewInputs)) {
            traces.put(parsedNewInputs, 1);
        } else {
            Integer traceCount = traces.get(parsedNewInputs);
            traceCount += 1;
            traces.remove(parsedNewInputs);
            traces.put(parsedNewInputs, traceCount);
        }

        newBranch = true;
        
        // Limit the inputs size to 20 similar to KLEE
        if(PathTracker.inputs.size() > 20) {
            PathTracker.inputs.clear();
        }
    }

    /**
     * Method for fuzzing new inputs for a program.
     * 
     * @param inputSymbols the inputSymbols to fuzz from.
     * @return a fuzzed sequence
     */
    static List<String> fuzz(String[] symbols) {
        /*
         * Add here your code for fuzzing a new sequence for the RERS problem.
         * You can guide your fuzzer to fuzz "smart" input sequences to cover
         * more branches using symbolic execution. Right now we just generate
         * a complete random sequence using the given input symbols. Please
         * change it to your own code.
         */
        Integer maxCount = 0;
        List<String> maxTrace = new ArrayList<>();

        // From the list of posible traces, we take the one with the highest number 
        // of apparitions => the highest number of new branches discovered
        if(traces.size() != 0) {
            for (Map.Entry<List<String>, Integer> entry : traces.entrySet()) {
                if (maxCount < entry.getValue()) {
                    maxCount = entry.getValue();
                    maxTrace = entry.getKey();
                }
            }
            traces.remove(maxTrace);
        } else {
            maxTrace = generateRandomTrace(symbols);
        }

        return maxTrace;
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
        return trace;
    }

    /**
     * Counts the number of paths not yet explored
     */
    static int getNumUntraveledPaths() {
        int n_paths_untraveled = 0;

            for (Map.Entry<Integer, Branch> branch : branch_line_numbers.entrySet()) {

                if (!branch.getValue().isSatisfiableOnTrue || !branch.getValue().isSatisfiableOnFalse) {
                    n_paths_untraveled += 1;
            }
        }

        return n_paths_untraveled;
    }

    static void run() {
        initialize(PathTracker.inputSymbols);
        PathTracker.runNextFuzzedSequence(currentTrace.toArray(new String[0]));

        while (!isFinished) {
            // Do things!
            PathTracker.runNextFuzzedSequence(fuzz(PathTracker.inputSymbols).toArray(new String[0]));

            // finish when there are no more branches to be covered
            if (getNumUntraveledPaths() == 0) {
                isFinished = true;
            }

            System.out.println("Number of untraveled paths is: " + getNumUntraveledPaths());
            printUniqueErrorCodes();
        }
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

    public static void output(String out) {
        if (out.contains("error")) {
            error_codes.add(out);
        }
        //System.out.println(out);
    }

}