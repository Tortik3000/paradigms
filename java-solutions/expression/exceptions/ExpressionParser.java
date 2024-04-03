package expression.exceptions;

import expression.*;
import expression.typeEvaluaters.Type;

import java.util.List;


public class ExpressionParser implements TripleParser, ListParser {
    private static final int MAX_PRIORITY = 7;

    public ExpressionParser() {
    }

    @Override
    public PriorityExpression parse(String expression) throws Exception {
        final Tokenizer tokenizer = new Tokenizer(expression);
        return parsePriority(tokenizer, MAX_PRIORITY);
    }

    public <T> PriorityExpression parse(String expression, Type<T> type) throws Exception {
        final Tokenizer tokenizer = new Tokenizer(expression, type);
        return parsePriority(tokenizer, MAX_PRIORITY);
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        final Tokenizer tokenizer = new Tokenizer(expression, variables);
        return parsePriority(tokenizer, MAX_PRIORITY);
    }

    public PriorityExpression parsePriority(Tokenizer tokenizer, int priority) throws Exception {
        PriorityExpression res = priority >= 2 ?
                parsePriority(tokenizer, priority - 1) : parsePriority1(tokenizer);
        while (true) {
            boolean flag = true;
            for (int i = 0; i < Token.binaryOp.length; i++) {
                if (checkOp(tokenizer, priority, Token.binaryOp[i])) {
                    if (priority == 2) {
                        res = Token.makeOperation(
                                Token.binaryOp[i], res, parsePriority1(tokenizer));
                    } else {
                        res = Token.makeOperation(
                                Token.binaryOp[i], res, parsePriority(tokenizer, priority - 1));
                    }
                    flag = false;
                    break;
                }
            }
            if (flag) {
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
            case COUNT -> res = new Count(parsePriority1(tokenizer));
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
                res = parsePriority(tokenizer, MAX_PRIORITY);
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

