package expression;

import java.math.BigInteger;

public class And extends AbstractBinaryExpression{

    public And (Priority expression1, Priority expression2){
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int variable){
        return  evaluate(variable, "&");
    }

    @Override
    public  int evaluate(int x, int y, int z){
        return evaluate(x, y, z, "&");
    }

    @Override
    public int getPriority(){
        return 4;
    }

    @Override
    public String toString(){
        return toString(" & ");
    }

    @Override
    public String toMiniString(){
        return toMiniString(4, true, "&");
    }

    @Override
    public boolean isSim(){
        return true;
    }
}
