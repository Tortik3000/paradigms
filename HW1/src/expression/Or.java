package expression;

import java.math.BigInteger;

public class Or extends AbstractBinaryExpression{

    public Or(Priority expression1, Priority expression2){
        super(expression1, expression2);
    }
    @Override
    public int evaluate(int variable){
        return  evaluate(variable, "|");
    }

    @Override
    public  int evaluate(int x, int y, int z){
        return evaluate(x, y, z, "|");
    }

    @Override
    public int getPriority(){
        return 6;
    }

    @Override
    public String toString(){
        return toString(" | ");
    }

    @Override
    public String toMiniString(){
        return toMiniString(6, true, "|");
    }

    @Override
    public boolean isSim(){
        return true;
    }
}
