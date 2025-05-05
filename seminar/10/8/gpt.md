Рассмотрим задачу:

---

## 📘 Условие:

Дан массив `a[]` из положительных чисел. Требуется найти **подотрезок** `[l..r]`, для которого значение:

```
sum(l..r) × min(l..r)
```

максимально.

---

## 🧠 Идея решения:

Это задача **подобна "Largest Rectangle in Histogram"**, но с twist: вместо площади прямоугольника, мы хотим максимизировать:

> сумма элементов на отрезке × минимальный элемент на этом отрезке

Для каждого элемента `a[i]`, мы хотим определить:

* максимальный отрезок `[L..R]`, на котором `a[i]` — **минимум**,
* посчитать `sum[L..R] × a[i]` — потенциальный максимум.

---

## 🔧 Как реализовать:

### Основные шаги:

1. **Префиксная сумма:** для быстрого вычисления суммы отрезка `[L..R]`
2. **Монотонный стек:** для каждого `i` найдём:

    * `left[i]`: ближайший слева индекс, где элемент **меньше** `a[i]`,
    * `right[i]`: ближайший справа индекс, где элемент **меньше** `a[i]`.

Таким образом, на отрезке `[left[i]+1, right[i]-1]`, `a[i]` — минимум.

---

## ✅ Java-код с объяснением:

```java
import java.util.*;

public class MaxProductSegment {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        long[] prefixSum = new long[n + 1];

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            prefixSum[i + 1] = prefixSum[i] + a[i]; // префиксные суммы
        }

        int[] left = new int[n];
        int[] right = new int[n];

        // Монотонный стек для left[]
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && a[stack.peek()] >= a[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // Монотонный стек для right[]
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && a[stack.peek()] >= a[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // Вычисляем максимум (сумма × минимум)
        long maxProduct = 0;
        int bestL = 0, bestR = 0;

        for (int i = 0; i < n; i++) {
            int l = left[i] + 1;
            int r = right[i] - 1;

            long sum = prefixSum[r + 1] - prefixSum[l];
            long product = sum * a[i];

            if (product > maxProduct) {
                maxProduct = product;
                bestL = l;
                bestR = r;
            }
        }

        System.out.println("Max product: " + maxProduct);
        System.out.println("Segment: [" + bestL + ", " + bestR + "]");
    }
}
```

---

## 🧪 Пример:

Ввод:

```
6
3 1 6 4 5 2
```

Пояснение:

* Максимальное значение достигается на отрезке `[2, 4]` (то есть числа `6, 4, 5`)
* Сумма = `15`, минимум = `4`
* Произведение = `15 × 4 = 60`

Вывод:

```
Max product: 60
Segment: [2, 4]
```

---

## ⏱ Сложность:

* Префиксные суммы: O(n)
* Монотонные стеки: O(n)
* Итерация по массиву: O(n)

➡️ Общая сложность: **O(n)** — эффективно даже при `n` до `10^5`.

---

Хочешь, чтобы программа также выводила сам подотрезок (не только индексы)?
