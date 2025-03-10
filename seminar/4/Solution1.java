public class RSQ1D {
    private int[] prefix;

    public RSQ1D(int[] a) {
        int n = a.length;
        prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + a[i];
        }
    }

    // Возвращает сумму на отрезке [l, r] (включительно)
    public int query(int l, int r) {
        return prefix[r + 1] - prefix[l];
    }
}