package expression;

import expression.typeEvaluaters.Type;

import java.util.List;
import java.util.Objects;

public class Const implements PriorityExpression, ListExpression {
    private final String value;


    public Const(int value) {
        this.value = Integer.toString(value);
    }

    public Const(String value) {
        this.value = value;
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return type.constant(value);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Integer.parseInt(value);
    }

    @Override
    public int evaluate(int variable) {
        return Integer.parseInt(value);
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.parseInt(value);
    }


    @Override
    public String toString() {
        return String.valueOf(value);

    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Const that = (Const) obj;
        return value == that.value;
    }


    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSim() {
        return false;
    }

}
