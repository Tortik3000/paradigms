package expression;

import java.math.BigInteger;

public class HeightBit extends AbstractUnaryExpression {

    public HeightBit(Priority expression){
        super(expression);
    }

    @Override
    public String toString(){
        return toString("l1");
    }

    @Override
    public int evaluate(int x){
        return evaluate(x, "l1");
    }

    @Override
    public int evaluate(int x, int y, int z){
        return evaluate(x, y, z, "l1");
    }

    @Override
    public String toMiniString(){
        return toMiniString("l1");
    }
}
