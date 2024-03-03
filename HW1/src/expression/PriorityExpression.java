package expression;

public interface PriorityExpression extends TripleExpression, Expression, ListExpression{
    int getPriority();

    boolean isSim();
}
