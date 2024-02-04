package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Variable implements Priority {

    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int variable) {
        return variable;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalStateException("Unexpected value: " + variable);
        };
    }


    @Override
    public String toString() {
        return variable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable that = (Variable) obj;
        return Objects.equals(variable, that.variable);
    }


    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSim(){
        return false;
    }
}
