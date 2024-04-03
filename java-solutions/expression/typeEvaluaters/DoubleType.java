package expression.typeEvaluaters;

public class DoubleType implements Type<Double> {
    public DoubleType() {
    }

    ;

    @Override
    public Double add(Double num1, Double num2) {
        return num1 + num2;
    }

    @Override
    public Double subtract(Double num1, Double num2) {
        return num1 - num2;
    }

    @Override
    public Double multiply(Double num1, Double num2) {
        return num1 * num2;
    }

    @Override
    public Double divide(Double num1, Double num2) {
        return num1 / num2;
    }

    @Override
    public Double negate(Double num) {
        return -num;
    }

    @Override
    public Double constant(String num) {
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    @Override
    public Double min(Double num1, Double num2) {
        return Math.min(num1, num2);
    }

    @Override
    public Double max(Double num1, Double num2) {
        return Math.max(num1, num2);
    }

    @Override
    public Double countBite(Double num) {
        return (double) Long.bitCount(Double.doubleToLongBits(num));
    }
}
