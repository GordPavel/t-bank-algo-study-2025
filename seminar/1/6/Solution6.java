public static boolean findPairWithSum(int[] a, int[] b, int S) {
    int i = 0, j = b.length - 1;

    while (i < a.length && j >= 0) {
        int sum = a[i] + b[j];
        if (sum == S) {
            return true;
        } else if (sum < S) {
            i++;
        } else {
            j--;
        }
    }

    return false;
}

public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {2, 4, 6, 8, 10};
    int S = 7;
    System.out.println("Найдена пара с суммой " + S + ": " + findPairWithSum(a, b, S));
}