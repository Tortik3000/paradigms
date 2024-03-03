package expression.generic;

import expression.PriorityExpression;
import expression.exceptions.ExpressionParser;

public class GenericTabulator implements Tabulator{

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception{
        ExpressionParser expressionParser = new ExpressionParser();
        PriorityExpression expr = expressionParser.parse(expression);
        Object[][][] ans = new Object[x2-x1][y2-y1][z2-z1];
        for (int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                for (int k = z1; k <= z2; k++){
                    ans[i][j][k] = expr.evaluate(i + x1, j + y1, k + z1);
                }
            }
        }
        return ans;
    }
}
