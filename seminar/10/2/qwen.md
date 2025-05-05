Для решения задачи необходимо отслеживать размер самой большой компоненты связности в графе после каждого добавления
ребра. Для этого идеально подходит структура данных "Система непересекающихся множеств" (Union-Find), которая позволяет
эффективно объединять множества и находить их корни. Также потребуется отслеживать размер самой большой компоненты.

---

### Подход

1. **Использование Union-Find**:
    - Для реализации Union-Find используется структура данных `HashMap`, чтобы динамически добавлять вершины по мере их
      появления.
    - Каждая вершина хранит информацию о своем родителе (`parent`), ранге (`rank`) для оптимизации объединения и размере
      множества (`size`).

2. **Отслеживание максимального размера**:
    - Переменная `maxSize` отслеживает текущий максимальный размер компоненты связности.
    - При каждом объединении множеств (`union`) размер новой компоненты сравнивается с `maxSize`. Если он
      больше, `maxSize` обновляется.

3. **Обработка новых вершин**:
    - Если вершина еще не была добавлена, она создается с начальными
      значениями: `parent[x] = x`, `rank[x] = 0`, `size[x] = 1`.
    - При добавлении новой вершины проверяется, не превышает ли её размер (`1`) текущий `maxSize`. Если да, `maxSize`
      обновляется.

4. **Сложность**:
    - Операции `find` и `union` выполняются за почти константное время благодаря сжатию путей и объединению по рангу.
    - Общая сложность алгоритма для `m` рёбер: `O(m * α(n))`, где `α(n)` — обратная функция Аккермана, которая растёт
      очень медленно.

---

### Реализация на Java

```java
import java.util.*;

public class UnionFindWithMax {
    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;
    private Map<Integer, Integer> size;
    private int maxSize;

    public UnionFindWithMax() {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = new HashMap<>();
        maxSize = 0;
    }

    public int find(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
            size.put(x, 1);
            if (size.get(x) > maxSize) {
                maxSize = size.get(x);
            }
            return x;
        }
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x))); // Сжатие путей
        }
        return parent.get(x);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return; // Уже в одной компоненте
        }

        // Объединение по рангу
        if (rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
            size.put(rootX, size.get(rootX) + size.get(rootY));
            if (size.get(rootX) > maxSize) {
                maxSize = size.get(rootX);
            }
        } else if (rank.get(rootX) < rank.get(rootY)) {
            parent.put(rootX, rootY);
            size.put(rootY, size.get(rootY) + size.get(rootX));
            if (size.get(rootY) > maxSize) {
                maxSize = size.get(rootY);
            }
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
            size.put(rootX, size.get(rootX) + size.get(rootY));
            if (size.get(rootX) > maxSize) {
                maxSize = size.get(rootX);
            }
        }
    }

    public int getMaxSize() {
        return maxSize;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UnionFindWithMax uf = new UnionFindWithMax();
        List<Integer> result = new ArrayList<>();

        while (scanner.hasNextInt()) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            uf.union(u, v);
            result.add(uf.getMaxSize());
        }

        for (int size : result) {
            System.out.println(size);
        }
    }
}
```

---

### Объяснение

1. **Конструктор `UnionFindWithMax()`**:
    - Инициализирует структуры данных для хранения родителей, рангов и размеров множеств.
    - Переменная `maxSize` отслеживает максимальный размер компоненты связности.

2. **Метод `find(int x)`**:
    - Если вершина `x` еще не добавлена, она создаётся и её размер (`1`) сравнивается с `maxSize`.
    - Если вершина уже добавлена, метод находит корень множества с использованием сжатия путей для оптимизации.

3. **Метод `union(int x, int y)`**:
    - Находит корни множеств, содержащих `x` и `y`.
    - Если корни разные, множества объединяются по рангу.
    - После объединения размер новой компоненты сравнивается с `maxSize`. Если он больше, `maxSize` обновляется.

4. **Метод `getMaxSize()`**:
    - Возвращает текущий максимальный размер компоненты связности.

5. **Метод `main`**:
    - Считывает рёбра из стандартного ввода.
    - Для каждого ребра вызывается `union`, и текущий `maxSize` сохраняется в список.
    - В конце список выводится.

---

### Пример работы

#### Вход:

```
0 1
1 2
2 3
```

#### Выход:

```
2
3
4
```

#### Объяснение:

- После добавления ребра `(0,1)`: компонента `{0,1}` имеет размер `2`.
- После добавления ребра `(1,2)`: компонента `{0,1,2}` имеет размер `3`.
- После добавления ребра `(2,3)`: компонента `{0,1,2,3}` имеет размер `4`.

---

### Заключение

Реализация Union-Find с отслеживанием максимального размера компоненты связности позволяет эффективно решать задачу
добавления рёбер в граф и нахождения размера самой большой компоненты связности после каждого шага.
Использование `HashMap` делает структуру гибкой и динамичной, что особенно полезно при неизвестном количестве вершин.