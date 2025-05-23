Для решения задачи, где необходимо найти количество отрезков, в которых каждое число массива является минимальным, мы
можем воспользоваться следующим подходом:

Понимание задачи: Для каждого элемента массива a[i] нужно определить количество подотрезков, в которых этот элемент
является минимальным. Подотрезок определяется как непрерывная часть массива.

Идея решения: Для каждого элемента a[i] мы должны найти:
Сколько элементов подряд слева от a[i] больше или равны a[i].
Сколько элементов подряд справа от a[i] больше или равны a[i].

Если мы знаем количество таких элементов слева и справа, мы можем вычислить количество подотрезков, в которых a[i]
является минимальным.

Реализация:
Используем два массива left и right, где left[i] будет хранить количество элементов подряд слева от a[i], которые больше
или равны a[i], а right[i] — количество элементов подряд справа.
Пройдемся по массиву дважды: один раз слева направо для заполнения left, и один раз справа налево для заполнения right.
Для каждого элемента a[i], количество подотрезков, в которых a[i] является минимальным, будет равно (left[i] + 1) * (
right[i] + 1).

Код на Java:

```java
public class MinSegments {
    public static void main(String[] args) {
        int[] a = {3, 1, 2, 4};
        int n = a.length;

        int[] left = new int[n];
        int[] right = new int[n];

        // Заполняем массив left
        for (int i = 0; i < n; i++) {
            left[i] = 0;
            for (int j = i - 1; j >= 0 && a[j] >= a[i]; j--) {
                left[i]++;
            }
        }

        // Заполняем массив right
        for (int i = n - 1; i >= 0; i--) {
            right[i] = 0;
            for (int j = i + 1; j < n && a[j] >= a[i]; j++) {
                right[i]++;
            }
        }

        // Вычисляем количество отрезков для каждого элемента
        for (int i = 0; i < n; i++) {
            int count = (left[i] + 1) * (right[i] + 1);
            System.out.println("Element " + a[i] + " is minimum in " + count + " segments.");
        }
    }
}
```

Объяснение кода:
Мы используем два вложенных цикла для заполнения массивов left и right. Внешний цикл проходит по каждому элементу
массива, а внутренний — по соседним элементам, чтобы подсчитать количество элементов, которые больше или равны текущему.
После заполнения массивов left и right, мы вычисляем количество подотрезков для каждого элемента, используя формулу (
left[i] + 1) * (right[i] + 1), и выводим результат.

Этот алгоритм имеет временную сложность O(n^2) из-за вложенных циклов, но его можно оптимизировать до O(n) с
использованием стека для более эффективного подсчета элементов слева и справа.