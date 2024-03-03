package expression.exceptions;

public enum Token {

    ADD, SUB, MUL, DIV, NEG, END, NUM, LP, RP, VAR, BEGIN, AND, XOR, OR, MIN, MAX;

    public static int getPriority(Token token){
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

    public static boolean isBinaryOp(Token token){
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

}
