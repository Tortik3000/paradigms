package expression.exceptions;

import expression.*;

public enum Token {

    ADD, SUB, MUL, DIV, NEG, END, NUM, LP, RP, VAR, BEGIN, AND, XOR, OR, MIN, MAX, COUNT;

    public static final Token[] binaryOp = new Token[]{
            ADD, SUB, MUL, DIV, NEG, AND, XOR, OR, MIN, MAX,
    };

    public static int getPriority(Token token) {
        return switch (token) {
            case ADD -> 3;
            case SUB -> 3;
            case MUL -> 2;
            case DIV -> 2;
            case AND -> 4;
            case XOR -> 5;
            case OR -> 6;
            case MIN -> 7;
            case MAX -> 7;
            default -> 0;
        };
    }

    public static boolean isBinaryOp(Token token) {
        return switch (token) {
            case ADD -> true;
            case SUB -> true;
            case OR -> true;
            case AND -> true;
            case XOR -> true;
            case MUL -> true;
            case DIV -> true;
            default -> false;
        };
    }

    public static PriorityExpression makeOperation(Token token, PriorityExpression left, PriorityExpression right) {
        return switch (token) {
            case ADD -> new Add(left, right);
            case SUB -> new Subtract(left, right);
            case OR -> new Or(left, right);
            case AND -> new And(left, right);
            case XOR -> new Xor(left, right);
            case MUL -> new Multiply(left, right);
            case DIV -> new Divide(left, right);
            case MIN -> new Min(left, right);
            case MAX -> new Max(left, right);
            default -> throw new ParserException("");
        };
    }
}
