public static int countEqualPairs(int[] a, int[] b) {
    int i = 0, j = 0;
    int count = 0;

    while (i < a.length && j < b.length) {
        if (a[i] == b[j]) {
            count++;
            i++;
            j++;
        } else if (a[i] < b[j]) {
            i++;
        } else {
            j++;
        }
    }

    return count;
}

public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {2, 4, 6, 8, 10};
    System.out.println("Количество пар с одинаковыми элементами: " + countEqualPairs(a, b));
}