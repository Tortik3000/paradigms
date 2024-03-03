package expression.exceptions;

import java.util.List;
import java.util.Map;


public class Tokenizer {
    private final String data;
    private int pos;
    private Token curToken = Token.BEGIN;
    private Token prevToken = Token.BEGIN;
    private int number;
    private String varName;
    private int balance = 0;
    private String curBracket;
    private List<String> variables = List.of("x", "y", "z");

    private static final Map<String, String> pairs = Map.of("(", ")", "{", "}", "[", "]");

    public Tokenizer(final String data) {
        this.data = data;
    }

    public Tokenizer(final String data, final List<String> variables) {
        this.data = data;
        this.variables = variables;
    }

    public void skipWhitespace() {
        while (pos < data.length() && Character.isWhitespace(data.charAt(pos))) {
            pos += 1;
        }
    }

    public void nextInt() {
        StringBuilder sb = new StringBuilder();
        sb.append(data.charAt(pos));
        pos += 1;

        while (pos < data.length() && Character.isDigit(data.charAt(pos))) {
            sb.append(data.charAt(pos));
            pos++;
        }

        pos--;
        try {
            number = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new OverflowException("overflow");
        }
        curToken = Token.NUM;
    }

    public String nextVar() {
        StringBuilder sb = new StringBuilder();

        char nextChar;
        if (pos < data.length()) {
            nextChar = data.charAt(pos);
            if (nextChar == '$' || data.charAt(pos) == '_' ||
                    Character.isLetter(data.charAt(pos))) {

                while (pos < data.length() && (nextChar == '$' ||
                        nextChar == '_' || Character.isLetter(nextChar) ||
                        Character.isDigit(nextChar))) {

                    sb.append(nextChar);
                    pos++;
                    if (pos < data.length()) {
                        nextChar = data.charAt(pos);
                    } else {
                        break;
                    }
                }
            }
        }
        pos--;
        return sb.toString();
    }

    public void nextToken() {
        skipWhitespace();
        if (pos >= data.length()) {
            curToken = Token.END;
            return;
        }

        char ch = data.charAt(pos);
        switch (ch) {
            case '*' -> curToken = Token.MUL;
            case '/' -> curToken = Token.DIV;
            case '+' -> curToken = Token.ADD;
            case '&' -> curToken = Token.AND;
            case '^' -> curToken = Token.XOR;
            case '|' -> curToken = Token.OR;
            case '-' -> {
                if (curToken == Token.NUM || curToken == Token.VAR || curToken == Token.RP) {
                    curToken = Token.SUB;
                } else {
                    if (pos == data.length() - 1) {
                        throw new ParserException("No last argument, in position " + (pos + 1));
                    }
                    if (Character.isDigit(data.charAt(pos + 1))) {
                        nextInt();
                    }else {
                        curToken = Token.NEG;
                    }
                }
            }
            case '(', '{', '[' -> {
                curBracket = Character.toString(ch);

                if (curToken == Token.RP || curToken == Token.NUM || curToken == Token.VAR) {
                    throw new ParserException("No operator, in position " + (pos + 1));
                }
                balance += 1;
                curToken = Token.LP;
            }
            case ')', '}', ']' -> {

                curBracket = Character.toString(ch);
                if (Token.isBinaryOp(curToken) || curToken == Token.BEGIN) {
                    throw new ParserException("No last argument, in position " + (pos + 1));
                }
                if(curToken == Token.LP ){
                    throw new ParserException("No expression in bracket, in position " + pos);
                }
                balance -= 1;
                if (balance < 0) {
                    throw new ParserException("No opening parenthesis, in position " + pos);
                }
                curToken = Token.RP;
            }
            case 'm' -> {
                if (pos == 0) {
                    throw new ParserException("No first argument, in position " + pos);
                }
                if (Character.isDigit(data.charAt(pos - 1)) && Character.isDigit(data.charAt(pos + 3))) {
                    throw new ParserException("Expected value: min or max, in position " + pos);
                }
                if (data.startsWith("max", pos)) {
                    pos += 2;
                    curToken = Token.MAX;
                } else if ((data.startsWith("min", pos))) {
                    pos += 2;
                    curToken = Token.MIN;
                } else {
                    throw new ParserException("Expected value: min or max, in position" + pos);
                }
            }
            default -> {
                if (Character.isDigit(ch)) {
                    if (curToken == Token.RP || curToken == Token.NUM || curToken == Token.VAR) {
                        throw new ParserException("Spaces in number, in position " + pos);
                    }
                    nextInt();
                } else {
                    varName = nextVar();

                    if (variables.contains(varName)) {
                        curToken = Token.VAR;
                    } else {
                        throw new ParserException("Unexpected value: " + varName + ", in position " + (pos + 1));
                    }
                }
            }
        }
        if( Token.isBinaryOp(curToken)){
            checkBinaryOperation();
        }
        prevToken = curToken;
        pos++;
    }

    public void checkBinaryOperation() {
        if (Token.isBinaryOp(prevToken) ){
            throw new ParserException("No middle argument, in position " + (pos - 1));
        } else if (prevToken == Token.LP || prevToken == Token.BEGIN) {
            throw new ParserException("No first argument, in position " + pos);
        }
    }


    public Token getCurToken() {
        return curToken;
    }

    public int getNumber() {
        return number;
    }

    public String getVarName() {
        return varName;
    }

    public String getCurBracket() {
        return curBracket;
    }

    public String getPairBracket(String openBracket) {
        return pairs.get(openBracket);
    }

    public int getVarIndex() {
        return variables.indexOf(varName);
    }
    public int getPos(){
        return pos;
    }
}

