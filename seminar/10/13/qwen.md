Для решения задачи о нахождении максимальной минимальной надежности моста на пути из острова `s` в остров `t`, при
условии, что суммарное время прохождения пути меньше заданного значения `C`, используется **бинарный поиск по надежности
** в сочетании с **алгоритмом Дейкстры** для проверки существования пути.

---

## 🧠 Основная идея

1. **Бинарный поиск по надежности**:
    - Ищем максимальное значение `X`, такое что в графе остаются только мосты с надежностью `≥ X`.
    - Для каждого `X` проверяем, существует ли путь из `s` в `t`, суммарное время которого меньше `C`.

2. **Алгоритм Дейкстры**:
    - Используется для нахождения кратчайшего пути из `s` в `t` в графе, где учитываются только мосты с
      надежностью `≥ X`.

---

## ⚙️ Шаги решения

1. **Чтение входных данных**:
    - Количество островов `n`, количество мостов `m`.
    - Список мостов, заданных как `(u, v, time, reliability)`.

2. **Сбор всех уникальных значений надежности**:
    - Сортируем их для последующего бинарного поиска.

3. **Бинарный поиск**:
    - Для каждого значения `X` в диапазоне надежности проверяем, существует ли путь из `s` в `t` с суммарным
      временем `< C` и минимальной надежностью `≥ X`.

4. **Проверка пути с помощью алгоритма Дейкстры**:
    - На каждом шаге бинарного поиска запускаем Дейкстру, учитывая только рёбра с надежностью `≥ X`.

---

## 💻 Реализация на Java

```java
import java.util.*;

class Edge {
    int to;
    int time;
    int reliability;

    public Edge(int to, int time, int reliability) {
        this.to = to;
        this.time = time;
        this.reliability = reliability;
    }
}

public class MaxMinReliabilityPath {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // количество островов
        int m = scanner.nextInt(); // количество мостов

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        Set<Integer> reliabilitySet = new HashSet<>();

        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt() - 1;
            int v = scanner.nextInt() - 1;
            int time = scanner.nextInt();
            int reliability = scanner.nextInt();
            adj.get(u).add(new Edge(v, time, reliability));
            adj.get(v).add(new Edge(u, time, reliability));
            reliabilitySet.add(reliability);
        }

        int s = scanner.nextInt() - 1;
        int t = scanner.nextInt() - 1;
        int C = scanner.nextInt();

        List<Integer> reliabilities = new ArrayList<>(reliabilitySet);
        Collections.sort(reliabilities);

        int left = 0;
        int right = reliabilities.size() - 1;
        int answer = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int currentReliability = reliabilities.get(mid);

            int shortestTime = dijkstra(s, t, currentReliability, adj, n);
            if (shortestTime < C && shortestTime != Integer.MAX_VALUE) {
                answer = currentReliability;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println("Максимальная минимальная надежность: " + answer);
    }

    private static int dijkstra(int s, int t, int minReliability, List<List<Edge>> adj, int n) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[] {0, s});

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[1];
            int currTime = current[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : adj.get(u)) {
                if (edge.reliability < minReliability) continue;

                int v = edge.to;
                int newTime = currTime + edge.time;

                if (!visited[v] && newTime < dist[v]) {
                    dist[v] = newTime;
                    pq.offer(new int[] {newTime, v});
                }
            }
        }

        return dist[t];
    }
}
```

---

## 📊 Пример работы программы

### Вход:

```
3
3
1 2 5 3
2 3 4 2
1 3 10 5
1
3
9
```

### Выход:

```
Максимальная минимальная надежность: 2
```

### Объяснение:

- Возможные пути:
    - Путь `1 → 2 → 3`: время = 5 + 4 = 9, минимальная надежность = 2.
    - Путь `1 → 3`: время = 10 > 9 → не подходит.
- Максимальная минимальная надежность = `2`.

---

## 📈 Анализ сложности

| Параметр          | Сложность                  |
|-------------------|----------------------------|
| Бинарный поиск    | `O(log R)`                 |
| Алгоритм Дейкстры | `O((V + E) log V)`         |
| Общая сложность   | `O((V + E) log V * log R)` |

Где:

- `R` — количество уникальных значений надежности.
- `V` — количество вершин.
- `E` — количество рёбер.

---

## ✅ Вывод

Этот подход эффективно решает задачу поиска максимальной минимальной надежности моста на пути из `s` в `t`, при условии,
что суммарное время прохождения пути меньше `C`. Использование бинарного поиска и алгоритма Дейкстры гарантирует
оптимальное решение за приемлемое время даже для больших графов.