package expression.parser;



import java.math.BigInteger;
import java.util.List;

public class StringSource{
    private final String data;
    private int pos;

    private Token curToken = Token.BEGIN;

    private BigInteger number;
    private String varName;

    private List<String> vars = List.of("x", "y", "z");

    public StringSource(final String data) {
        this.data = data;
    }



        public void skipWhitespace() {
        while (pos < data.length() && Character.isWhitespace(data.charAt(pos))) {
            pos += 1;
        }
    }

    public void nextInt(char ch){
        StringBuilder sb = new StringBuilder();
        sb.append(ch);
        pos += 1;

        while (pos < data.length() && Character.isDigit(data.charAt(pos))) {
            sb.append(data.charAt(pos));
            pos++;
        }

        pos--;
        number = new BigInteger(sb.toString());
        curToken = Token.NUM;
    }
    public void nextToken() {
        skipWhitespace();
        if (pos >= data.length()) {
            curToken = Token.END;
            return ;
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
                    if (Character.isDigit(data.charAt(pos + 1))) {
                        nextInt(ch);
                        break;
                    }
                    curToken = Token.NEG;
                }
            }
            case '(' -> curToken = Token.LP;
            case ')' -> curToken = Token.RP;
            case 'l' -> {
                pos += 1;
                if (data.charAt(pos) == '1') {
                    curToken = Token.L1;
                } else {
                    throw new IllegalStateException("Unexpected value: l");
                }
            }
            case 't' -> {
                pos += 1;
                if (data.charAt(pos) == '1') {
                    curToken = Token.T1;
                } else {
                    throw new IllegalStateException("Unexpected value: t");
                }
            }
            default -> {
                if (Character.isDigit(ch)) {
                    nextInt(ch);
                } else if (vars.contains(Character.toString(ch))) {
                    varName = Character.toString(ch);
                    curToken = Token.VAR;
                }
            }
        }
        pos++;
    }


    public Token getCurToken(){
        return  curToken;
    }

    public BigInteger getNumber(){
        return number;
    }

    public String getVarName(){
        return varName;
    }
}
