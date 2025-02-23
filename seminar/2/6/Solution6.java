public int[] restoreOriginalArray(int[] arr) {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < arr.length - 1; ) {
        if (arr[i] == arr[i + 1]) {
            i += 2;
        } else {
            result.add(arr[i]);
            i++;
        }
    }
    // Добавляем последний элемент, если он не был дубликатом
    if (i < arr.length) {
        result.add(arr[i]);
    }
    return result.stream().mapToInt(Integer::intValue).toArray();
}