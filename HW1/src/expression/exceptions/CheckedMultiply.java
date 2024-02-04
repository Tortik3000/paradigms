package expression.exceptions;

import expression.Multiply;
import expression.Priority;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(Priority expression1, Priority expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int num1 = expression1.evaluate(x, y, z);
        int num2 = expression2.evaluate(x, y, z);
        int res = num2 * num1;
        if ((num1 == Integer.MIN_VALUE && num2 == -1) || (num1 == -1 && num2 == Integer.MIN_VALUE) || (num1 != 0 && res / num1 != num2)) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }
}
