package expression;

import expression.typeEvaluaters.Type;

import java.util.List;

public class Xor extends AbstractBinaryExpression {

    public Xor(PriorityExpression expression1, PriorityExpression expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int variable) {
        return evaluate(variable, "^");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "^");
    }


    @Override
    public int evaluate(List<Integer> vars) {
        return evaluate(vars, "^");
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String toString() {
        return toString(" ^ ");
    }

    @Override
    public String toMiniString() {
        return toMiniString(5, true, "^");
    }

    @Override
    public boolean isSim() {
        return true;
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return null;
    }
}
