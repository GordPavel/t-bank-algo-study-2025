import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MultiStack {
    private List<Stack<Integer>> stacks = new ArrayList<>();
    private int totalElements = 0;

    public void push(int value) {
        int currentStack = 0;
        Stack<Integer> current = getStack(currentStack);
        current.push(value);
        totalElements++;

        // Проверка на переполнение и перекладка
        while (current.size() > Math.pow(2, currentStack)) {
            Stack<Integer> nextStack = getStack(currentStack + 1);
            while (!current.isEmpty()) {
                nextStack.push(current.pop());
            }
            currentStack++;
            current = nextStack;
        }
    }

    private Stack<Integer> getStack(int index) {
        if (index >= stacks.size()) {
            stacks.add(new Stack<>());
        }
        return stacks.get(index);
    }

    public static void main(String[] args) {
        MultiStack ms = new MultiStack();
        for (int i = 0; i < 10; i++) {
            ms.push(i);
        }
    }
}