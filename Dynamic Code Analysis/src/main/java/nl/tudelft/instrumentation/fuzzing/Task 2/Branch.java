package nl.tudelft.instrumentation.fuzzing;

/**
 * Class that defines a branch and containes all the information needed about a branch
*/
public class Branch {
    public MyVar condition; // the if-condition saved in the variable
    public boolean isExecutedOnTrue; // boolean value that states whether a branch has been executed on true value
    public boolean isExecutedOnFalse; // boolean value that states whether a branch has been executed on false value
    public float branchDistanceTrue; // branch distance for the true branch
    public float branchDistanceFalse; // branch distance for the false branch
    
    /**
     * Contructor to wrap a Branch object with branch distance values known
     * @condition the variable if-condition
     * @isExecutedOnTrue whether the branch executed on true
     * @isExecutedOnFalse whether the branch is executed on false
     * @branchDistanceTrue the true branch distance
     * @branchDistanceFalse the false branch distance
     */
    public Branch(MyVar condition, boolean isExecutedOnTrue, boolean isExecutedOnFalse, float branchDistanceTrue, float branchDistanceFalse) {
            this.isExecutedOnTrue = isExecutedOnTrue;
            this.isExecutedOnFalse = isExecutedOnFalse;
            this.condition = condition;
            this.branchDistanceTrue = branchDistanceTrue;
            this.branchDistanceFalse = branchDistanceFalse;
    }

    /**
     * Contructor to wrap a Branch object without branch distance values known
     * @condition the variable if-condition
     * @isExecutedOnTrue whether the branch executed on true
     * @isExecutedOnFalse whether the branch is executed on false
     */
    public Branch(MyVar condition, boolean isExecutedOnTrue, boolean isExecutedOnFalse) {
        this.isExecutedOnTrue = isExecutedOnTrue;
        this.isExecutedOnFalse = isExecutedOnFalse;
        this.condition = condition;
    }

    /**
    * Overrides the clone method for Branch class
    */
    @Override
    public Branch clone() {
        MyVar aux = condition.clone();
        Branch br = new Branch(aux, this.isExecutedOnTrue, this.isExecutedOnFalse, this.branchDistanceTrue, this.branchDistanceFalse);

        return br;
    }
}
