public static int findMinDifference(int[] a, int[] b) {
    int i = 0, j = 0;
    int minDiff = Integer.MAX_VALUE;

    while (i < a.length && j < b.length) {
        if (a[i] == b[j]) {
            return 0; // Если элементы равны, минимальная разница 0
        } else {
            minDiff = Math.min(minDiff, Math.abs(a[i] - b[j]));
            if (a[i] < b[j]) {
                i++;
            } else {
                j++;
            }
        }
    }

    return minDiff;
}

public static void main(String[] args) {
    int[] a = {1, 4, 6, 8};
    int[] b = {2, 3, 7, 9};
    System.out.println("Минимальная разница: " + findMinDifference(a, b));
}