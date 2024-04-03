package expression.typeEvaluaters;

public class BooleanType implements Type<Boolean> {


    @Override
    public Boolean add(Boolean num1, Boolean num2) {
        return num1 || num2;
    }

    @Override
    public Boolean subtract(Boolean num1, Boolean num2) {
        return num1 ^ num2;
    }

    @Override
    public Boolean multiply(Boolean num1, Boolean num2) {
        return num1 & num2;
    }

    @Override
    public Boolean divide(Boolean num1, Boolean num2) {
        if (!num2) {
            throw new ArithmeticException();
        }
        return num1;
    }

    @Override
    public Boolean negate(Boolean num) {
        return num;
    }

    @Override
    public Boolean constant(String num) {
        int num0 = Integer.parseInt(num);
        return num0 != 0;
    }

    @Override
    public Boolean min(Boolean num1, Boolean num2) {
        return num1 && num2;
    }

    @Override
    public Boolean max(Boolean num1, Boolean num2) {
        return num1 || num2;
    }

    @Override
    public Boolean countBite(Boolean num) {
        return num;
    }
}
