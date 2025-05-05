Для реализации **СНМ (Система Непересекающихся Множеств)** с поддержкой операций `getMin(x)`, `getMax(x)`, `getSize(x)` нам нужно:

1. Использовать структуру **Disjoint Set Union (DSU)** / **Union-Find**.
2. Для каждой компоненты (множества) хранить:

    * минимум,
    * максимум,
    * размер.

---

## 🔧 Структура данных

Мы модифицируем стандартный DSU, добавляя к каждой вершине следующую информацию:

* `parent[i]` — родитель вершины `i`.
* `size[i]` — размер множества, корнем которого является `i`.
* `min[i]` — минимальный элемент в множестве.
* `max[i]` — максимальный элемент в множестве.

---

## ✅ Реализация на Java

```java
public class DisjointSet {
    private int[] parent;
    private int[] size;
    private int[] min;
    private int[] max;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        min = new int[n];
        max = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;   // каждый элемент сам себе родитель
            size[i] = 1;     // размер множества = 1
            min[i] = i;      // минимум = сам элемент
            max[i] = i;      // максимум = сам элемент
        }
    }

    // Поиск сжатия пути (path compression)
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // сжатие пути
        }
        return parent[x];
    }

    // Объединение двух множеств по размеру
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return;

        // делаем rootX корнем большего множества
        if (size[rootX] < size[rootY]) {
            int temp = rootX;
            rootX = rootY;
            rootY = temp;
        }

        parent[rootY] = rootX;

        // Обновление информации
        size[rootX] += size[rootY];
        min[rootX] = Math.min(min[rootX], min[rootY]);
        max[rootX] = Math.max(max[rootX], max[rootY]);
    }

    public int getSize(int x) {
        return size[find(x)];
    }

    public int getMin(int x) {
        return min[find(x)];
    }

    public int getMax(int x) {
        return max[find(x)];
    }
}
```

---

## 🧪 Пример использования:

```java
public class Main {
    public static void main(String[] args) {
        DisjointSet dsu = new DisjointSet(10); // Элементы от 0 до 9

        dsu.union(2, 3);
        dsu.union(3, 5);

        System.out.println("Size of set containing 2: " + dsu.getSize(2)); // 3
        System.out.println("Min of set containing 2: " + dsu.getMin(2));   // 2
        System.out.println("Max of set containing 2: " + dsu.getMax(2));   // 5

        dsu.union(0, 9);
        System.out.println("Size of set containing 9: " + dsu.getSize(9)); // 2
        System.out.println("Min of set containing 9: " + dsu.getMin(9));   // 0
        System.out.println("Max of set containing 9: " + dsu.getMax(9));   // 9
    }
}
```