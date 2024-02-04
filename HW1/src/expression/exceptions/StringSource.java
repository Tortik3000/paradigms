package expression.exceptions;


import java.util.List;


public class StringSource {
    private final String data;
    private int pos;

    private Token curToken = Token.BEGIN;

    private int number;
    private String varName;
    private int balance = 0;

    private final List<String> vars = List.of("x", "y", "z");

    private final List<Token> binaryOperationToken = List.of(Token.MIN, Token.MAX, Token.ADD, Token.SUB, Token.MUL, Token.DIV, Token.AND, Token.XOR, Token.OR);
    private final List<String> binaryOperation = List.of("+", "*", "/", "|", "&", "^");
    public StringSource(final String data) {
        this.data = data;
    }

    public void skipWhitespace() {
        while (pos < data.length() && Character.isWhitespace(data.charAt(pos))) {
            pos += 1;
        }
    }

    public void nextInt(char ch) {
        StringBuilder sb = new StringBuilder();
        sb.append(ch);
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

    public void nextToken() {
        skipWhitespace();
        if (pos >= data.length()) {
            curToken = Token.END;
            return;
        }

        char ch = data.charAt(pos);
        if (binaryOperation.contains(Character.toString(ch))) {
            checkBinaryOperation();
        }
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
                        throw new ParserException("No last argument");
                    }
                    if (Character.isDigit(data.charAt(pos + 1))) {
                        nextInt(ch);
                        break;
                    }
                    curToken = Token.NEG;
                }
            }
            case '(' -> {
                if (curToken == Token.RP || curToken == Token.NUM || curToken == Token.VAR) {
                    throw new ParserException("No operator");
                }
                balance += 1;
                curToken = Token.LP;
            }
            case ')' -> {

                if (binaryOperationToken.contains(curToken) || curToken == Token.LP || curToken == Token.BEGIN) {
                    throw new ParserException("No last argument");
                }
                balance -= 1;
                if (balance < 0) {
                    throw new ParserException("No opening parenthesis");
                }
                curToken = Token.RP;
            }
            case 'l' -> {
                pos += 1;
                if (data.charAt(pos) == '1') {
                    curToken = Token.L1;
                } else {
                    throw new ParserException("Expected value: l1");
                }
            }
            case 't' -> {
                pos += 1;
                if (data.charAt(pos) == '1') {
                    curToken = Token.T1;
                } else {
                    throw new ParserException("Expected value: t1");
                }
            }
            case 'm' -> {
                if (pos == 0) {
                    throw new ParserException("No first argument");
                }
                if (Character.isDigit(data.charAt(pos - 1)) && Character.isDigit(data.charAt(pos + 3))) {
                    throw new ParserException("Expected value: min or max");
                }
                if (data.startsWith("max", pos)) {
                    pos += 2;
                    checkBinaryOperation();
                    curToken = Token.MAX;
                } else if ((data.startsWith("min", pos))) {

                    pos += 2;
                    checkBinaryOperation();
                    curToken = Token.MIN;
                } else {
                    throw new ParserException("Expected value: min or max");
                }
            }
            default -> {
                if (Character.isDigit(ch)) {
                    if (curToken == Token.RP || curToken == Token.NUM || curToken == Token.VAR) {
                        throw new ParserException("Spaces in number");
                    }
                    nextInt(ch);
                } else if (vars.contains(Character.toString(ch))) {
                    varName = Character.toString(ch);
                    curToken = Token.VAR;
                } else {
                    throw new ParserException("Unexpected value: " + ch);
                }
            }
        }
        pos++;
    }

    public void checkBinaryOperation() {
        if (binaryOperationToken.contains(curToken)) {
            throw new ParserException("No middle argument");
        } else if (curToken == Token.LP || curToken == Token.BEGIN) {
            throw new ParserException("No first argument");
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
}
