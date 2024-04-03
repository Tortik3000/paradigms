package expression;


import expression.generic.GenericTabulator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        GenericTabulator gn = new GenericTabulator();
        Object[][][] ans = gn.tabulate(args[0], args[1], -2, 2, -2, 2, -2, 2);

        System.out.println(Arrays.deepToString(ans));
    }
}