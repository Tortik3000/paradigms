package expression.parser;

import expression.*;
import expression.exceptions.TripleParser;


public class ExpressionParser implements TripleParser {
    public ExpressionParser() {
    }

    @Override
    public Priority parse(String expression) {
        StringSource exp = new StringSource(expression);
        return parsePriority(exp, 6);
    }

    public Priority parsePriority(StringSource expression, int priority){
        Priority res;
        if(priority >= 2){
            res = parsePriority(expression, priority - 1);
        }else{
            res = parsePriority1(expression);
        }
        while (true){
            if (priority == 6 && expression.getCurToken() == Token.OR) {
                res = new Or(res, parsePriority(expression, 5));
            } else if(priority == 5 && expression.getCurToken() == Token.XOR) {
                res = new Xor(res, parsePriority(expression, 4));
            } else if (priority == 4 && expression.getCurToken() == Token.AND) {
                res = new And(res, parsePriority(expression, 3));
            }else if(priority == 3 && expression.getCurToken() == Token.ADD){
                res = new Add(res, parsePriority(expression, 2));
            }else if(priority == 3 && expression.getCurToken() == Token.SUB){
                res = new Subtract(res, parsePriority(expression, 2));
            } else if(priority == 2 && expression.getCurToken() == Token.MUL){
                res = new Multiply(res, parsePriority1(expression));
            }else if(priority == 2 && expression.getCurToken() == Token.DIV){
                res = new Divide(res, parsePriority1(expression));
            }
            else{
                return res;
            }
        }
    }

    public Priority parsePriority1(StringSource expression) {
        expression.nextToken();
        Priority res;
        switch (expression.getCurToken()) {
            case NEG:
                res = new Negate(parsePriority1(expression));
                break;
            case L1:
                res = new HeightBit(parsePriority1(expression));
                break;
            case T1:
                res = new LowBit(parsePriority1(expression));
                break;
            case NUM:
                res = new Const(expression.getNumber());
                expression.nextToken();
                break;
            case VAR:
                res = new Variable(expression.getVarName());
                expression.nextToken();
                break;
            case LP:
                res = parsePriority(expression, 6);
                if(expression.getCurToken() != Token.RP){
                    throw new IllegalStateException("Expected value: )");
                }
                expression.nextToken();
                break;
            default:
                throw new IllegalStateException("Unexpected value:");
        }
        return res;
    }
}

