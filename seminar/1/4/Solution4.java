public static boolean hasCommonElement(int[] a, int[] b) {
    int i = 0, j = 0;

    while (i < a.length && j < b.length) {
        if (a[i] == b[j]) {
            return true;
        } else if (a[i] < b[j]) {
            i++;
        } else {
            j++;
        }
    }

    return false;
}

public static void main(String[] args) {
    int[] a = {1, 3, 5, 7, 9};
    int[] b = {2, 4, 6, 8, 10};
    System.out.println("Есть ли одинаковые числа: " + hasCommonElement(a, b));
}