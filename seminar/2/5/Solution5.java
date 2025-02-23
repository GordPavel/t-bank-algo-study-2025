import java.util.Arrays;
import java.util.Stack;

class Solution5 {

    public static void main(String[] args) {
        InfixToPostfix infixToPostfix = new InfixToPostfix();
        System.out.println(Arrays.toString(infixToPostfix.convert(new char[]{
            '(', '4', '-', '(', '(', '1', '+', '2', ')', '*', '3', ')', ')'
        })));
    }

    static class InfixToPostfix {
        public char[] convert(char[] tokens) {
            StringBuilder output = new StringBuilder();
            Stack<Character> operators = new Stack<>();

            for (char token : tokens) {
                if (Character.isDigit(token)) {
                    output.append(token);
                } else if (token == '(') {
                    operators.push(token);
                } else if (token == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.append(operators.pop());
                    }
                    operators.pop();
                } else if (isOperator(token)) {
                    while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                        output.append(operators.pop());
                    }
                    operators.push(token);
                }
            }

            while (!operators.isEmpty()) {
                output.append(operators.pop());
            }

            return output.toString().toCharArray();
        }

        private boolean isOperator(char token) {
            return token == '+' || token == '-' || token == '*' || token == '/';
        }

        private int precedence(char operator) {
            return switch (operator) {
                case '+', '-' -> 1;
                case '*', '/' -> 2;
                default -> -1;
            };
        }
    }
}