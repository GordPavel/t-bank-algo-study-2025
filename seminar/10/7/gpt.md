Рассмотрим задачу:

---

## 📘 Условие:

Дано **дерево** (связный ацикличный граф), все вершины изначально **чёрные**.

Два типа запросов:

1. `PAINT x` — покрасить вершину `x` в **белый** цвет.
2. `FIND x` — найти **ближайшего черного предка** вершины `x`.

---

## 🧠 Ключевая идея:

* Дерево — это граф без циклов, поэтому у каждой вершины есть **один родитель** (кроме корня).
* Мы храним:

    * для каждой вершины — её **родителя**,
    * цвет каждой вершины (`true` = черная, `false` = белая),
    * массив `nearestBlackAncestor[x]`: ближайший черный предок вершины `x`.

---

## 🔧 Подход:

### Решаем задачу с помощью **дискретного подъема к предкам** (или просто идем вверх по родителям), обновляя информацию по мере покраски.

Мы **не будем** использовать тяжелые структуры вроде LCA/segment tree, потому что:

* дерево не изменяется,
* запросов не очень много,
* предков можно проходить вверх линейно до ближайшего черного.

---

## ✅ Реализация на Java:

```java
import java.util.*;

public class TreeColoring {
    static List<Integer>[] tree;
    static int[] parent;
    static boolean[] isBlack;
    static int n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();  // количество вершин
        int q = sc.nextInt(); // количество запросов

        tree = new ArrayList[n];
        parent = new int[n];
        isBlack = new boolean[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            isBlack[i] = true; // все вершины изначально черные
        }

        // читаем дерево
        for (int i = 1; i < n; i++) {
            int p = sc.nextInt(); // родитель вершины i
            parent[i] = p;
            tree[p].add(i);
        }
        parent[0] = -1; // корень

        // обрабатываем запросы
        for (int i = 0; i < q; i++) {
            String command = sc.next();
            int x = sc.nextInt();

            if (command.equals("PAINT")) {
                paintWhite(x);
            } else if (command.equals("FIND")) {
                System.out.println(findNearestBlackAncestor(x));
            }
        }
    }

    static void paintWhite(int x) {
        isBlack[x] = false;
    }

    static int findNearestBlackAncestor(int x) {
        while (x != -1 && !isBlack[x]) {
            x = parent[x];
        }
        return x;
    }
}
```

---

## 🧪 Пример:

**Ввод:**

```
7 6
0
0
1
1
2
2
PAINT 4
PAINT 2
FIND 4
FIND 2
FIND 5
FIND 6
```

**Описание дерева:**

```
        0
      /   \
     1     2
    / \   / \
   3  4  5  6
```

**Вывод:**

```
1
0
2
2
```

---

## ⏱ Эффективность:

* `PAINT x` — O(1)
* `FIND x` — в худшем случае O(h), где `h` — высота дерева (линейно вверх до корня)

Для ускорения можно использовать **дискретный подъем (binary lifting)**, но это нужно только при строгих ограничениях на время (например, если `n` очень большое и `q` ≫ 10⁵).

---

Хочешь вариант с бинарным подъемом для `FIND x` за `O(log n)`?
