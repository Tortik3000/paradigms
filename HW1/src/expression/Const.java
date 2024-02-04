package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const implements Priority {
    private final BigInteger value;


    public Const(int value) {
        this.value = BigInteger.valueOf(value);
    }

    public Const(BigInteger value) {
        this.value = value;
    }

    @Override
    public int evaluate(int variable) {
        return value.intValue();
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
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
        return Objects.equals(value, that.value);
    }


    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSim(){
        return false;
    }
}
