//public static int countEqualPairs(int[] a, int[] b) {
//    int i = 0, j = 0;
//    int count = 0;
//
//    while (i < a.length && j < b.length) {
//        if (a[i] == b[j]) {
//            count++;
//            i++;
//            j++;
//        } else if (a[i] < b[j]) {
//            i++;
//        } else {
//            j++;
//        }
//    }
//
//    return count;
//}
//
//public static void main(String[] args) {
//    int[] a = {1, 2, 3, 4, 5};
//    int[] b = {2, 4, 6, 8, 10};
//    System.out.println("Количество пар с одинаковыми элементами: " + countEqualPairs(a, b));
//}


//Для данной задачи, где массивы отсортированы, можно также использовать **технику двух указателей (Two-Pointer Technique)**, которая позволяет решить задачу за линейное время \(O(n + m)\).
//
//Идея заключается в том, чтобы использовать два указателя: один для массива `a`, другой — для массива `b`. Затем мы сравниваем элементы, на которые указывают указатели, и принимаем решение, как двигать указатели (в зависимости от того, какой элемент меньше, больше или равен).
//
//---
//
//### Реализация на Java:
//
//```java
public class FindEqualPairs {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};  // Массив a отсортирован
        int[] b = {2, 2, 3, 5};  // Массив b отсортирован

        int result = countEqualPairs(a, b);
        System.out.println(result);  // Ожидаемый результат: 2 (пары (2,2) и (3,3))
    }

    public static int countEqualPairs(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;

        int i = 0;  // Указатель для массива `a`
        int j = 0;  // Указатель для массива `b`
        int count = 0;  // Количество равных элементов

        // Пока оба указателя находятся в пределах своих массивов
        while (i < n && j < m) {
            if (a[i] < b[j]) {
                // Если текущий элемент `a[i]` меньше `b[j]`, двигаемся вперёд в `a`
                i++;
            } else if (a[i] > b[j]) {
                // Если текущий элемент `a[i]` больше `b[j]`, двигаемся вперёд в `b`
                j++;
            } else {
                // Если a[i] == b[j], это совпадающая пара
                count++;
                // Двигаем оба указателя вперёд
                i++;
                j++;
            }
        }

        return count;
    }
}
//```
//
//---
//
//### Объяснение работы алгоритма:
//
//1. Используем два указателя `i` (для массива `a`) и `j` (для массива `b`):
//    - **Если `a[i] < b[j]`**, двигаем указатель `i`, так как текущий элемент в `a` может быть меньше всех последующих элементов массива `b`.
//    - **Если `a[i] > b[j]`**, двигаем указатель `j`, так как текущий элемент в `b` может быть меньше всех последующих элементов массива `a`.
//    - **Если `a[i] == b[j]`**, это означает, что мы нашли соответствующую пару. Увеличиваем счётчик и передвигаем оба указателя.
//
//2. Алгоритм завершается, когда один из указателей выходит за пределы длины своего массива (либо `i >= n`, либо `j >= m`).
//
//3. Учитывая, что каждый указатель перемещается максимум длину соответствующего массива (\(O(n)\) для `a` и \(O(m)\) для `b`), общая сложность алгоритма составляет \(O(n + m)\).
//
//---
//
//### Пример работы:
//Для массивов:
//- \(a = \{1, 2, 3, 4\}\)
//- \(b = \{2, 2, 3, 5\}\)
//
//Шаги выполнения:
//
//1. Инициализация \(i = 0, j = 0, count = 0\).
//2. \(a[i] = 1 < b[j] = 2\): увеличиваем \(i\).
//3. \(a[i] = 2 == b[j] = 2\): совпадение, \(count = 1\). Увеличиваем \(i\) и \(j\).
//4. \(a[i] = 3, b[j] = 2\): \(a[i] > b[j]\), увеличиваем \(j\).
//5. \(a[i] = 3 == b[j] = 3\): совпадение, \(count = 2\). Увеличиваем \(i\) и \(j\).
//6. \(a[i] = 4 < b[j] = 5\): увеличиваем \(i\).
//7. \(i\) выходит за пределы массива \(a\), завершаем.
//
//Результат: \(count = 2\), пары \((2, 2)\) и \((3, 3)\).
//
//---
//
//### Дополнительная информация:
//- **Обработка равных значений**:
//  Если массивы содержат несколько одинаковых элементов, можно расширить алгоритм для подсчёта всех дублей одновременно (например, если в обоих массивах одинаковые значения повторяются больше одного раза). Это можно сделать немного модифицировав подход, например, добавляя шаги для подсчёта количества дублирующихся значений в каждом массиве.