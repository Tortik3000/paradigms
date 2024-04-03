package expression;

import expression.typeEvaluaters.Type;

import java.util.List;

public class Negate extends AbstractUnaryExpression {

    public Negate(PriorityExpression expression) {
        super(expression);
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return evaluate(x, y, z, "-", type);
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, "-");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "-");
    }

    @Override
    public int evaluate(List<Integer> vars) {
        return evaluate(vars, "-");
    }

    @Override
    public String toString() {
        return toString("-");
    }

    @Override
    public String toMiniString() {
        return toMiniString("-");
    }


}

