package expression.exceptions;

import base.Asserts;
import expression.*;

import java.math.BigInteger;

public class CheckedAdd extends Add {

    public CheckedAdd(Priority expression1, Priority expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int num1 = expression1.evaluate(x, y, z);
        int num2 = expression2.evaluate(x, y, z);
        int res = num2 + num1;
        if ((0 < num1 && Integer.MAX_VALUE - num1 < num2) ||
                (0 > num1 && Integer.MIN_VALUE - num1 > num2)) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }
}
