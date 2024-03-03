package expression;

import java.util.List;

public class Negate extends AbstractUnaryExpression{

    public Negate(PriorityExpression expression){
        super(expression);
    }

    @Override
    public int evaluate(int x){
        return evaluate(x, "-");
    }

    @Override
    public int evaluate(int x, int y, int z){
        return evaluate(x, y, z, "-");
    }

    @Override
    public int evaluate(List<Integer> vars){
        return evaluate(vars, "-");
    }

    @Override
    public String toString(){
        return toString("-");
    }

    @Override
    public String toMiniString(){
        return toMiniString("-");
    }
}

