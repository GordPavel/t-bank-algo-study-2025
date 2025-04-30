package task2;

class SegmentTree {
    private long[] tree;
    private long[] lazySet;
    private long[] lazyAdd;
    private int size;

    public SegmentTree(int n) {
        size = n;
        tree = new long[4 * n];
        lazySet = new long[4 * n];
        lazyAdd = new long[4 * n];
        for (int i = 0; i < lazySet.length; i++) {
            lazySet[i] = Long.MIN_VALUE; // -1 означает, что нет отложенного присвоения
        }
    }

    private void push(int node, int start, int end) {
        if (lazySet[node] != Long.MIN_VALUE) {
            long value = lazySet[node];
            tree[node] = value * (end - start + 1); // Применяем присвоение
            if (start != end) {
                lazySet[node * 2] = value;
                lazySet[node * 2 + 1] = value;
                lazyAdd[node * 2] = 0;
                lazyAdd[node * 2 + 1] = 0;
            }
            lazySet[node] = Long.MIN_VALUE;
        }

        if (lazyAdd[node] != 0) {
            long value = lazyAdd[node];
            tree[node] += value * (end - start + 1); // Применяем прибавление
            if (start != end) {
                lazyAdd[node * 2] += value;
                lazyAdd[node * 2 + 1] += value;
            }
            lazyAdd[node] = 0;
        }
    }

    public void updateSet(int l, int r, long x) {
        updateSet(1, 0, size - 1, l, r, x);
    }

    private void updateSet(int node, int start, int end, int l, int r, long x) {
        push(node, start, end);
        if (start > end || start > r || end < l) return;

        if (start >= l && end <= r) {
            lazySet[node] = x;
            push(node, start, end);
            return;
        }

        int mid = (start + end) / 2;
        updateSet(node * 2, start, mid, l, r, x);
        updateSet(node * 2 + 1, mid + 1, end, l, r, x);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    public void updateInvert(int l, int r) {
        updateInvert(1, 0, size - 1, l, r);
    }

    private void updateInvert(int node, int start, int end, int l, int r) {
        push(node, start, end);
        if (start > end || start > r || end < l) return;

        if (start >= l && end <= r) {
            lazyAdd[node] += -1; // Инвертируем значения
            push(node, start, end);
            return;
        }

        int mid = (start + end) / 2;
        updateInvert(node * 2, start, mid, l, r);
        updateInvert(node * 2 + 1, mid + 1, end, l, r);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    public long querySum(int l, int r) {
        return querySum(1, 0, size - 1, l, r);
    }

    private long querySum(int node, int start, int end, int l, int r) {
        push(node, start, end);
        if (start > end || start > r || end < l) return 0;

        if (start >= l && end <= r) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        long p1 = querySum(node * 2, start, mid, l, r);
        long p2 = querySum(node * 2 + 1, mid + 1, end, l, r);
        return p1 + p2;
    }

    public long queryMax(int l, int r) {
        return queryMax(1, 0, size - 1, l, r);
    }

    private long queryMax(int node, int start, int end, int l, int r) {
        push(node, start, end);
        if (start > end || start > r || end < l) return Long.MIN_VALUE;

        if (start >= l && end <= r) {
            return tree[node]; // Здесь нужно будет изменить логику для нахождения максимума
        }

        int mid = (start + end) / 2;
        long p1 = queryMax(node * 2, start, mid, l, r);
        long p2 = queryMax(node * 2 + 1, mid + 1, end, l, r);
        return Math.max(p1, p2);
    }

    public long queryElement(int index) {
        return queryElement(1, 0, size - 1, index);
    }

    private long queryElement(int node, int start, int end, int index) {
        push(node, start, end);
        if (start == end) {
            return tree[node] / (end - start + 1); // Возвращаем значение элемента
        }

        int mid = (start + end) / 2;
        if (index <= mid) {
            return queryElement(node * 2, start, mid, index);
        } else {
            return queryElement(node * 2 + 1, mid + 1, end, index);
        }
    }
}


public class Task2 {
    public static void main(String[] args) {
        int n = 10; // размер массива
        SegmentTree segmentTree = new SegmentTree(n);

        // Пример использования
        segmentTree.updateSet(0, 4, 5); // Установить 5 для индексов от 0 до 4
        segmentTree.updateInvert(0, 4); // Инвертировать значения от 0 до 4
        System.out.println(segmentTree.querySum(0, 9)); // Подсчитать сумму на отрезке
        System.out.println(segmentTree.queryMax(0, 9)); // Найти максимум на отрезке
        segmentTree.updateSet(0, 4, 3); // Установить 3 для индексов от 0 до 4
        segmentTree.updateInvert(0, 4); // Инвертировать значения от 0 до 4
        System.out.println(segmentTree.queryElement(2)); // Найти значение элемента по индексу 2
        System.out.println(segmentTree.querySum(0, 9)); // Подсчитать сумму на отрезке
    }
}