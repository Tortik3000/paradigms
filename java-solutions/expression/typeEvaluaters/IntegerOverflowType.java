package expression.typeEvaluaters;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

public class IntegerOverflowType implements Type<Integer> {


    @Override
    public Integer add(Integer num1, Integer num2) {
        int res = num2 + num1;
        if ((0 < num1 && Integer.MAX_VALUE - num1 < num2) ||
                (0 > num1 && Integer.MIN_VALUE - num1 > num2)) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }

    @Override
    public Integer subtract(Integer num1, Integer num2) {
        int res = num1 - num2;
        if (((num1 ^ num2) & (num1 ^ res)) < 0) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }

    @Override
    public Integer divide(Integer num1, Integer num2) {
        if (num2 == 0) {
            throw new DBZException("division by zero");
        } else if (num2 == -1 && num1 == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        } else {
            return num1 / num2;
        }
    }

    @Override
    public Integer negate(Integer num) {
        if (num == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        } else {
            return -num;
        }
    }

    @Override
    public Integer constant(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    @Override
    public Integer min(Integer num1, Integer num2) {
        return Math.min(num1, num2);
    }

    @Override
    public Integer max(Integer num1, Integer num2) {
        return Math.max(num1, num2);
    }

    @Override
    public Integer countBite(Integer num) {
        return Integer.bitCount(num);
    }

    @Override
    public Integer multiply(Integer num1, Integer num2) {
        int res = num2 * num1;
        if ((num1 == Integer.MIN_VALUE && num2 == -1) || (num1 == -1 && num2 == Integer.MIN_VALUE) || (num1 != 0 && res / num1 != num2)) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }
}
