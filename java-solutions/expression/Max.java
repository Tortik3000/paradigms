package expression;

import expression.typeEvaluaters.Type;

import java.util.List;

public class Max extends AbstractBinaryExpression {
    public Max(PriorityExpression expression1, PriorityExpression expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "max");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "max");
    }


    @Override
    public int evaluate(List<Integer> vars) {
        return evaluate(vars, "max");
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
    public boolean isSim() {
        return true;
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return evaluate(x, y, z, "max", type);
    }
}
