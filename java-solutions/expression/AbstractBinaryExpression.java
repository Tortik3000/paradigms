package expression;


import expression.typeEvaluaters.Type;

import java.util.List;
import java.util.Objects;

abstract public class AbstractBinaryExpression implements PriorityExpression {
    protected final PriorityExpression expression1;
    protected final PriorityExpression expression2;

    public AbstractBinaryExpression(PriorityExpression expression1, PriorityExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public <T> T evaluate(T x, T y, T z, String operation, Type<T> type) {
        return switch (operation) {
            case "+" -> type.add(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            case "-" -> type.subtract(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            case "*" -> type.multiply(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            case "/" -> type.divide(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            case "min" -> type.min(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            case "max" -> type.max(expression1.evaluate(x, y, z, type), expression2.evaluate(x, y, z, type));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(List<Integer> variables, String operation) {
        return switch (operation) {
            case "+" -> expression1.evaluate(variables) + expression2.evaluate(variables);
            case "-" -> expression1.evaluate(variables) - expression2.evaluate(variables);
            case "*" -> expression1.evaluate(variables) * expression2.evaluate(variables);
            case "/" -> expression1.evaluate(variables) / expression2.evaluate(variables);
            case "min" -> {
                int exp1 = expression1.evaluate(variables);
                int exp2 = expression2.evaluate(variables);
                yield Math.min(exp1, exp2);
            }
            case "max" -> {
                int exp1 = expression1.evaluate(variables);
                int exp2 = expression2.evaluate(variables);
                yield Math.max(exp1, exp2);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(int x, String operation) {
        return switch (operation) {
            case "+" -> expression1.evaluate(x) + expression2.evaluate(x);
            case "-" -> expression1.evaluate(x) - expression2.evaluate(x);
            case "*" -> expression1.evaluate(x) * expression2.evaluate(x);
            case "/" -> expression1.evaluate(x) / expression2.evaluate(x);
            case "min" -> {
                int exp1 = expression1.evaluate(x);
                int exp2 = expression2.evaluate(x);
                yield Math.min(exp1, exp2);
            }
            case "max" -> {
                int exp1 = expression1.evaluate(x);
                int exp2 = expression2.evaluate(x);
                yield Math.max(exp1, exp2);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };

    }

    public int evaluate(int x, int y, int z, String operation) {
        return switch (operation) {
            case "+" -> expression1.evaluate(x, y, z) + expression2.evaluate(x, y, z);
            case "-" -> expression1.evaluate(x, y, z) - expression2.evaluate(x, y, z);
            case "*" -> expression1.evaluate(x, y, z) * expression2.evaluate(x, y, z);
            case "/" -> expression1.evaluate(x, y, z) / expression2.evaluate(x, y, z);
            case "min" -> {
                int exp1 = expression1.evaluate(x, y, z);
                int exp2 = expression2.evaluate(x, y, z);
                yield Math.min(exp1, exp2);
            }
            case "max" -> {
                int exp1 = expression1.evaluate(x, y, z);
                int exp2 = expression2.evaluate(x, y, z);
                yield Math.max(exp1, exp2);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };

    }


    public String toMiniString(int priority, boolean isSim, String operation) {
        String leftToken = expression1.toMiniString();
        String rightToken = expression2.toMiniString();

        if (priority < expression1.getPriority()) {
            leftToken = "(" + leftToken + ")";
        }

        if (isSim) {
            if (priority < expression2.getPriority()) {
                rightToken = "(" + rightToken + ")";
            }
            if (expression2.getPriority() == 2 && expression2.getPriority() == priority && !expression2.isSim()) {
                rightToken = "(" + rightToken + ")";
            }
            if (expression2.getPriority() == 7 && expression2.getPriority() == priority && (expression2.getClass() != getClass())) {
                rightToken = "(" + rightToken + ")";
            }
        } else {
            if (priority <= expression2.getPriority()) {
                rightToken = "(" + rightToken + ")";
            }
        }
        return leftToken + " " + operation + " " + rightToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression1, expression2, getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractBinaryExpression that = (AbstractBinaryExpression) obj;
        return Objects.equals(expression1, that.expression1) && Objects.equals(expression2, that.expression2);
    }

    public String toString(String operation) {
        return "(" + expression1.toString() + operation + expression2.toString() + ")";
    }
}
