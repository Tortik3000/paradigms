package expression;

import java.util.List;

public class LowBit extends AbstractUnaryExpression {

    public LowBit(PriorityExpression expression){
        super(expression);
    }

    @Override
    public String toString(){
        return toString("t1");
    }

    @Override
    public int evaluate(int x){
        return evaluate(x, "t1");
    }

    @Override
    public int evaluate(int x, int y, int z){
        return evaluate(x, y, z, "t1");
    }

    @Override
    public int evaluate(List<Integer> vars){
        return evaluate(vars, "t1");
    }

    @Override
    public String toMiniString(){
        return toMiniString("t1");
    }
}
