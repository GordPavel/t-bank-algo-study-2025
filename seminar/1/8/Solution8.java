public static int countGreaterPairs(int[] a, int[] b) {
    int i = 0, j = 0;
    int count = 0;

    while (i < a.length && j < b.length) {
        if (a[i] > b[j]) {
            count += a.length - i; // Все оставшиеся элементы в a больше b[j]
            j++;
        } else {
            i++;
        }
    }

    return count;
}

public static void main(String[] args) {
    int[] a = {1, 3, 5, 7, 9};
    int[] b = {2, 4, 6, 8, 10};
    System.out.println("Количество пар, где ai > bj: " + countGreaterPairs(a, b));
}