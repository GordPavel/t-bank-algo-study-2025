Для задачи, где нужно:

1. Добавлять рёбра в **пустой граф** (то есть изначально только вершины, без рёбер),
2. И по запросу находить **количество рёбер** в **компоненте связности**, в которой находится вершина `x`,

— мы можем снова использовать **СНМ (Disjoint Set Union, DSU)**, но модифицировать его, чтобы отслеживать **количество рёбер** внутри каждой компоненты.

---

## 🧠 Ключевая идея:

* В DSU каждая компонента связности будет хранить:

   * `size` — число вершин в компоненте,
   * `edges` — **общее число рёбер**, соединяющих вершины внутри компоненты.

---

## 🔧 Реализация структуры

Мы добавим массив `edges[root]`, который будет хранить число рёбер внутри компоненты, чьим представителем является `root`.

При добавлении ребра `(u, v)`:

* если `u` и `v` в **разных** компонентах — объединим их, и сумма рёбер будет `edges[u] + edges[v] + 1`,
* если `u` и `v` в **одной** компоненте — это значит, что мы добавляем **дополнительное ребро**, увеличивая `edges[root]` на 1.

---

## ✅ Реализация на Java:

```java
public class DisjointSet {
    private int[] parent;
    private int[] size;
    private int[] edges;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        edges = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            edges[i] = 0;
        }
    }

    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x];
    }

    public void addEdge(int u, int v) {
        int ru = find(u);
        int rv = find(v);

        if (ru == rv) {
            // уже в одной компоненте — просто увеличиваем число рёбер
            edges[ru]++;
        } else {
            // объединяем компоненты
            if (size[ru] < size[rv]) {
                int temp = ru;
                ru = rv;
                rv = temp;
            }

            parent[rv] = ru;
            size[ru] += size[rv];
            edges[ru] += edges[rv] + 1; // добавили новое ребро
        }
    }

    public int getEdgeCount(int x) {
        return edges[find(x)];
    }
}
```

---

## 🧪 Пример использования:

```java
public class Main {
    public static void main(String[] args) {
        int n = 6;
        DisjointSet dsu = new DisjointSet(n);

        dsu.addEdge(0, 1);
        dsu.addEdge(1, 2);
        dsu.addEdge(3, 4);
        dsu.addEdge(2, 0); // замыкаем цикл

        System.out.println("Edges in component of 0: " + dsu.getEdgeCount(0)); // 3
        System.out.println("Edges in component of 3: " + dsu.getEdgeCount(3)); // 1

        dsu.addEdge(2, 3); // соединяем две компоненты
        System.out.println("Edges in component of 4: " + dsu.getEdgeCount(4)); // 5
    }
}
```

---

### 📤 Объяснение вывода:

* Компонента `{0,1,2}` после первых трёх рёбер имеет 2 ребра.
* Четвёртым ребром `(2, 0)` мы добавляем цикл — становится 3 ребра.
* Компонента `{3,4}` имеет 1 ребро.
* После `addEdge(2, 3)` все они объединяются, и общее число рёбер — 3 (первая) + 1 (вторая) + 1 (новое соединяющее) = **5**.

---

Хочешь добавить поддержку ввода запросов в формате команд из консоли (например: `"ADD 1 2"` или `"COUNT 2"`)?
