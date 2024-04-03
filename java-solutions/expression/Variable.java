package expression;

import expression.typeEvaluaters.Type;

import java.util.List;
import java.util.Objects;

public class Variable implements PriorityExpression, ListExpression {

    private final String variable;
    private int indexVar;

    @Override
    public <T> T evaluate(T x, T y, T z, Type<T> type) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalStateException("Unexpected value: " + variable);
        };
    }

    public Variable(String variable, int indexVar) {
        this.variable = variable;
        this.indexVar = indexVar;
    }

    public Variable(int var) {
        this.variable = Integer.toString(var);
        this.indexVar = var;
    }

    public Variable(String var) {
        this.variable = var;

    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(indexVar);
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
    public boolean isSim() {
        return false;
    }


}
