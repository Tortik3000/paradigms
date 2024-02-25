package expression.exceptions;

import expression.Negate;
import expression.Priority;

import java.util.List;

public class CheckedNegate extends Negate{

    public CheckedNegate (Priority expression){
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z){
        int num = expression.evaluate(x, y, z);
        return checkOverflow(num);
    }

    @Override
    public int evaluate(List<Integer> vars){
        int num = expression.evaluate(vars);
        return checkOverflow(num);
    }

    public int checkOverflow(int  num){
        if (num == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        } else {
            return -num;
        }
    }
}
