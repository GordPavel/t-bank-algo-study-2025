public static int minMaxDifference(int[] arr, int k) {
    Arrays.sort(arr);
    int minDiff = Integer.MAX_VALUE;
    for (int i = 0; i <= arr.length - 2 * k; i++) {
        int maxDiff = 0;
        for (int j = 0; j < k; j++) {
            maxDiff = Math.max(maxDiff, arr[i + 2 * j + 1] - arr[i + 2 * j]);
        }
        minDiff = Math.min(minDiff, maxDiff);
    }
    return minDiff;
}

public static void main(String[] args) {
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int k = 2;
    System.out.println("Минимальная максимальная разница: " + minMaxDifference(arr, k));
}