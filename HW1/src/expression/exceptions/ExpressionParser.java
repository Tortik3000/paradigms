package expression.exceptions;

import expression.*;


public class ExpressionParser implements TripleParser {
    public ExpressionParser() {
    }

    @Override
    public Priority parse(String expression) throws Exception {
        StringSource exp = new StringSource(expression);
        return parsePriority(exp, 7);
    }

    public Priority parsePriority(StringSource expression, int priority) throws Exception {
        Priority res;
        if (priority >= 2) {
            res = parsePriority(expression, priority - 1);
        } else {
            res = parsePriority1(expression);
        }
        while (true) {
            if (priority == 7 && expression.getCurToken() == Token.MIN) {
                res = new Min(res, parsePriority(expression, 6));
            } else if (priority == 7 && expression.getCurToken() == Token.MAX) {
                res = new Max(res, parsePriority(expression, 6));
            } else if (priority == 6 && expression.getCurToken() == Token.OR) {
                res = new Or(res, parsePriority(expression, 5));
            } else if (priority == 5 && expression.getCurToken() == Token.XOR) {
                res = new Xor(res, parsePriority(expression, 4));
            } else if (priority == 4 && expression.getCurToken() == Token.AND) {
                res = new And(res, parsePriority(expression, 3));
            } else if (priority == 3 && expression.getCurToken() == Token.ADD) {
                res = new CheckedAdd(res, parsePriority(expression, 2));
            } else if (priority == 3 && expression.getCurToken() == Token.SUB) {
                res = new CheckedSubtract(res, parsePriority(expression, 2));
            } else if (priority == 2 && expression.getCurToken() == Token.MUL) {
                res = new CheckedMultiply(res, parsePriority1(expression));
            } else if (priority == 2 && expression.getCurToken() == Token.DIV) {
                res = new CheckedDivide(res, parsePriority1(expression));
            } else {
                return res;
            }
        }
    }

    public Priority parsePriority1(StringSource expression) throws Exception {
        expression.nextToken();
        Priority res;
        switch (expression.getCurToken()) {
            case NEG -> res = new CheckedNegate(parsePriority1(expression));
            case L1 -> res = new HeightBit(parsePriority1(expression));
            case T1 -> res = new LowBit(parsePriority1(expression));
            case NUM -> {
                res = new Const(expression.getNumber());
                expression.nextToken();
            }
            case VAR -> {
                res = new Variable(expression.getVarName());
                expression.nextToken();
            }
            case LP -> {
                res = parsePriority(expression, 7);
                if (expression.getCurToken() != Token.RP) {
                    throw new IllegalStateException("No closing parenthesis");
                }
                expression.nextToken();
            }
            default -> throw new IllegalArgumentException("No last argument");
        }
        return res;
    }


}

