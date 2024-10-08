package expression;

import expression.typeEvaluaters.Type;

import java.util.List;
import java.util.Objects;

abstract public class AbstractUnaryExpression implements PriorityExpression {
    protected final PriorityExpression expression;

    public AbstractUnaryExpression(PriorityExpression expression) {
        this.expression = expression;
    }


    public <T> T evaluate(T x, T y, T z, String operation, Type<T> type) {
        return switch (operation) {
            case "-" -> type.negate(expression.evaluate(x, y, z, type));
            case "count" -> type.countBite((expression.evaluate(x, y, z, type)));
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }

    public int evaluate(List<Integer> variables, String operation) {
        return switch (operation) {
            case "-" -> (-1) * expression.evaluate(variables);
            case "l1" -> highestOneBit(expression.evaluate(variables));
            case "t1" -> lowestOneBit(expression.evaluate(variables));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(int x, String operation) {
        return switch (operation) {
            case "-" -> (-1) * expression.evaluate(x);
            case "l1" -> highestOneBit(expression.evaluate(x));
            case "t1" -> lowestOneBit(expression.evaluate(x));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(int x, int y, int z, String operation) {
        return switch (operation) {
            case "-" -> (-1) * expression.evaluate(x, y, z);
            case "l1" -> highestOneBit(expression.evaluate(x, y, z));
            case "t1" -> lowestOneBit(expression.evaluate(x, y, z));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }


    public String toMiniString(String operation) {
        if (expression.getPriority() <= 1) {
            return operation + " " + expression.toMiniString();

        }
        return operation + "(" + expression.toMiniString() + ")";
    }


    public int getPriority() {
        return 1;
    }


    public boolean isSim() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractUnaryExpression that = (AbstractUnaryExpression) obj;
        return Objects.equals(expression, that.expression);
    }


    public String toString(String operation) {
        return operation + "(" + expression + ")";

    }

    private int highestOneBit(int x) {
        String strNum = Integer.toBinaryString(x);
        if (strNum.length() < 32) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while (index < 32 && strNum.charAt(index) == '1') {
            count += 1;
            index += 1;
        }
        return count;
    }

    private int lowestOneBit(int x) {
        String strNum = Integer.toBinaryString(x);

        int count = 0;
        int index = strNum.length() - 1;
        while (index >= 0 && strNum.charAt(index) == '1') {
            count += 1;
            index -= 1;
        }
        return count;
    }
}
