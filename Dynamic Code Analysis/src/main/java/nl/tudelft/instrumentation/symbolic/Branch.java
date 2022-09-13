package nl.tudelft.instrumentation.symbolic;

/**
 * Class that defines a branch and containes all the information needed about a branch
*/
public class Branch {
    public MyVar condition; // the if-condition saved in the variable
    public boolean isSatisfiableOnTrue; // boolean value that states whether a branch has been executed on true value
    public boolean isSatisfiableOnFalse; // boolean value that states whether a branch has been executed on false value
    
    /**
     * Contructor to wrap a Branch object with branch distance values known
     * @condition the variable if-condition
     * @isExecutedOnTrue whether the branch executed on true
     * @isExecutedOnFalse whether the branch is executed on false
     */
    public Branch(MyVar condition, boolean isExecutedOnTrue, boolean isExecutedOnFalse) {
            this.isSatisfiableOnTrue = isExecutedOnTrue;
            this.isSatisfiableOnFalse = isExecutedOnFalse;
            this.condition = condition;
    }
}
