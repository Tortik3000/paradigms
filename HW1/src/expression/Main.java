package expression;

import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {




        ExpressionParser expPars = new ExpressionParser();
        TripleExpression exp = new Const(0);
        try {
            exp = expPars.parse("(({(0 + -30)})]");

            System.out.println(exp.evaluate(5, 0, 0));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

//        System.out.println(exp);
//        System.out.println(exp.toMiniString());


    }
}