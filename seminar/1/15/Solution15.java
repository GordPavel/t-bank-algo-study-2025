public static int maxMinDifference(int[] arr, int k) {
    Arrays.sort(arr);
    int minDiff = Integer.MAX_VALUE;
    for (int i = 0; i <= arr.length - k; i++) {
        minDiff = Math.min(minDiff, arr[i + k - 1] - arr[i]);
    }
    return minDiff;
}

public static void main(String[] args) {
    int[] arr = {1, 2, 3, 4, 10, 20, 30, 40, 100, 200};
    int k = 4;
    System.out.println("Максимальная минимальная разница: " + maxMinDifference(arr, k));
}