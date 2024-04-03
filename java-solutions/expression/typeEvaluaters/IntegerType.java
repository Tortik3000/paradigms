package expression.typeEvaluaters;

public class IntegerType implements Type<Integer> {
    public IntegerType() {
    }

    @Override
    public Integer add(Integer num1, Integer num2) {
        return num1 + num2;
    }

    @Override
    public Integer subtract(Integer num1, Integer num2) {
        return num1 - num2;
    }

    @Override
    public Integer divide(Integer num1, Integer num2) {
        return num1 / num2;
    }

    @Override
    public Integer negate(Integer num) {
        return -num;
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
        return num1 * num2;
    }
}
