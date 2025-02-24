import java.util.Stack;

class Solution3 {

    public static void main(String[] args) {
        PostfixEvaluator postfixEvaluator = new PostfixEvaluator();
        System.out.println(postfixEvaluator.evaluate(new String[]{"4", "1", "2", "+", "3", "*", "-"}));
    }

    static class PostfixEvaluator {

        public int evaluate(String[] tokens) {
            Stack<Integer> stack = new Stack<>();

            for (String token : tokens) {
                if (isOperator(token)) {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(applyOperation(token, a, b));
                } else {
                    stack.push(Integer.parseInt(token));
                }
            }

            return stack.pop();
        }

        private boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
        }

        private int applyOperation(String operator, int a, int b) {
            return switch (operator) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> a / b;
                default -> throw new UnsupportedOperationException("Unsupported operator: " + operator);
            };
        }
    }
}