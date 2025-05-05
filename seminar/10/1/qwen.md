Для реализации структуры непересекающихся множеств (СНМ) с поддержкой операций `getMin(x)`, `getMax(x)` и `getSize(x)` на Java, необходимо использовать следующий подход:

### Подход
Структура непересекающихся множеств (СНМ) реализуется с использованием массивов:
- `parent[]` — хранит родителя каждого элемента.
- `rank[]` — используется для управления высотой деревьев (для оптимизации объединения).
- `size[]` — хранит размер множества для корневых элементов.
- `min[]` и `max[]` — хранят соответственно минимальное и максимальное значение элементов множества для корневых элементов.

Операции `find`, `union`, `getMin`, `getMax` и `getSize` реализуются следующим образом:
- **find(x)**: Находит корень множества, содержащего элемент `x`, с использованием сжатия путей.
- **union(x, y)**: Объединяет множества, содержащие элементы `x` и `y`, используя объединение по размеру.
- **getMin(x), getMax(x), getSize(x)**: Возвращают соответственно минимальный, максимальный элемент и размер множества, содержащего элемент `x`.

### Решение на Java

```java
public class DisjointSet {
    private int[] parent;
    private int[] rank;
    private int[] size;
    private int[] min;
    private int[] max;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        min = new int[n];
        max = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
            min[i] = i;
            max[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Сжатие путей
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return; // Уже в одном множестве
        }

        // Объединение по размеру
        if (size[rootX] < size[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
            // Обновление min и max для rootY
            if (min[rootX] < min[rootY]) {
                min[rootY] = min[rootX];
            }
            if (max[rootX] > max[rootY]) {
                max[rootY] = max[rootX];
            }
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
            // Обновление min и max для rootX
            if (min[rootY] < min[rootX]) {
                min[rootX] = min[rootY];
            }
            if (max[rootY] > max[rootX]) {
                max[rootX] = max[rootY];
            }
        }
    }

    public int getMin(int x) {
        return min[find(x)]; // Минимум в множестве
    }

    public int getMax(int x) {
        return max[find(x)]; // Максимум в множестве
    }

    public int getSize(int x) {
        return size[find(x)]; // Размер множества
    }

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(5);
        ds.union(0, 1);
        ds.union(2, 3);
        ds.union(0, 2);

        System.out.println(ds.getMin(3));  // Вывод: 0
        System.out.println(ds.getMax(0));  // Вывод: 3
        System.out.println(ds.getSize(1)); // Вывод: 4

        ds.union(4, 4); // Сам с собой
        System.out.println(ds.getSize(4)); // Вывод: 1
    }
}
```

### Объяснение
1. **Конструктор `DisjointSet(int n)`**:
    - Инициализирует массивы `parent`, `rank`, `size`, `min`, `max`.
    - Каждый элемент изначально является корнем самого себя с размером 1, `min[i] = max[i] = i`.

2. **Метод `find(int x)`**:
    - Находит корень множества, содержащего `x`, с использованием сжатия путей (рекурсивно обновляет родителей для уменьшения глубины дерева).

3. **Метод `union(int x, int y)`**:
    - Находит корни множеств `x` и `y`.
    - Если они разные, объединяет множества по размеру, обновляя `size`, `min`, `max` для корня результирующего множества.

4. **Методы `getMin`, `getMax`, `getSize`**:
    - Возвращают соответствующие значения для корня множества, содержащего элемент `x`.

### Пример использования
В методе `main` демонстрируется работа СНМ:
- Объединяются элементы `0` и `1`, затем `2` и `3`, после чего объединяются полученные множества.
- Для элементов `3`, `0` и `1` выводятся минимальный, максимальный элементы и размер множества соответственно.
- Проверяется объединение элемента с самим собой.

Этот подход обеспечивает эффективную реализацию всех требуемых операций с оптимизацией времени выполнения за счет сжатия путей и объединения по размеру.