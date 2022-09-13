package nl.tudelft.instrumentation.fuzzing;

public class Branch {
    public MyVar condition;
    public boolean isExecutedOnTrue;
    public boolean isExecutedOnFalse;
    public float branchDistanceTrue;
    public float branchDistanceFalse;
    
    public Branch(MyVar condition, boolean value, float branchDistanceTrue, float branchDistanceFalse) {
            this.isExecutedOnTrue = value;
            this.isExecutedOnFalse = !value;
            this.condition = condition;
            this.branchDistanceTrue = branchDistanceTrue;
            this.branchDistanceFalse = branchDistanceFalse;
    }

    public Branch(MyVar condition, boolean value) {
        this.isExecutedOnTrue = value;
        this.isExecutedOnFalse = !value;
        this.condition = condition;
    }
}
