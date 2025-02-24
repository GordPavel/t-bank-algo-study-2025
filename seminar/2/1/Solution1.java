import java.util.Stack;

class Solution1 {

    static class MyQueue {

        private Stack<Integer> inputStack;
        private Stack<Integer> outputStack;

        public MyQueue() {
            inputStack = new Stack<>();
            outputStack = new Stack<>();
        }

        public void enqueue(int x) {
            inputStack.push(x);
        }

        public int dequeue() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            return outputStack.pop();
        }

        public int peek() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            return outputStack.peek();
        }

        public boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }
    }

    static class MyQueueWithOperations {

        private Stack<Integer> inputStack;
        private Stack<Integer> outputStack;
        private Stack<Integer> minStack;
        private Stack<Integer> maxStack;
        private int sum;

        public MyQueueWithOperations() {
            inputStack = new Stack<>();
            outputStack = new Stack<>();
            minStack = new Stack<>();
            maxStack = new Stack<>();
            sum = 0;
        }

        public void enqueue(int x) {
            inputStack.push(x);
            sum += x;

            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }

            if (maxStack.isEmpty() || x >= maxStack.peek()) {
                maxStack.push(x);
            }
        }

        public int dequeue() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    int element = inputStack.pop();
                    outputStack.push(element);

                    // Update minStack and maxStack for outputStack
                    if (minStack.peek() == element) {
                        minStack.pop();
                    }
                    if (maxStack.peek() == element) {
                        maxStack.pop();
                    }
                    sum -= element;
                }
            }
            return outputStack.pop();
        }

        public int peek() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            return outputStack.peek();
        }

        public boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }

        public int min() {
            return minStack.isEmpty() ? Integer.MAX_VALUE : minStack.peek();
        }

        public int max() {
            return maxStack.isEmpty() ? Integer.MIN_VALUE : maxStack.peek();
        }

        public int sum() {
            return sum;
        }

        public int gcd() {
            if (outputStack.isEmpty() && inputStack.isEmpty()) {
                return 0;
            }
            Stack<Integer> tempStack = new Stack<>();
            int currentGCD = 0;

            while (!outputStack.isEmpty()) {
                tempStack.push(outputStack.pop());
            }

            while (!tempStack.isEmpty() || !inputStack.isEmpty()) {
                int element;
                if (!tempStack.isEmpty()) {
                    element = tempStack.pop();
                    outputStack.push(element);
                } else {
                    element = inputStack.pop();
                    outputStack.push(element);
                }
                currentGCD = gcd(currentGCD, element);
            }

            return currentGCD;
        }

        private int gcd(int a, int b) {
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }
    }
}