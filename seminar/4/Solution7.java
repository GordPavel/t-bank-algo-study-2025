public static int[] countIntersections(int[] l, int[] r) {
    int n = l.length;
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) continue;
            // Проверяем пересечение отрезков i и j
            if (l[j] <= r[i] && r[j] >= l[i]) {
                result[i]++;
            }
        }
    }
    return result;
}