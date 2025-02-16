public static int search(int[] arr, int x) {
    int pivot = findPivot(arr, 0, arr.length - 1);
    if (pivot == -1) {
        return binarySearch(arr, 0, arr.length - 1, x);
    }
    if (arr[pivot] == x) {
        return pivot;
    }
    if (arr[0] <= x) {
        return binarySearch(arr, 0, pivot - 1, x);
    }
    return binarySearch(arr, pivot + 1, arr.length - 1, x);
}

private static int findPivot(int[] arr, int low, int high) {
    if (high < low) {
        return -1;
    }
    if (high == low) {
        return low;
    }
    int mid = (low + high) / 2;
    if (mid < high && arr[mid] > arr[mid + 1]) {
        return mid;
    }
    if (mid > low && arr[mid] < arr[mid - 1]) {
        return mid - 1;
    }
    if (arr[low] >= arr[mid]) {
        return findPivot(arr, low, mid - 1);
    }
    return findPivot(arr, mid + 1, high);
}

private static int binarySearch(int[] arr, int low, int high, int key) {
    if (high < low) {
        return -1;
    }
    int mid = (low + high) / 2;
    if (key == arr[mid]) {
        return mid;
    }
    if (key > arr[mid]) {
        return binarySearch(arr, mid + 1, high, key);
    }
    return binarySearch(arr, low, mid - 1, key);
}

public static void main(String[] args) {
    int[] arr =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5,
                    4, 3, 2, 1};
    int x = 10;
    System.out.println("Индекс элемента: " + search(arr, x));
}