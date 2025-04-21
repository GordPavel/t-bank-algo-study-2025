Давайте разберем четвертую задачу, которая связана с обработкой запросов над строкой из круглых скобок.

Задача 4: Строка из круглых скобок

У нас есть строка из n круглых скобок, и мы должны поддерживать следующие операции:

Изменить i-ю скобку.
Проверить, является ли правильной скобочной последовательностью подстрока с l до r.

Решение

Для решения этой задачи мы будем использовать дерево отрезков (Segment Tree). Дерево отрезков позволяет эффективно выполнять запросы на отрезках массива, такие как нахождение суммы, минимума и т.д. В данном случае мы будем использовать его для проверки правильности скобочной последовательности.

Шаги решения:

Построение дерева отрезков:
Каждый узел дерева будет хранить информацию о балансе скобок на соответствующем отрезке, а также минимальный баланс.

Обновление элемента:
При изменении i-й скобки мы обновляем соответствующий узел в дереве отрезков и все его предки.

Запрос на правильность скобочной последовательности:
Для проверки правильности скобочной последовательности на отрезке от l до r, мы будем использовать дерево отрезков для объединения информации из соответствующих узлов.

Вот пример реализации на Java:

class SegmentTree {
private int[] balance;
private int[] minBalance;
private int n;

    public SegmentTree(String s) {
        n = s.length();
        balance = new int[4 * n];
        minBalance = new int[4 * n];
        build(s, 0, 0, n - 1);
    }

    private void build(String s, int node, int start, int end) {
        if (start == end) {
            balance[node] = (s.charAt(start) == '(') ? 1 : -1;
            minBalance[node] = balance[node];
        } else {
            int mid = (start + end) / 2;
            build(s, 2 * node + 1, start, mid);
            build(s, 2 * node + 2, mid + 1, end);
            merge(node);
        }
    }

    private void merge(int node) {
        balance[node] = balance[2 * node + 1] + balance[2 * node + 2];
        minBalance[node] = Math.min(minBalance[2 * node + 1], balance[2 * node + 1] + minBalance[2 * node + 2]);
    }

    public void update(int idx, char newChar, int node, int start, int end) {
        if (start == end) {
            balance[node] = (newChar == '(') ? 1 : -1;
            minBalance[node] = balance[node];
        } else {
            int mid = (start + end) / 2;
            if (idx = 0;
        }
        int mid = (start + end) / 2;
        boolean leftValid = isValid(L, R, 2 * node + 1, start, mid);
        boolean rightValid = isValid(L, R, 2 * node + 2, mid + 1, end);
        int leftBalance = queryBalance(L, R, 2 * node + 1, start, mid);
        int rightMinBalance = queryMinBalance(L, R, 2 * node + 2, mid + 1, end);
        return leftValid && rightValid && (leftBalance + rightMinBalance >= 0);
    }

    private int queryBalance(int L, int R, int node, int start, int end) {
        if (R < start || end < L) {
            return 0;
        }
        if (L <= start && end <= R) {
            return balance[node];
        }
        int mid = (start + end) / 2;
        int left = queryBalance(L, R, 2 * node + 1, start, mid);
        int right = queryBalance(L, R, 2 * node + 2, mid + 1, end);
        return left + right;
    }

    private int queryMinBalance(int L, int R, int node, int start, int end) {
        if (R < start || end < L) {
            return Integer.MAX_VALUE;
        }
        if (L <= start && end <= R) {
            return minBalance[node];
        }
        int mid = (start + end) / 2;
        int left = queryMinBalance(L, R, 2 * node + 1, start, mid);
        int right = queryMinBalance(L, R, 2 * node + 2, mid + 1, end);
        return Math.min(left, queryBalance(L, R, 2 * node + 1, start, mid) + right);
    }
}

public class Main {
public static void main(String[] args) {
String s = "(()())";
SegmentTree st = new SegmentTree(s);

        // Пример проверки правильности скобочной последовательности на отрезке от 0 до 5
        boolean isValid = st.isValid(0, 5, 0, 0, s.length() - 1);
        System.out.println("Правильная скобочная последовательность: " + isValid);

        // Пример изменения скобки
        st.update(1, ')', 0, 0, s.length() - 1);

        // Повторная проверка после изменения
        isValid = st.isValid(0, 5, 0, 0, s.length() - 1);
        System.out.println("Правильная скобочная последовательность: " + isValid);
    }
}

Объяснение:

Построение дерева: Мы строим дерево отрезков, где каждый узел хранит баланс скобок и минимальный баланс на соответствующем отрезке.
Обновление: При изменении i-й скобки мы обновляем соответствующий узел и все его предки.
Запрос: Для проверки правильности скобочной последовательности мы объединяем результаты из соответствующих узлов дерева.

Если у вас есть вопросы по коду или вы хотите рассмотреть другую задачу, дайте знать!