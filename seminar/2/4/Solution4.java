import java.util.Stack;

class Solution4 {

    public static void main(String[] args) {
        InfixEvaluator infixEvaluator = new InfixEvaluator();
        System.out.println(infixEvaluator.evaluate(new char[]{
            '(', '4', '-', '(', '(', '1', '+', '2', ')', '*', '3', ')', ')'
        }));
    }

    static class InfixEvaluator {

        public int evaluate(char[] tokens) {
            Stack<Integer> operands = new Stack<>();
            Stack<Character> operators = new Stack<>();
            for (int i = 0; i < tokens.length; i++) {
                char token = tokens[i];

                if (Character.isDigit(token)) {
                    StringBuilder sb = new StringBuilder();
                    while (i < tokens.length && Character.isDigit(tokens[i])) {
                        sb.append(tokens[i++]);
                    }
                    operands.push(Integer.parseInt(sb.toString()));
                    i--;
                } else if (token == '(') {
                    operators.push(token);
                } else if (token == ')') {
                    while (operators.peek() != '(') {
                        operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                    }
                    operators.pop();
                } else if (isOperator(token)) {
                    while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                        operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                    }
                    operators.push(token);
                }
            }

            while (!operators.isEmpty()) {
                operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
            }

            return operands.pop();
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

        private int applyOperation(char operator, int b, int a) {
            return switch (operator) {
                case '+' -> a + b;
                case '-' -> a - b;
                case '*' -> a * b;
                case '/' -> a / b;
                default -> 0;
            };
        }
    }
}