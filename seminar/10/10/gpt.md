Задача, которую вы описали, является классической задачей поиска минимального остовного дерева (Minimum Spanning Tree,
MST) в графе. Для решения этой задачи можно использовать два основных алгоритма: алгоритм Крускала и алгоритм Прима. Я
объясню и реализую алгоритм Крускала, так как он более интуитивен для понимания и реализации.

Алгоритм Крускала

Алгоритм Крускала работает следующим образом:

Инициализация:
Создать пустое множество для хранения ребер минимального остовного дерева.
Отсортировать все ребра графа по возрастанию их веса.

Объединение компонент:
Использовать структуру данных "Объединение-Поиск" (Union-Find) для управления компонентами связности.
Изначально каждая вершина является своей собственной компонентой.

Построение MST:
Проходить по отсортированным ребрам и для каждого ребра проверять, соединяет ли оно две разные компоненты.
Если соединяет, добавить это ребро в минимальное остовное дерево и объединить компоненты.
Повторять, пока не будет добавлено V-1 ребро (где V — количество вершин).

Реализация на Java

```java
import java.util.*;

class Edge implements Comparable {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Метод для сравнения ребер по весу
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class Subset {
    int parent, rank;
}

public class KruskalMST {
    int V, E; // Количество вершин и ребер
    Edge[] edges; // Массив всех ребер

    // Конструктор
    KruskalMST(int v, int e) {
        V = v;
        E = e;
        edges = new Edge[E];
    }

    // Найти корень элемента i с применением сжатия пути
    int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Объединить два множества x и y
    void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Присоединить дерево меньшего ранга под корень дерева большего ранга
        if (subsets[xroot].rank subsets[yroot].rank){
            subsets[yroot].parent = xroot;
        } else{
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Основной метод для построения MST с использованием алгоритма Крускала
    void kruskalMST() {
        Edge[] result = new Edge[V]; // Это будет наш MST
        int e = 0; // Индекс для результата
        int i = 0; // Индекс для отсортированных ребер

        // Шаг 1: Отсортировать все ребра по возрастанию веса
        Arrays.sort(edges);

        // Создать V подмножеств с одним элементом
        Subset[] subsets = new Subset[V];
        for (int v = 0; v < V; ++v) {
            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        // Количество ребер в MST будет V-1
        while (e < V - 1) {
            // Шаг 2: Выбрать следующее ребро с наименьшим весом
            Edge nextEdge = edges[i++];

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            // Если включение этого ребра не образует цикл, включить его в результат
            if (x != y) {
                result[e++] = nextEdge;
                union(subsets, x, y);
            }
        }

        // Вывод результата
        System.out.println("Ребра в минимальном остовном дереве:");
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
        }
    }

    public static void main(String[] args) {
        int V = 4; // Количество вершин
        int E = 5; // Количество ребер
        KruskalMST graph = new KruskalMST(V, E);

        // Добавление ребер
        graph.edges[0] = new Edge(0, 1, 10);
        graph.edges[1] = new Edge(0, 2, 6);
        graph.edges[2] = new Edge(0, 3, 5);
        graph.edges[3] = new Edge(1, 3, 15);
        graph.edges[4] = new Edge(2, 3, 4);

        // Построение MST
        graph.kruskalMST();
    }
}
```

Объяснение кода:

Edge: Класс для представления ребра с полями для начальной и конечной вершин и веса.
Subset: Класс для представления подмножества в структуре "Объединение-Поиск".
find: Метод для нахождения корня элемента с применением сжатия пути.
union: Метод для объединения двух подмножеств.
kruskalMST: Основной метод, который реализует алгоритм Крускала для построения минимального остовного дерева.
main: Создает граф, добавляет ребра и вызывает метод для построения MST.

Этот алгоритм имеет временную сложность O(E log E), где E — количество ребер, что делает его эффективным для разреженных
графов.