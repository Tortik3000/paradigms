package expression.exceptions;

import expression.Priority;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(Priority expression1, Priority expression2){
        super(expression1, expression2);
    }

    public int evaluate(int x, int y, int z){
        int num1 = expression1.evaluate(x, y, z);
        int num2 = expression2.evaluate(x, y, z);
        int res = num1 - num2;
        if (((num1 ^ num2) & (num1 ^ res)) < 0) {
            throw new OverflowException("overflow");
        } else {
            return res;
        }
    }
}
