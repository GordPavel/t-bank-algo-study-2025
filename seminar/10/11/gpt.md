Для задачи поиска минимального расстояния от одной вершины до всех остальных в взвешенном графе можно использовать
алгоритм Дейкстры. Этот алгоритм эффективен для графов с неотрицательными весами ребер и позволяет найти кратчайшие пути
от одной исходной вершины до всех остальных.

Алгоритм Дейкстры
Инициализация:

Создайте массив dist[], где dist[i] будет хранить минимальное расстояние от исходной вершины s до вершины i. Изначально
все значения в dist[] установлены в бесконечность, кроме dist[s], которое равно 0.
Создайте приоритетную очередь (обычно реализуется с помощью PriorityQueue в Java) для хранения вершин, которые нужно
обработать, с их текущими минимальными расстояниями.
Основной цикл:

Пока очередь не пуста, извлекайте вершину u с минимальным значением dist[u].
Для каждой соседней вершины v вершины u, если существует ребро (u, v) с весом w, проверьте, можно ли улучшить
минимальное расстояние до v через u. Если dist[u] + w < dist[v], обновите dist[v] и добавьте v в очередь.
Завершение:

После завершения алгоритма массив dist[] будет содержать минимальные расстояния от вершины s до всех остальных вершин.
Реализация на Java

```java
import java.util.*;

class Graph {
    private int V; // Количество вершин
    private LinkedList<Edge>[] adj; // Список смежности

    class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    void addEdge(int u, int v, int weight) {
        adj[u].add(new Edge(v, weight));
        adj[v].add(new Edge(u, weight)); // Если граф неориентированный
    }

    void dijkstra(int src) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        pq.offer(new int[] {src, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];

            for (Edge edge : adj[u]) {
                int v = edge.target;
                int weight = edge.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[] {v, dist[v]});
                }
            }
        }

        // Вывод минимальных расстояний
        System.out.println("Минимальные расстояния от вершины " + src + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("До вершины " + i + " = " + dist[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5;
        Graph graph = new Graph(V);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 4, 5);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 4, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 2, 6);
        graph.addEdge(4, 2, 3);

        graph.dijkstra(0);
    }
}
```

Объяснение кода:
Graph: Класс для представления графа с использованием списка смежности.
Edge: Вложенный класс для представления ребра с целевой вершиной и весом.
addEdge: Метод для добавления ребра в граф.
dijkstra: Метод, реализующий алгоритм Дейкстры. Использует приоритетную очередь для выбора вершины с минимальным текущим
расстоянием.
main: Создает граф, добавляет ребра и вызывает метод dijkstra для поиска минимальных расстояний от заданной исходной
вершины.
Алгоритм Дейкстры имеет временную сложность O((V + E) log V), где V — количество вершин, а E — количество ребер, что
делает его эффективным для графов с неотрицательными весами.