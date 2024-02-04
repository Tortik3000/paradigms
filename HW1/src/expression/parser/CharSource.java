package expression.parser;

public interface CharSource {

    void skipWhitespace();

    void nextToken();
}
