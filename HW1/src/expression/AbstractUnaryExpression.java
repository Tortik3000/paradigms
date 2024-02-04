package expression;

import java.util.Objects;

abstract public class AbstractUnaryExpression implements Priority{
    protected final Priority expression;

    public AbstractUnaryExpression(Priority expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractUnaryExpression that = (AbstractUnaryExpression) obj;
        return Objects.equals(expression, that.expression);
    }


    public String toString(String operation) {
        return operation + "(" + expression + ")";

    }

    public int evaluate(int x, String operation) {
        return switch (operation) {
            case "-" -> (-1) * expression.evaluate(x);
            case "l1" -> highestOneBit(expression.evaluate(x));
            case "t1" -> lowestOneBit(expression.evaluate(x));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }

    public int evaluate(int x, int y, int z, String operation){
        return switch (operation) {
            case "-" -> (-1) * expression.evaluate(x, y, z);
            case "l1" -> highestOneBit(expression.evaluate(x, y, z));
            case "t1" -> lowestOneBit(expression.evaluate(x, y, z));
            default -> throw new IllegalArgumentException("Unexpected value: " + operation);
        };
    }


    public String toMiniString( String operation) {
        if(expression.getPriority() <= 1){
            return  operation + " " + expression.toMiniString();

        }
        return operation + "(" + expression.toMiniString()+")";
    }


    public int getPriority(){
        return 1;
    }


    public boolean isSim(){
        return false;
    }

    private int highestOneBit(int x){
        String strNum = Integer.toBinaryString(x);
        if(strNum.length() < 32){
            return 0;
        }
        int count = 0;
        int index = 0;
        while( index < 32 && strNum.charAt(index) == '1'){
            count += 1;
            index += 1;
        }
        return count;
    }

    private int lowestOneBit(int x){
        String strNum = Integer.toBinaryString(x);

        int count = 0;
        int index = strNum.length() - 1;
        while(index >= 0 && strNum.charAt(index) == '1'){
            count += 1;
            index -= 1;
        }
        return count;
    }
}
