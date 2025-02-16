public static int minCostToWin(int[] a, int s) {
    int maxVotes = 0;
    for (int votes : a) {
        maxVotes = Math.max(maxVotes, votes);
    }
    int cost = 0;
    for (int votes : a) {
        if (votes == maxVotes) {
            continue;
        }
        int needed = maxVotes - votes + 1;
        cost += needed * s;
    }
    return cost;
}

public static void main(String[] args) {
    int[] a = {10, 20, 30};
    int s = 5;
    System.out.println("Минимальная стоимость: " + minCostToWin(a, s));
}