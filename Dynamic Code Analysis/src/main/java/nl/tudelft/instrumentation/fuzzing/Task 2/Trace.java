package nl.tudelft.instrumentation.fuzzing;

import java.util.HashMap;
import java.util.List;

/**
 * Class that defines a trace object and containes information relevant to analysis of fuzzer
 */
public class Trace {
    public int number_of_branches = 0; // total number of branches
    public List<String> trace; // the input trace
    public float d_branch_sum = 0.0f; // total sum of branch distance

    /**
     * Constructor to wrap a Trace object with known number of branches
     * @param currentTrace
     * @param i
     */
    public Trace(List<String> currentTrace, int i) {
        trace = currentTrace;
        number_of_branches = i;
    }

    /**
     * Constructor to wrap a Trace object with unknown number of branches
     * @param currentTrace
     */
    public Trace(List<String> currentTrace) {
        trace = currentTrace;
    }
}
