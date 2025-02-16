public static int maxUnits(int[] a, int[] b, int d, int[] di) {
    int units = 0;
    int gold = d;
    for (int i = 0; i < a.length; i++) {
        int needed = a[i] - b[i];
        if (needed > 0) {
            int canBuy = Math.min(needed, gold / di[i]);
            needed -= canBuy * di[i];
            gold -= canBuy * di[i];
            if (needed <= 0) {
                units++;
            }
        } else {
            units++;
        }
    }
    return units;
}

public static void main(String[] args) {
    int[] a = {10, 20, 30};
    int[] b = {5, 10, 15};
    int d = 100;
    int[] di = {2, 3, 4};
    System.out.println("Максимальное количество юнитов: " + maxUnits(a, b, d, di));
}