Чтобы решить задачу "добавление рёбер в пустой граф и отслеживание размера самой большой компоненты связности после каждого добавления", мы можем использовать **СНМ (Disjoint Set Union, DSU)**.

---

## 🧠 Идея:

* Изначально у нас `n` вершин и **нет рёбер**, то есть каждая вершина — это отдельная компонента связности.
* После каждого добавления ребра `(u, v)`:

    * объединяем множества, в которые входят `u` и `v`,
    * обновляем текущий **максимальный размер компоненты**.

---

## 🔧 Что нужно:

1. Структура `DSU` (как из прошлого примера), хранящая размер компоненты.
2. Переменная `maxComponentSize` для отслеживания размера наибольшей компоненты.
3. После каждого `union(u, v)` — обновляем `maxComponentSize`.

---

## ✅ Реализация на Java:

```java
public class DisjointSet {
    private int[] parent;
    private int[] size;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // сжатие пути
        }
        return parent[x];
    }

    public int union(int x, int y) {
        int rx = find(x);
        int ry = find(y);

        if (rx == ry) return size[rx]; // уже в одном компоненте

        // объединение по размеру
        if (size[rx] < size[ry]) {
            int tmp = rx;
            rx = ry;
            ry = tmp;
        }

        parent[ry] = rx;
        size[rx] += size[ry];
        return size[rx];
    }
}
```

---

## 🧪 Использование: добавление рёбер и отслеживание компоненты

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int n = 6; // количество вершин (0...5)
        int[][] edges = {
            {0, 1},
            {2, 3},
            {1, 2},
            {4, 5},
            {3, 4}
        };

        DisjointSet dsu = new DisjointSet(n);
        int maxComponentSize = 1;

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int newSize = dsu.union(u, v);
            maxComponentSize = Math.max(maxComponentSize, newSize);
            System.out.println("After adding edge (" + u + ", " + v + "): max size = " + maxComponentSize);
        }
    }
}
```

---

### 📤 Пример вывода:

```
After adding edge (0, 1): max size = 2
After adding edge (2, 3): max size = 2
After adding edge (1, 2): max size = 4
After adding edge (4, 5): max size = 4
After adding edge (3, 4): max size = 6
```

---

Хочешь, чтобы программа читала рёбра из консоли или файла?
