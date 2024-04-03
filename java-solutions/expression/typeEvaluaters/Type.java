package expression.typeEvaluaters;

public interface Type<T> {
    T add(T num1, T num2);

    T subtract(T num1, T num2);

    T multiply(T num1, T num2);

    T divide(T num1, T num2);

    T negate(T num);

    T constant(String num);

//    T getFromInt(int num);

    T min(T num1, T num2);

    T max(T num1, T num2);

    T countBite(T num);


}

