package expression;

import java.util.List;

public class Subtract extends AbstractBinaryExpression{

    public Subtract(PriorityExpression expression1, PriorityExpression expression2) {
        super(expression1, expression2);
    }


    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "-");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "-");
    }


    @Override
    public int evaluate(List<Integer> vars){
        return evaluate(vars, "-");
    }

    @Override
    public String toString() {
        return toString(" - ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(3, false, "-");
    }


    public int getPriority() {
        return 3;
    }

    @Override
    public boolean isSim(){
        return false;
    }
}
