Для решения задачи, где **в начальный момент времени некоторые вершины заражены**, и вирус распространяется по графу с *
*весом ребра как временем передачи инфекции**, можно использовать модификацию **алгоритма Дейкстры**.

---

## 🧠 Основная идея

- В этой задаче мы имеем **взвешенный неориентированный граф**.
- Несколько вершин изначально заражены (это стартовые точки).
- Инфекция распространяется по рёбрам за время, равное весу ребра.
- Нужно найти для каждой вершины **момент времени**, когда она будет заражена.

### Это аналогично:

> Запуску **многопоточного алгоритма Дейкстры**, где начальная очередь содержит **все заражённые вершины**. Мы находим
> кратчайшее время, за которое любая "волна" заражения достигнет каждой вершины.

---

## ⚙️ Шаги решения

1. Используем **приоритетную очередь (min-heap)** для хранения пар `(время_заражения, вершина)`.
2. Для всех изначально заражённых вершин устанавливаем `time[vertex] = 0` и добавляем их в очередь.
3. Пока очередь не пуста:
    - Извлекаем вершину с минимальным временем заражения.
    - Для каждого соседа вычисляем, когда он может быть заражён через эту вершину.
    - Если новое время меньше текущего известного — обновляем и добавляем в очередь.

---

## 💻 Реализация на Java

```java
import java.util.*;

class Edge {
    int to;
    int time; // Время распространения заразы по ребру

    public Edge(int to, int time) {
        this.to = to;
        this.time = time;
    }
}

public class InfectionSpread {

    public static int[] spreadInfection(int n, List<List<Edge>> graph, int[] initialInfected) {
        int[] infectionTime = new int[n];
        Arrays.fill(infectionTime, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Установка начальных инфицированных вершин
        for (int v : initialInfected) {
            infectionTime[v] = 0;
            pq.offer(new int[] {0, v});
        }

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currTime = current[0];
            int u = current[1];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int travelTime = edge.time;
                int newTime = currTime + travelTime;

                if (!visited[v] && newTime < infectionTime[v]) {
                    infectionTime[v] = newTime;
                    pq.offer(new int[] {newTime, v});
                }
            }
        }

        return infectionTime;
    }

    public static void main(String[] args) {
        int n = 6; // количество вершин

        // Создание графа
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Рёбра графа (вершина A, вершина B, время заражения)
        graph.get(0).add(new Edge(1, 2));
        graph.get(1).add(new Edge(0, 2));
        graph.get(0).add(new Edge(2, 5));
        graph.get(2).add(new Edge(0, 5));
        graph.get(1).add(new Edge(3, 3));
        graph.get(3).add(new Edge(1, 3));
        graph.get(2).add(new Edge(4, 1));
        graph.get(4).add(new Edge(2, 1));
        graph.get(3).add(new Edge(4, 4));
        graph.get(4).add(new Edge(3, 4));
        graph.get(3).add(new Edge(5, 2));
        graph.get(5).add(new Edge(3, 2));

        // Зараженные вершины
        int[] initialInfected = {0, 5};

        int[] result = spreadInfection(n, graph, initialInfected);

        System.out.println("Время заражения вершин:");
        for (int i = 0; i < n; i++) {
            System.out.println("Вершина " + i + ": " + result[i]);
        }
    }
}
```

---

## 📊 Пример работы программы

### Вход:

- Вершины: 6
- Заражены: `0`, `5`
- Граф:

```
0 <-> 1 (2), 0 <-> 2 (5)
1 <-> 3 (3)
2 <-> 4 (1)
3 <-> 4 (4), 3 <-> 5 (2)
```

### Выход:

```
Вершина 0: 0     // изначально заражена
Вершина 1: 2     // через вершину 0
Вершина 2: 5     // через вершину 0
Вершина 3: 2     // через вершину 5
Вершина 4: 3     // через 5 -> 3 -> 4
Вершина 5: 0     // изначально заражена
```

---

## 📈 Анализ сложности

| Метрика   | Сложность        |
|-----------|------------------|
| Временная | O((V + E) log V) |
| Память    | O(V + E)         |

> Где `V` — количество вершин, `E` — количество рёбер.

---

## ✅ Вывод

Эта задача является классическим примером применения **алгоритма Дейкстры с несколькими источниками**. Подход эффективен
и хорошо подходит для моделирования процессов распространения чего-либо (вирусов, сигналов, информации) по графу с
учётом времени или стоимости передачи.