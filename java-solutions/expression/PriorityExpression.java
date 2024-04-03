package expression;

import expression.typeEvaluaters.Type;

public interface PriorityExpression extends TripleExpression, Expression, ListExpression {
    int getPriority();

    boolean isSim();

    <T> T evaluate(T x, T y, T z, Type<T> type);
}
