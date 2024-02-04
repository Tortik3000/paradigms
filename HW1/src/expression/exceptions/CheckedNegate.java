package expression.exceptions;

import expression.Negate;
import expression.Priority;

public class CheckedNegate extends Negate {

    public CheckedNegate (Priority expression){
        super(expression);
    }

    public int evaluate(int x, int y, int z){
        int num = expression.evaluate(x, y, z);
        if (num == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        } else {
            return -num;
        }
    }
}
