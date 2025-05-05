Для решения задачи, где требуется отслеживать количество компонент связности, являющихся деревьями, после каждого добавления ребра в граф, используется модифицированная структура данных **Union-Find** (СНМ) с расширением для отслеживания характеристик компонент.

---

### Подход

1. **Основная идея**:
    - Компонента связности является деревом, если количество рёбер в ней равно количеству вершин минус один (`edges = size - 1`).
    - При добавлении ребра между вершинами:
        - Если вершины находятся в разных компонентах, объединяем их, обновляем характеристики и пересчитываем, является ли новая компонента деревом.
        - Если вершины в одной компоненте, увеличиваем количество рёбер и проверяем, не нарушается ли условие дерева (`edges > size - 1`).

2. **Структура данных**:
    - Используются `HashMap` для хранения:
        - `parent`: родитель каждой вершины.
        - `rank`: ранг для оптимизации объединения.
        - `size`: количество вершин в компоненте.
        - `edges`: количество рёбер в компоненте.
        - `isTree`: флаг, является ли компонента деревом.
    - Глобальная переменная `treeCount` отслеживает текущее количество деревьев.

3. **Операции**:
    - `addEdge(u, v)`: добавляет ребро между вершинами `u` и `v`.
    - `query()`: возвращает текущее количество деревьев в графе.

4. **Оптимизация**:
    - Используется **сжатие путей** в методе `find` для ускорения операций.
    - Используется **объединение по рангу** для минимизации глубины деревьев.

---

### Реализация на Java

```java
import java.util.*;

public class TreeComponentCounter {
    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;
    private Map<Integer, Integer> size;
    private Map<Integer, Integer> edges;
    private Map<Integer, Boolean> isTree;
    private int treeCount;

    public TreeComponentCounter() {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = new HashMap<>();
        edges = new HashMap<>();
        isTree = new HashMap<>();
        treeCount = 0;
    }

    public int find(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
            size.put(x, 1);
            edges.put(x, 0);
            isTree.put(x, true);
            treeCount++;
            return x;
        }
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x))); // Сжатие путей
        }
        return parent.get(x);
    }

    public void addEdge(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) {
            // Добавление ребра внутри компоненты
            edges.put(rootU, edges.get(rootU) + 1);
            if (isTree.get(rootU)) {
                isTree.put(rootU, false);
                treeCount--; // Компонента больше не дерево
            }
            return;
        }

        // Добавление ребра между разными компонентами
        boolean wasTreeU = isTree.get(rootU);
        boolean wasTreeV = isTree.get(rootV);

        // Объединение по рангу
        if (rank.get(rootU) < rank.get(rootV)) {
            int temp = rootU;
            rootU = rootV;
            rootV = temp;
        }

        parent.put(rootV, rootU);
        size.put(rootU, size.get(rootU) + size.get(rootV));
        edges.put(rootU, edges.get(rootU) + edges.get(rootV) + 1);

        // Обновление флага isTree
        boolean newIsTree = wasTreeU && wasTreeV;
        isTree.put(rootU, newIsTree);

        // Обновление treeCount
        int oldTrees = 0;
        if (wasTreeU) oldTrees++;
        if (wasTreeV) oldTrees++;
        treeCount -= oldTrees;
        if (newIsTree) {
            treeCount++;
        }

        // Обновление ранга
        if (rank.get(rootU) == rank.get(rootV)) {
            rank.put(rootU, rank.get(rootU) + 1);
        }
    }

    public int query() {
        return treeCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TreeComponentCounter tcc = new TreeComponentCounter();
        List<Integer> results = new ArrayList<>();

        while (scanner.hasNext()) {
            String queryType = scanner.next();
            if (queryType.equals("add")) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                tcc.addEdge(u, v);
            } else if (queryType.equals("count")) {
                results.add(tcc.query());
            }
        }

        for (int res : results) {
            System.out.println(res);
        }
    }
}
```

---

### Объяснение

1. **Метод `find(int x)`**:
    - Если вершина `x` ещё не добавлена, она создаётся как отдельная компонента (`size=1`, `edges=0`, `isTree=true`) и добавляется в `treeCount`.
    - Если вершина уже добавлена, метод находит её корень с использованием сжатия путей для оптимизации.

2. **Метод `addEdge(int u, int v)`**:
    - Если вершины `u` и `v` находятся в одной компоненте, увеличивается количество рёбер. Если компонента была деревом, `treeCount` уменьшается на 1.
    - Если вершины в разных компонентах, объединяются их характеристики (`size`, `edges`), и обновляется флаг `isTree`. `treeCount` пересчитывается с учётом изменения количества деревьев.

3. **Метод `query()`**:
    - Возвращает текущее количество деревьев в графе.

4. **Метод `main`**:
    - Считывает запросы из стандартного ввода.
    - Для каждого запроса вызывается соответствующий метод (`addEdge` или `query`).
    - Результаты выводятся в консоль.

---

### Пример работы

#### Вход:
```
add 0 1
count
add 1 2
count
add 0 2
count
```

#### Выход:
```
1
1
0
```

#### Объяснение:
1. Добавляется ребро `(0, 1)`: компонента `{0, 1}` содержит 1 вершину и 1 ребро → дерево. `treeCount = 1`.
2. Добавляется ребро `(1, 2)`: компоненты `{0, 1}` и `{2}` объединяются. Новая компонента `{0, 1, 2}` содержит 3 вершины и 2 ребра → дерево. `treeCount = 1`.
3. Добавляется ребро `(0, 2)`: внутри компоненты `{0, 1, 2}` количество рёбер становится 3. Условие `edges = size - 1` нарушается → компонента больше не дерево. `treeCount = 0`.

---

### Заключение

Реализация Union-Find с отслеживанием характеристик компонент связности позволяет эффективно решать задачу добавления рёбер в граф и определения количества деревьев после каждого шага. Использование `HashMap` делает структуру гибкой и динамичной, а оптимизации (сжатие путей и объединение по рангу) обеспечивают высокую производительность.