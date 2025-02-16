public static int maxElementsWithSum(int[] arr, int X) {
    int sum = 0;
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
        if (sum + arr[i] <= X) {
            sum += arr[i];
            count++;
        } else {
            break;
        }
    }
    return count;
}

public static void main(String[] args) {
    int[] arr = {1, 2, 3, 4, 5};
    int X = 10;
    System.out.println("Максимальное число элементов: " + maxElementsWithSum(arr, X));
}