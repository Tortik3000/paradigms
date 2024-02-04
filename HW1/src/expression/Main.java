package expression;

import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {
//        Expression exp = new Add(
//                                new Subtract(
//                                    new Multiply(
//                                            new Variable("x"),
//                                            new Variable("x")
//                                    ),
//                                    new Multiply(
//                                            new Const(2),
//                                            new Variable("x"))
//                                ),
//                                new Const(1)
//                        );



        ExpressionParser expPars = new ExpressionParser();
        TripleExpression exp = new Const(0);
        try {
            exp = expPars.parse("1000000*x*x*x*x*x/(x-1)");

            System.out.println(exp.evaluate(5, 0, 0));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

//        System.out.println(exp);
//        System.out.println(exp.toMiniString());


    }
}