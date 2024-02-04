package expression;

import java.math.BigInteger;

public class Max extends AbstractBinaryExpression{
    public Max(Priority expression1, Priority expression2){
        super(expression1, expression2);
    }
    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "max");
    }

    @Override
    public int evaluate(int x, int y, int z)  {
        return evaluate(x, y, z, "max");
    }


    @Override
    public String toString() {
        return toString(" max ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(7, true, "max");
    }

    @Override
    public int getPriority() {
        return 7;
    }

    @Override
    public boolean isSim(){
        return true;
    }
}
