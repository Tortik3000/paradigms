package expression;

import java.util.List;
import java.util.Objects;

public class Const implements PriorityExpression, ListExpression {
    private final int value;


    public Const(int value) {
        this.value = value;
    }


    @Override
    public int evaluate(List<Integer> variables){
        return value;
    }

    @Override
    public int evaluate(int variable) {
        return value;
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return value;
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
    public boolean isSim(){
        return false;
    }
}
