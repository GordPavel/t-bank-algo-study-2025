package task1;

class SegmentTree {
    private int[] tree;
    private int[] lazySet;
    private int[] lazyFlip;
    private int n;

    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        lazySet = new int[4 * n];
        lazyFlip = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    private void propagate(int node, int start, int end) {
        if (lazySet[node] != -1) {
            tree[node] = (end - start + 1) * lazySet[node];
            if (start != end) {
                lazySet[2 * node + 1] = lazySet[node];
                lazySet[2 * node + 2] = lazySet[node];
                lazyFlip[2 * node + 1] = 0;
                lazyFlip[2 * node + 2] = 0;
            }
            lazySet[node] = -1;
        }
        if (lazyFlip[node] != 0) {
            tree[node] = (end - start + 1) - tree[node];
            if (start != end) {
                lazyFlip[2 * node + 1] ^= 1;
                lazyFlip[2 * node + 2] ^= 1;
            }
            lazyFlip[node] = 0;
        }
    }

    public void updateSet(int l, int r, int x) {
        updateSet(0, 0, n - 1, l, r, x);
    }

    private void updateSet(int node, int start, int end, int l, int r, int x) {
        propagate(node, start, end);
        if (start > end || start > r || end < l) return;
        if (start >= l && end <= r) {
            lazySet[node] = x;
            propagate(node, start, end);
            return;
        }
        int mid = (start + end) / 2;
        updateSet(2 * node + 1, start, mid, l, r, x);
        updateSet(2 * node + 2, mid + 1, end, l, r, x);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }

    public void updateFlip(int l, int r) {
        updateFlip(0, 0, n - 1, l, r);
    }

    private void updateFlip(int node, int start, int end, int l, int r) {
        propagate(node, start, end);
        if (start > end || start > r || end < l) return;
        if (start >= l && end <= r) {
            lazyFlip[node] ^= 1;
            propagate(node, start, end);
            return;
        }
        int mid = (start + end) / 2;
        updateFlip(2 * node + 1, start, mid, l, r);
        updateFlip(2 * node + 2, mid + 1, end, l, r);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }

    public int querySum(int l, int r) {
        return querySum(0, 0, n - 1, l, r);
    }

    private int querySum(int node, int start, int end, int l, int r) {
        propagate(node, start, end);
        if (start > end || start > r || end < l) return 0;
        if (start >= l && end <= r) return tree[node];
        int mid = (start + end) / 2;
        int p1 = querySum(2 * node + 1, start, mid, l, r);
        int p2 = querySum(2 * node + 2, mid + 1, end, l, r);
        return p1 + p2;
    }

    // Метод для нахождения ближайшей единицы к i
    public int findNearestOne(int i) {
        return findNearestOne(0, 0, n - 1, i);
    }

    private int findNearestOne(int node, int start, int end, int i) {
        propagate(node, start, end);
        if (start == end) {
            return tree[node] == 1 ? start : -1;
        }
        int mid = (start + end) / 2;
        if (i <= mid) {
            int leftResult = findNearestOne(2 * node + 1, start, mid, i);
            if (leftResult != -1) return leftResult;
            return findNearestOne(2 * node + 2, mid + 1, end, i);
        } else {
            int rightResult = findNearestOne(2 * node + 2, mid + 1, end, i);
            if (rightResult != -1) return rightResult;
            return findNearestOne(2 * node + 1, start, mid, i);
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        int[] a = {0, 1, 0, 1, 0, 1, 1, 0};
        SegmentTree segmentTree = new SegmentTree(a);

        // Пример использования
        segmentTree.updateSet(0, 4, 1); // Присвоить 1 элементам отрезка [0, 3] - {1, 1, 1, 1, 0, 1, 1, 0}
        System.out.println(segmentTree.querySum(0, 5)); // Найти число единиц на отрезке [0, 5] - 5
        segmentTree.updateFlip(2, 5); // Изменить значение всех элементов отрезка [2, 5] на противоположное - {1, 1, 0, 0, 1, 0, 1, 0}
        System.out.println(segmentTree.querySum(0, 5)); // Найти число единиц на отрезке [0, 5] - 3
        System.out.println(segmentTree.querySum(0, 7)); // Найти число единиц на отрезке [0, 7] - 4
        System.out.println(segmentTree.findNearestOne(2)); // Найти ближайшую к 2 единицу - 4
    }
}