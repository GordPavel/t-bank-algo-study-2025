Для решения задачи:

> Есть пустой граф. Делают два вида запросов:
>
> 1. Добавить ребро между вершинами `u` и `v`.
> 2. Найти количество **компонент связности**, которые являются **деревьями**.

нужна структура данных, отслеживающая **компоненты связности**, **число рёбер** в них, и позволяющая быстро определить, является ли каждая компонента **деревом**.

---

## 📚 Что такое дерево в графе?

Компонента связности — **дерево**, если:

* она **связна**,
* **не содержит циклов**.

👉 Это значит, что в компоненте на `k` вершинах должно быть ровно `k-1` рёбер.

---

## 🧠 Идея решения:

Мы используем **DSU (Disjoint Set Union)** со следующими дополнениями:

* `size[root]` — количество вершин в компоненте,
* `edges[root]` — количество рёбер в компоненте,
* `isTree[root]` — флаг: является ли компонента деревом.

Дополнительно — переменная `treeComponentsCount`, отслеживающая общее количество компонент, являющихся деревьями.

---

## ✅ Реализация на Java:

```java
public class DisjointSet {
    private int[] parent;
    private int[] size;
    private int[] edges;
    private boolean[] isTree;
    private int treeComponentsCount;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        edges = new int[n];
        isTree = new boolean[n];
        treeComponentsCount = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            edges[i] = 0;
            isTree[i] = true; // каждая вершина сама по себе — дерево
        }
    }

    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void addEdge(int u, int v) {
        int ru = find(u);
        int rv = find(v);

        if (ru == rv) {
            // Рёбро внутри компоненты — возможно, цикл
            edges[ru]++;
            if (isTree[ru] && edges[ru] >= size[ru]) {
                isTree[ru] = false;
                treeComponentsCount--;
            }
        } else {
            // Объединяем компоненты
            int newSize = size[ru] + size[rv];
            int newEdges = edges[ru] + edges[rv] + 1;
            boolean newIsTree = isTree[ru] && isTree[rv] && (newEdges == newSize - 1);

            // объединение по размеру
            if (size[ru] < size[rv]) {
                int temp = ru;
                ru = rv;
                rv = temp;
            }

            parent[rv] = ru;
            size[ru] = newSize;
            edges[ru] = newEdges;
            if (isTree[ru]) treeComponentsCount--;
            if (isTree[rv]) treeComponentsCount--;
            isTree[ru] = newIsTree;
            if (newIsTree) treeComponentsCount++;
        }
    }

    public int getTreeComponentsCount() {
        return treeComponentsCount;
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

        dsu.addEdge(0, 1); // дерево
        dsu.addEdge(2, 3); // дерево
        dsu.addEdge(1, 2); // объединяем два дерева в одно — тоже дерево
        dsu.addEdge(3, 0); // добавили цикл — больше не дерево
        dsu.addEdge(4, 5); // дерево

        System.out.println("Number of tree components: " + dsu.getTreeComponentsCount()); // 1
    }
}
```

---

### 📤 Объяснение:

1. `{0,1}` — дерево.
2. `{2,3}` — дерево.
3. после соединения `1-2`, получаем `{0,1,2,3}` — размер 4, 3 ребра — ещё дерево.
4. после `3-0` — становится 4 ребра, **не дерево**.
5. `{4,5}` — ещё одно дерево.

Остаётся только одна компонента, являющаяся деревом: `{4,5}`.

---

Хочешь, чтобы эта программа принимала запросы из консоли, например:

```
ADD 1 2
COUNT
```
