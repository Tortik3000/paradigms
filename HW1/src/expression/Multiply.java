package expression;

import java.math.BigInteger;

public class Multiply extends AbstractBinaryExpression {

    public Multiply(Priority expression1, Priority expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "*");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "*");
    }

    @Override
    public String toString() {
        return toString(" * ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(2, true, "*");
    }


    public int getPriority() {
        return 2;
    }

    @Override
    public boolean isSim(){
        return true;
    }
}
