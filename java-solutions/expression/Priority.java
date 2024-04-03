package expression;

import expression.exceptions.ListParser;

public interface Priority extends TripleExpression, Expression, ListExpression{
    int getPriority();

    boolean isSim();
}
