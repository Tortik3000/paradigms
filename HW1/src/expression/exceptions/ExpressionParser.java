package expression.exceptions;

import expression.*;

import java.util.List;


public class ExpressionParser implements TripleParser, ListParser {
    private final int maxPriority = 7;

    public ExpressionParser() {
    }

    @Override
    public Priority parse(String expression) throws Exception {
        final StringSource exp = new StringSource(expression);
        return parsePriority(exp, maxPriority);
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        final StringSource exp = new StringSource(expression, variables);
        return parsePriority(exp, maxPriority);
    }

    public Priority parsePriority(StringSource expression, int priority) throws Exception {
        Priority res = priority >= 2 ?
                parsePriority(expression, priority - 1) : parsePriority1(expression);
        while (true) {
//            for (int i = 2; i <= maxPriority; i++){
//                if(i == priority){
//                    if(i == getPriority(expression.getCurToken())) {
//                        switch (expression.getCurToken()) {
//                            case ADD -> res = new CheckedAdd(res, parsePriority(expression, priority - 1));
//                            case SUB -> res = new CheckedSubtract(res, parsePriority(expression, priority - 1));
//                            case MUL -> res = new CheckedMultiply(res, parsePriority1(expression));
//                            case DIV -> res = new CheckedDivide(res, parsePriority1(expression));
//                            case AND -> res = new And(res, parsePriority(expression, priority - 1));
//                            case XOR -> res = new Xor(res, parsePriority(expression, priority - 1));
//                            case OR -> res = new Or(res, parsePriority(expression, priority - 1));
//                            case MIN -> res = new Min(res, parsePriority(expression, priority - 1));
//                            case MAX -> res = new Max(res, parsePriority(expression, priority - 1));
//                            default -> {
//                                return res;
//                            }
//                        };
//                        break;
//                    }else{
//                        return res;
//                    }
//                }
//            }

            if (checkOp(expression, priority, Token.MIN)) {
                res = new Min(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.MAX)) {
                res = new Max(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.OR)) {
                res = new Or(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.XOR)) {
                res = new Xor(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.AND)) {
                res = new And(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.ADD)) {
                res = new CheckedAdd(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.SUB)) {
                res = new CheckedSubtract(res, parsePriority(expression, priority - 1));
            } else if (checkOp(expression, priority, Token.MUL)) {
                res = new CheckedMultiply(res, parsePriority1(expression));
            } else if (checkOp(expression, priority, Token.DIV)) {
                res = new CheckedDivide(res, parsePriority1(expression));
            } else {
                return res;
            }

        }
    }

    private static boolean checkOp(StringSource expression, int priority, Token op) {
        return priority == getPriority(op) && expression.getCurToken() == op;
    }

    private static int getPriority(Token token) {
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

    public Priority parsePriority1(StringSource expression) throws Exception {
        expression.nextToken();
        Priority res;
        switch (expression.getCurToken()) {
            case NEG -> res = new CheckedNegate(parsePriority1(expression));
            case NUM -> {
                res = new Const(expression.getNumber());
                expression.nextToken();
            }
            case VAR -> {
                res = new Variable(expression.getVarName(), expression.getVarIndex());
                expression.nextToken();
            }
            case LP -> {
                String curBracket = expression.getCurBracket();
                res = parsePriority(expression, maxPriority);
                if (expression.getCurToken() != Token.RP || !expression.getCurBracket().equals(expression.getPair(curBracket))) {
                    throw new ParserException("No closing parenthesis, in position " + expression.getPos());
                }
                expression.nextToken();
            }
            default -> throw new ParserException("No last argument, in position " + expression.getPos());
        }
        return res;
    }


}

