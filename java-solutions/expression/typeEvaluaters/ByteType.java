package expression.typeEvaluaters;

public class ByteType implements Type<Byte> {


    @Override
    public Byte add(Byte num1, Byte num2) {
        return (byte) (num1 + num2);
    }

    @Override
    public Byte subtract(Byte num1, Byte num2) {
        return (byte) (num1 - num2);
    }

    @Override
    public Byte multiply(Byte num1, Byte num2) {
        return (byte) (num1 * num2);
    }

    @Override
    public Byte divide(Byte num1, Byte num2) {
        return (byte) (num1 / num2);
    }

    @Override
    public Byte negate(Byte num) {
        return (byte) -num;
    }

    @Override
    public Byte constant(String num) {
        try {
            return (byte) Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    @Override
    public Byte min(Byte num1, Byte num2) {
        return (byte) Math.min(num1, num2);
    }

    @Override
    public Byte max(Byte num1, Byte num2) {
        return (byte) Math.max(num1, num2);
    }

    @Override
    public Byte countBite(Byte num) {
        try {
            return (byte) Integer.bitCount(Byte.toUnsignedInt(num));
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }

    }
}
