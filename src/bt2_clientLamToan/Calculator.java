package bt2_clientLamToan;

import java.util.Stack;

public class Calculator {
    public static Double calculator (String Str) {
        Str = Str.replaceAll(" ", "");

        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        char[] tokens = Str.toCharArray();

        for (int i=0; i<tokens.length; i++) {
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i]=='.') {
                String sNum = "";
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i]=='.')){
                    sNum+=(tokens[i++])+"";
                }
                i--;
                values.push(Double.parseDouble(sNum));
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!ops.empty() && isHigher(ops.peek(), tokens[i])) {
                    values.push(calc(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            } else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (ops.peek() != '(') {
                    values.push(calc(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else {
                throw new UnsupportedOperationException("Input String has some errors");
            }
        }

        while (!ops.isEmpty()) {
            values.push(calc(ops.pop(), values.pop(), values.pop()));
        }

        Double res = values.pop();

        if (!ops.isEmpty() || !values.isEmpty()) {
            throw new UnsupportedOperationException("Input String has some errors");
        }

        return res;
    }

    public static Boolean isHigher(char op1, char op2) {
        if (op1 == '(' || op1 == ')')
            return false;
        if ((op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/'))
            return false;
        return true;
    }

    public static Double calc(char op, Double b, Double a) {
        if (op == '+')
            return a + b;
        if (op == '-')
            return a - b;
        if (op == '*')
            return a * b;
        if (op == '/') {
            if (b != 0)
                return a / b;
            else
                throw new UnsupportedOperationException("Cannot divide by zero");
        }
        return 0.0;
    }

    public static void log(Object o) {
        System.out.println(o);
    }
}