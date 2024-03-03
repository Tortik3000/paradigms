package expression;

import java.util.List;

public class Min extends AbstractBinaryExpression{
    public Min(PriorityExpression expression1, PriorityExpression expression2){
        super(expression1, expression2);
    }
    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "min");
    }

    @Override
    public int evaluate(int x, int y, int z)  {
        return evaluate(x, y, z, "min");
    }


    @Override
    public int evaluate(List<Integer> vars){
        return evaluate(vars, "min");
    }

    @Override
    public String toString() {
        return toString(" min ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(7, true, "min");
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
