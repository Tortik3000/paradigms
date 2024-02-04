package expression;


import java.util.Objects;

abstract public class AbstractBinaryExpression implements Priority {
    protected final Priority expression1;
    protected final Priority expression2;

    public AbstractBinaryExpression(Priority expression1, Priority expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
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

    public int evaluate(int x, String operation) {
        return switch (operation) {
            case "+" -> expression1.evaluate(x) + expression2.evaluate(x);
            case "-" -> expression1.evaluate(x) - expression2.evaluate(x);
            case "*" -> expression1.evaluate(x) * expression2.evaluate(x);
            case "/" -> expression1.evaluate(x) / expression2.evaluate(x);
            case "&" -> expression1.evaluate(x) & expression2.evaluate(x);
            case "^" -> expression1.evaluate(x) ^ expression2.evaluate(x);
            case "|" -> expression1.evaluate(x) | expression2.evaluate(x);
            case "min" -> Math.min(expression1.evaluate(x), expression2.evaluate(x));
            case "max" -> Math.max(expression1.evaluate(x), expression2.evaluate(x));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(int x, int y, int z, String operation) {

        return switch (operation) {
            case "+" -> expression1.evaluate(x, y, z) + expression2.evaluate(x, y, z);
            case "-" -> expression1.evaluate(x, y, z) - expression2.evaluate(x, y, z);
            case "*" -> expression1.evaluate(x, y, z) * expression2.evaluate(x, y, z);
            case "/" -> expression1.evaluate(x, y, z) / expression2.evaluate(x, y, z);
            case "&" -> expression1.evaluate(x, y, z) & expression2.evaluate(x, y, z);
            case "^" -> expression1.evaluate(x, y, z) ^ expression2.evaluate(x, y, z);
            case "|" -> expression1.evaluate(x, y, z) | expression2.evaluate(x, y, z);
            case "min" -> Math.min(expression1.evaluate(x, y, z), expression2.evaluate(x, y, z));
            case "max" -> Math.max(expression1.evaluate(x, y, z), expression2.evaluate(x, y, z));
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
}
