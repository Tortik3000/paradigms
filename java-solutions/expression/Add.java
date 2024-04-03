package expression;

import expression.typeEvaluaters.Type;

import java.util.List;

public class Add extends AbstractBinaryExpression {

    public Add(PriorityExpression expression1, PriorityExpression expression2) {
        super(expression1, expression2);
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return evaluate(x, y, z, "+", type);
    }

    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "-");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "+");
    }

    @Override
    public int evaluate(List<Integer> vars) {
        return evaluate(vars, "+");
    }

    @Override
    public String toString() {
        return toString(" + ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(3, true, "+");
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean isSim() {
        return true;
    }


}
