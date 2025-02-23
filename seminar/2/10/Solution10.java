PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

public void add(int value) {
    // Добавление в мультистек как в задаче 9
    // Обновление мин-кучи
    int currentStack = 0;
    while (currentStack < stacks.size() && !stacks.get(currentStack).isEmpty() && stacks.get(currentStack).get(0) > value) {
        currentStack++;
    }
    if (currentStack < stacks.size()) {
        minHeap.remove(new int[]{stacks.get(currentStack).get(0), currentStack});
        stacks.get(currentStack).add(0, value);
        minHeap.add(new int[]{value, currentStack});
    } else {
        stacks.get(currentStack).add(value);
        minHeap.add(new int[]{value, currentStack});
    }
}

public int removeMin() {
    int[] minElement = minHeap.poll();
    int value = minElement[0];
    int stackIndex = minElement[1];
    stacks.get(stackIndex).remove(0);
    if (!stacks.get(stackIndex).isEmpty()) {
        minHeap.add(new int[]{stacks.get(stackIndex).get(0), stackIndex});
    }
    return value;
}