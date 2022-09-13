package nl.tudelft.instrumentation.fuzzing;

import java.util.HashMap;
import java.util.List;

public class Trace {
    public Trace(List<String> currentTrace, int i) {
        trace = currentTrace;
        number_of_branches = i;
    }

    public Trace(List<String> currentTrace) {
        trace = currentTrace;
    }

    public int number_of_branches = 0;
    public List<String> trace;
    // HashMap<Integer, Branch> branches = new HashMap<Integer, Branch>();
    public float d_branch_sum = 0.0f;
}
