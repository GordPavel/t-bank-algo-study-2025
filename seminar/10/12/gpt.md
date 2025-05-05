Для решения задачи распространения заразы в графе, где заражение распространяется между вершинами за время, равное весу
ребра, можно использовать модифицированный алгоритм Дейкстры. Этот алгоритм позволяет эффективно вычислить минимальное
время, за которое инфекция достигнет каждой вершины, начиная с нескольких зараженных вершин.

Подход к решению
Инициализация:

Создайте массив infectionTime[], где infectionTime[i] будет хранить минимальное время, за которое вершина i будет
заражена. Изначально все значения в infectionTime[] установлены в бесконечность, кроме начально зараженных вершин, для
которых время заражения равно 0.
Используйте приоритетную очередь для управления вершинами, которые нужно обработать, с их текущими минимальными
временами заражения.
Распространение заразы:

Пока очередь не пуста, извлекайте вершину u с минимальным значением infectionTime[u].
Для каждой соседней вершины v вершины u, если существует ребро (u, v) с весом w, проверьте, можно ли улучшить
минимальное время заражения для v через u. Если infectionTime[u] + w < infectionTime[v], обновите infectionTime[v] и
добавьте v в очередь.
Завершение:

После завершения алгоритма массив infectionTime[] будет содержать минимальные времена заражения для всех вершин.
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

    void spreadInfection(Set<Integer> initiallyInfected) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int[] infectionTime = new int[V];
        Arrays.fill(infectionTime, Integer.MAX_VALUE);

        // Инициализация начально зараженных вершин
        for (int infected : initiallyInfected) {
            infectionTime[infected] = 0;
            pq.offer(new int[] {infected, 0});
        }

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];

            for (Edge edge : adj[u]) {
                int v = edge.target;
                int weight = edge.weight;

                if (infectionTime[u] + weight < infectionTime[v]) {
                    infectionTime[v] = infectionTime[u] + weight;
                    pq.offer(new int[] {v, infectionTime[v]});
                }
            }
        }

        // Вывод времени заражения для каждой вершины
        System.out.println("Время заражения для каждой вершины:");
        for (int i = 0; i < V; i++) {
            System.out.println("Вершина " + i + " будет заражена в момент времени " + infectionTime[i]);
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

        Set<Integer> initiallyInfected = new HashSet<>(Arrays.asList(0, 3));
        graph.spreadInfection(initiallyInfected);
    }
}
```

Объяснение кода:
Graph: Класс для представления графа с использованием списка смежности.
Edge: Вложенный класс для представления ребра с целевой вершиной и весом.
addEdge: Метод для добавления ребра в граф.
spreadInfection: Метод, реализующий модифицированный алгоритм Дейкстры для распространения заразы. Использует
приоритетную очередь для выбора вершины с минимальным текущим временем заражения.
main: Создает граф, добавляет ребра, задает начально зараженные вершины и вызывает метод для распространения заразы.
Этот алгоритм имеет временную сложность O((V + E) log V), где V — количество вершин, а E — количество ребер, что делает
его эффективным для графов с неотрицательными весами.