Для решения задачи поиска **кратчайших расстояний от заданной вершины `s` до всех остальных вершин** во взвешенном графе используется один из самых известных алгоритмов — **алгоритм Дейкстры**.

---

## 🧠 Основная идея

Алгоритм Дейкстры находит кратчайшие пути **от одной начальной вершины до всех остальных**, при условии, что **веса рёбер неотрицательны**.

### Что он делает:
- Поддерживает множество непосещённых вершин.
- На каждом шаге выбирает вершину с минимальным текущим расстоянием и "релаксирует" все её соседей (обновляет расстояния).
- Использует **приоритетную очередь** для эффективного выбора следующей вершины.

---

## ⚙️ Шаги алгоритма

1. Инициализируем массив расстояний: `dist[i] = ∞`, кроме `dist[s] = 0`.
2. Используем **минимальную кучу (PriorityQueue)**, чтобы хранить пары `(расстояние, вершина)`.
3. Пока очередь не пуста:
    - Извлекаем вершину с минимальным расстоянием.
    - Для каждого соседа проверяем, можно ли улучшить его текущее расстояние через эту вершину.
4. Результат — массив `dist[]`, где `dist[i]` — кратчайшее расстояние от `s` до `i`.

---

## 💻 Реализация на Java

```java
import java.util.*;

class Edge {
    int to;
    int weight;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class Dijkstra {

    public static int[] dijkstra(int n, List<List<Edge>> graph, int start) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // PriorityQueue хранит пары (расстояние, вершина)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, start});

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int uDist = current[0];
            int u = current[1];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;

                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int n = 5; // количество вершин

        // Создаём граф как список смежности
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Добавляем рёбра
        graph.get(0).add(new Edge(1, 10));
        graph.get(0).add(new Edge(4, 5));
        graph.get(1).add(new Edge(2, 1));
        graph.get(1).add(new Edge(4, 2));
        graph.get(2).add(new Edge(3, 4));
        graph.get(3).add(new Edge(0, 7));
        graph.get(4).add(new Edge(1, 3));
        graph.get(4).add(new Edge(2, 9));
        graph.get(4).add(new Edge(3, 2));

        int start = 0;
        int[] distances = dijkstra(n, graph, start);

        System.out.println("Кратчайшие расстояния от вершины " + start + ":");
        for (int i = 0; i < n; i++) {
            System.out.println("до вершины " + i + ": " + distances[i]);
        }
    }
}
```

---

## 📊 Пример работы

### Входной граф:

```
0: [(1,10), (4,5)]
1: [(2,1), (4,3)]
2: [(3,4)]
3: [(0,7)]
4: [(1,3), (2,9), (3,2)]
```

### Запуск от вершины `0`:

```
до вершины 0: 0
до вершины 1: 8
до вершины 2: 9
до вершины 3: 7
до вершины 4: 5
```

---

## 📈 Анализ сложности

| Метрика | Сложность |
|--------|-----------|
| Временная (с использованием PriorityQueue) | O((V + E) log V) |
| Память | O(V + E) |

> Где `V` — количество вершин, `E` — количество рёбер.

---

## ✅ Вывод

Алгоритм Дейкстры — это мощный и часто используемый метод для поиска кратчайших путей в графах с неотрицательными весами. Его реализация на Java с использованием `PriorityQueue` позволяет эффективно обрабатывать даже относительно большие графы.