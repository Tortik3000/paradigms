package expression.generic;

import expression.PriorityExpression;
import expression.exceptions.ExpressionParser;
import expression.typeEvaluaters.*;


import java.util.Map;

public class GenericTabulator implements Tabulator {

    private static final Map<String, Type<?>> types = Map.of(
            "i", new IntegerOverflowType(),
            "d", new DoubleType(),
            "bi", new BigIntegerType(),
            "u", new IntegerType(),
            "b", new ByteType(),
            "bool", new BooleanType()
    );

    public Object[][][] tabulate(String mode, String expression,
                                 int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        ExpressionParser expressionParser = new ExpressionParser();
        PriorityExpression expr = expressionParser.parse(expression, types.get(mode));
        return tabulate(types.get(mode), expr, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulate(Type<T> type, PriorityExpression expr,
                                     int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {

                    try {
                        // :NOTE: getFromInt insted of type.constant(Integer.toString
                        ans[i][j][k] = expr.evaluate(type.constant(Integer.toString(i + x1)),
                                type.constant(Integer.toString(j + y1)), type.constant(Integer.toString((k + z1))), type);
                    } catch (ArithmeticException e) {
                        ans[i][j][k] = null;
                    }
                }
            }
        }
        return ans;
    }
}
