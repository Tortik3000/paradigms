package expression;

public interface Priority extends TripleExpression, Expression{
    int getPriority();

    boolean isSim();
}
