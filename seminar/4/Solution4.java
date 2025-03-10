public static int findMaxCoveredPoint(int[] l, int[] r) {
    int n = l.length;
    List<int[]> events = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        events.add(new int[]{l[i], 1});   // Начало отрезка
        events.add(new int[]{r[i], -1});  // Конец отрезка
    }

    // Сортируем события: по координате, при равенстве концы идут раньше начал
    events.sort((a, b) -> {
        if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
        return Integer.compare(b[1], a[1]); // Конец отрезка перед началом
    });

    int count = 0, maxCount = 0;
    int bestPoint = 0;
    for (int[] event : events) {
        count += event[1];
        if (count > maxCount) {
            maxCount = count;
            bestPoint = event[0];
        }
    }
    return bestPoint;
}