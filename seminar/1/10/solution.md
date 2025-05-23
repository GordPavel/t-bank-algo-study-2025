### Задача 10: Нахождение числа инверсий в массиве

**Объяснение:**
Для нахождения числа инверсий в массиве можно использовать модифицированный алгоритм сортировки слиянием. В стандартном алгоритме сортировки слиянием мы разбиваем массив на две части, сортируем их рекурсивно и затем объединяем. При объединении мы можем подсчитывать количество инверсий.

Когда мы сравниваем элементы из двух половинок массива, если элемент из правой половины оказывается меньше элемента из левой, то это означает, что все оставшиеся элементы в левой половинке образуют инверсию с текущим элементом из правой половины. Таким образом, мы можем эффективно подсчитать количество инверсий за \(O(n \log n)\) времени.