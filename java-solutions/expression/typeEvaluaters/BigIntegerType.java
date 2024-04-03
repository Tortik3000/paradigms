package expression.typeEvaluaters;

import java.math.BigInteger;

public class BigIntegerType implements Type<BigInteger> {
    public BigIntegerType() {
    }

    ;

    @Override
    public BigInteger add(BigInteger num1, BigInteger num2) {
        return num1.add(num2);
    }

    @Override
    public BigInteger subtract(BigInteger num1, BigInteger num2) {
        return num1.subtract(num2);
    }

    @Override
    public BigInteger divide(BigInteger num1, BigInteger num2) {
        return num1.divide(num2);
    }

    @Override
    public BigInteger negate(BigInteger num) {
        return num.negate();
    }

    @Override
    public BigInteger constant(String num) {
        try {
            return new BigInteger(num);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    @Override
    public BigInteger min(BigInteger num1, BigInteger num2) {
        return num1.min(num2);
    }

    @Override
    public BigInteger max(BigInteger num1, BigInteger num2) {
        return num1.max(num2);
    }

    @Override
    public BigInteger countBite(BigInteger num) {
        return new BigInteger(String.valueOf(num.bitCount()));
    }

    @Override
    public BigInteger multiply(BigInteger num1, BigInteger num2) {
        return num1.multiply(num2);
    }

}
