public void add(int value) {
    int currentStack = 0;
    while (currentStack < stacks.size() && stacks.get(currentStack).size() == (1 << currentStack)) {
        List<Integer> nextStack = new ArrayList<>();
        if (currentStack + 1 < stacks.size()) {
            nextStack = stacks.get(currentStack + 1);
        } else {
            stacks.add(nextStack);
        }
        nextStack.addAll(stacks.get(currentStack));
        Collections.sort(nextStack);
        stacks.set(currentStack + 1, nextStack);
        currentStack++;
    }
    stacks.get(currentStack).add(value);
}