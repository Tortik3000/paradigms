package expression.exceptions;

import expression.Divide;
import expression.Priority;
import expression.Subtract;

public class CheckedDivide extends Divide {
    public CheckedDivide(Priority expression1, Priority expression2){
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int num1 = expression1.evaluate(x, y, z);
        int num2 = expression2.evaluate(x, y, z);
        if (num2 == 0) {
            throw new DBZException("division by zero");
        } else if (num2 == -1 && num1 == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        } else {
            return num1 / num2;
        }
    }
}
