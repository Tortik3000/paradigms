package expression;

import expression.AbstractUnaryExpression;
import expression.typeEvaluaters.Type;

import java.util.List;

public class Count extends AbstractUnaryExpression {
    public Count(PriorityExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x) {
        return 0;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return 0;
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return evaluate(x, y, z, "count", type);
    }

    @Override
    public String toString() {
        return toString("count");
    }
}
