Для решения задачи, где требуется обрабатывать два типа запросов — добавление ребра в граф и определение количества рёбер в компоненте связности для заданной вершины — используется модифицированная структура данных **Union-Find** (СНМ). В отличие от стандартной реализации, здесь дополнительно отслеживается количество рёбер в каждой компоненте связности.

---

### Подход

1. **Структура Union-Find**:
    - Каждая вершина имеет родителя (`parent`), ранг (`rank`) для оптимизации объединения, размер компоненты (`size`), и количество рёбер в компоненте (`edges`).
    - Для динамического добавления вершин используются `HashMap`.

2. **Добавление ребра**:
    - Если вершины уже находятся в одной компоненте, количество рёбер в этой компоненте увеличивается на 1.
    - Если вершины в разных компонентах, компоненты объединяются, и количество рёбер в новой компоненте становится суммой рёбер исходных компонент плюс 1 (за добавленное ребро).

3. **Запрос количества рёбер**:
    - Для заданной вершины определяется корень её компоненты, и возвращается значение `edges` для этого корня.

4. **Оптимизация**:
    - Используется **сжатие путей** в методе `find` для ускорения последующих операций.
    - Используется **объединение по размеру** для минимизации глубины деревьев.

---

### Реализация на Java

```java
import java.util.*;

public class EdgeComponentFinder {
    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;
    private Map<Integer, Integer> size;
    private Map<Integer, Integer> edges;

    public EdgeComponentFinder() {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = new HashMap<>();
        edges = new HashMap<>();
    }

    public int find(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
            size.put(x, 1);
            edges.put(x, 0);
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
            // Рёбра внутри компоненты — просто увеличиваем счётчик
            edges.put(rootU, edges.get(rootU) + 1);
            return;
        }

        // Объединение по размеру
        if (size.get(rootU) < size.get(rootV)) {
            int temp = rootU;
            rootU = rootV;
            rootV = temp;
        }

        parent.put(rootV, rootU);
        size.put(rootU, size.get(rootU) + size.get(rootV));
        edges.put(rootU, edges.get(rootU) + edges.get(rootV) + 1);
    }

    public int getEdgeCount(int x) {
        return edges.getOrDefault(find(x), 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeComponentFinder ecf = new EdgeComponentFinder();
        List<Integer> results = new ArrayList<>();

        while (scanner.hasNext()) {
            String queryType = scanner.next();
            if (queryType.equals("add")) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                ecf.addEdge(u, v);
            } else if (queryType.equals("query")) {
                int x = scanner.nextInt();
                results.add(ecf.getEdgeCount(x));
            }
        }

        for (int result : results) {
            System.out.println(result);
        }
    }
}
```

---

### Объяснение

1. **Конструктор `EdgeComponentFinder()`**:
    - Инициализирует структуры данных для хранения родителей, рангов, размеров и количества рёбер в компонентах.

2. **Метод `find(int x)`**:
    - Если вершина `x` ещё не добавлена, она создаётся и инициализируется.
    - Если вершина уже существует, метод находит корень компоненты с использованием сжатия путей для оптимизации.

3. **Метод `addEdge(int u, int v)`**:
    - Находит корни компонент вершин `u` и `v`.
    - Если корни совпадают, увеличивает количество рёбер в этой компоненте.
    - Если корни разные, объединяет компоненты и обновляет количество рёбер.

4. **Метод `getEdgeCount(int x)`**:
    - Возвращает количество рёбер в компоненте, содержащей вершину `x`.

5. **Метод `main`**:
    - Считывает запросы из стандартного ввода.
    - Для каждого запроса вызывает соответствующий метод (`addEdge` или `getEdgeCount`).
    - Результаты запросов выводятся в консоль.

---

### Пример работы

#### Вход:
```
add 0 1
query 0
add 1 2
query 2
add 0 2
query 2
```

#### Выход:
```
1
2
3
```

#### Объяснение:
1. Добавляется ребро `0-1`: компонента `{0,1}` содержит 1 ребро.
2. Запрос для `0`: возвращается `1`.
3. Добавляется ребро `1-2`: компонента `{0,1,2}` содержит 2 ребра (`0-1`, `1-2`).
4. Запрос для `2`: возвращается `2`.
5. Добавляется ребро `0-2`: компонента `{0,1,2}` содержит 3 ребра (`0-1`, `1-2`, `0-2`).
6. Запрос для `2`: возвращается `3`.

---

### Заключение

Реализация Union-Find с отслеживанием количества рёбер позволяет эффективно обрабатывать запросы на добавление рёбер и определение количества рёбер в компоненте связности. Использование `HashMap` делает структуру гибкой и динамичной, а оптимизации (сжатие путей и объединение по размеру) обеспечивают высокую производительность.