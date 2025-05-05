Рассмотрим задачу:

---

## 📘 Условие:

Дан массив `a` из `n` элементов, изначально **заполненный нулями**.

Есть два типа запросов:

1. `SET i` — присвоить `a[i] = 1`,
2. `FIND i` — найти **ближайший к `i` индекс**, где `a[j] == 0`.

Если таких несколько, выбирается ближайший по расстоянию. Если равноудалённые — можно выбрать любой из них.

---

## 🧠 Идея:

Чтобы эффективно находить **ближайший ноль** после множества установок `a[i] = 1`, нам нужно быстро:

* узнавать, **есть ли ноль вблизи** позиции `i`,
* искать ближайший индекс с `a[j] == 0`.

---

## 🔧 Способы решения:

### Подход 1: **TreeSet** (Java)

Мы будем хранить **множество индексов, где `a[i] == 0`**, в `TreeSet<Integer> zeros`.

* При `SET i` — удаляем `i` из `zeros`.
* При `FIND i`:

    * `floor(i)` даст ближайший ноль слева или равный,
    * `ceiling(i)` даст ближайший ноль справа или равный,
    * сравниваем расстояния и возвращаем ближайший.

---

## ✅ Реализация на Java:

```java
import java.util.*;

public class NearestZeroFinder {
    static TreeSet<Integer> zeros;
    static int n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();  // размер массива
        int q = sc.nextInt(); // количество запросов

        zeros = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            zeros.add(i);  // изначально все нули
        }

        for (int i = 0; i < q; i++) {
            String command = sc.next();
            int index = sc.nextInt();

            if (command.equals("SET")) {
                set(index);
            } else if (command.equals("FIND")) {
                int res = findNearestZero(index);
                System.out.println(res);
            }
        }
    }

    static void set(int i) {
        zeros.remove(i);  // ставим 1 → убираем индекс из множества нулей
    }

    static int findNearestZero(int i) {
        if (zeros.isEmpty()) {
            return -1;  // нет ни одного нуля
        }

        Integer left = zeros.floor(i);
        Integer right = zeros.ceiling(i);

        if (left == null) return right;
        if (right == null) return left;

        // выбираем ближайший
        if (Math.abs(i - left) <= Math.abs(right - i)) {
            return left;
        } else {
            return right;
        }
    }
}
```

---

## 🧪 Пример входных данных:

```
10 6
SET 2
SET 5
FIND 2
FIND 5
FIND 4
FIND 9
```

## ✅ Вывод:

```
1
4
4
9
```

---

## ⏱ Эффективность:

* `TreeSet` обеспечивает:

    * `add/remove` за **O(log n)**,
    * `floor/ceiling` за **O(log n)**,
* Таким образом, **все запросы работают эффективно** даже при большом количестве обращений.

---

Хочешь, чтобы программа возвращала оба ближайших нуля (слева и справа), если равны по расстоянию?
