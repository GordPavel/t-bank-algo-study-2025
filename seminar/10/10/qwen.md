Задача, которую вы описали, является классической задачей **построения минимального остовного дерева (Minimum Spanning Tree, MST)** в графе.

---

## 🔧 Условие задачи:

- Дан **взвешенный неориентированный связный граф**.
- Нужно выбрать такие рёбра из графа, чтобы:
    - Граф остался **связным**.
    - Сумма весов выбранных рёбер была **минимальной возможной**.
    - То есть построить **минимальное остовное дерево**.

---

## 🧠 Алгоритмы для решения

Для построения минимального остовного дерева обычно используются два известных алгоритма:

1. **Алгоритм Краскала**
2. **Алгоритм Прима**

Мы рассмотрим **алгоритм Краскала**, так как он хорошо подходит для разреженных графов и легко реализуется с помощью **СНМ (Union-Find)**.

---

## ✅ Алгоритм Краскала — шаги:

1. Отсортировать все рёбра графа по возрастанию веса.
2. Инициализировать структуру **Union-Find (СНМ)** для отслеживания компонент связности.
3. Перебирать рёбра в порядке возрастания веса:
    - Если вершины ребра принадлежат **разным компонентам**, добавляем это ребро в MST и объединяем компоненты.
    - Если они в одной компоненте, ребро пропускается (чтобы не создавать цикл).
4. В конце мы получаем набор рёбер, образующих минимальное остовное дерево.

---

## 💻 Реализация на Java

```java
import java.util.*;

class Edge implements Comparable<Edge> {
    int u, v, weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return false; // В одной компоненте
        }

        // Union by rank
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}

public class MinimumSpanningTree {
    public static List<Edge> kruskal(int n, List<Edge> edges) {
        Collections.sort(edges); // Сортируем рёбра по весу
        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            if (uf.union(e.u, e.v)) {
                mst.add(e); // Ребро соединяет две части — добавляем в MST
            }
        }

        return mst;
    }

    public static void main(String[] args) {
        int n = 5; // количество вершин
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 2));
        edges.add(new Edge(0, 3, 6));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 3, 8));
        edges.add(new Edge(1, 4, 5));
        edges.add(new Edge(2, 4, 7));
        edges.add(new Edge(3, 4, 9));

        List<Edge> mst = kruskal(n, edges);

        System.out.println("Рёбра минимального остовного дерева:");
        int totalWeight = 0;
        for (Edge e : mst) {
            System.out.println(e.u + " - " + e.v + " : " + e.weight);
            totalWeight += e.weight;
        }

        System.out.println("Общая сумма весов: " + totalWeight);
    }
}
```

---

## 📊 Пример работы программы

### Входные данные:
```
n = 5
edges = {
  (0, 1, 2),
  (0, 3, 6),
  (1, 2, 3),
  (1, 3, 8),
  (1, 4, 5),
  (2, 4, 7),
  (3, 4, 9)
}
```

### Выход:
```
Рёбра минимального остовного дерева:
0 - 1 : 2
1 - 2 : 3
1 - 4 : 5
0 - 3 : 6
Общая сумма весов: 16
```

---

## 📈 Анализ сложности

| Параметр | Оценка |
|----------|--------|
| Сортировка рёбер | O(E log E) |
| Объединение через Union-Find | O(E α(N)) ~ O(E) |
| Итоговая сложность | **O(E log E)** |

---

## ✅ Заключение

Алгоритм Краскала эффективно решает задачу построения минимального остовного дерева. Он использует структуру данных **Union-Find (СНМ)** для быстрого определения и объединения компонент связности. Это позволяет находить оптимальное решение за время `O(E log E)`.

Такой подход применим во многих прикладных задачах, таких как:
- проектирование сетей (электрических, телекоммуникационных),
- кластеризация,
- поиск наиболее экономичных путей доставки и др.