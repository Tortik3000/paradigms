package expression.exceptions;

import expression.*;

import java.util.List;


public class ExpressionParser implements TripleParser, ListParser {
    private static final int maxPriority = 7;

    public ExpressionParser() {
    }

    @Override
    public PriorityExpression parse(String expression) throws Exception {
        final Tokenizer tokenizer = new Tokenizer(expression);
        return parsePriority(tokenizer, maxPriority);
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        final Tokenizer tokenizer = new Tokenizer(expression, variables);
        return parsePriority(tokenizer, maxPriority);
    }

    public PriorityExpression parsePriority(Tokenizer tokenizer, int priority) throws Exception {
        PriorityExpression res = priority >= 2 ?
                parsePriority(tokenizer, priority - 1) : parsePriority1(tokenizer);
        while (true) {
            if (checkOp(tokenizer, priority, Token.MIN)) {
                res = new Min(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.MAX)) {
                res = new Max(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.OR)) {
                res = new Or(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.XOR)) {
                res = new Xor(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.AND)) {
                res = new And(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.ADD)) {
                res = new CheckedAdd(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.SUB)) {
                res = new CheckedSubtract(res, parsePriority(tokenizer, priority - 1));
            } else if (checkOp(tokenizer, priority, Token.MUL)) {
                res = new CheckedMultiply(res, parsePriority1(tokenizer));
            } else if (checkOp(tokenizer, priority, Token.DIV)) {
                res = new CheckedDivide(res, parsePriority1(tokenizer));
            } else {
                return res;
            }
        }
    }

    private static boolean checkOp(Tokenizer tokenizer, int priority, Token op) {
        return priority == Token.getPriority(op) && tokenizer.getCurToken() == op;
    }



    public PriorityExpression parsePriority1(Tokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        PriorityExpression res;
        switch (tokenizer.getCurToken()) {
            case NEG -> res = new CheckedNegate(parsePriority1(tokenizer));
            case NUM -> {
                res = new Const(tokenizer.getNumber());
                tokenizer.nextToken();
            }
            case VAR -> {
                res = new Variable(tokenizer.getVarName(), tokenizer.getVarIndex());
                tokenizer.nextToken();
            }
            case LP -> {
                String curBracket = tokenizer.getCurBracket();
                res = parsePriority(tokenizer, maxPriority);
                if (tokenizer.getCurToken() != Token.RP || !tokenizer.getCurBracket().equals(tokenizer.getPairBracket(curBracket))) {
                    throw new ParserException("No closing parenthesis, in position " + tokenizer.getPos());
                }
                tokenizer.nextToken();
            }
            default -> throw new ParserException("No last argument, in position " + tokenizer.getPos());
        }
        return res;
    }


}

