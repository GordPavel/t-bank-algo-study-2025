Задача заключается в нахождении количества инверсий в массиве. Инверсия — это пара индексов \((i, j)\), таких что \(i  a[j]\).

Решение (a): Время \(O(n^2)\)

Это простое решение с использованием двух вложенных циклов для перебора всех пар элементов массива.

public class InversionCount {

    // Метод для подсчета инверсий в массиве
    public static int countInversionsBruteForce(int[] arr) {
        int n = arr.length;
        int inversionCount = 0;

        // Перебираем все пары (i, j)
        for (int i = 0; i  a[j], увеличиваем счетчик инверсий
                if (arr[i] > arr[j]) {
                    inversionCount++;
                }
            }
        }

        return inversionCount;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 5};
        System.out.println("Количество инверсий (O(n^2)): " + countInversionsBruteForce(arr));
    }
}

Объяснение:

Инициализация: Создаем переменную inversionCount для хранения количества инверсий.
Два вложенных цикла: Первый цикл перебирает элементы массива, второй — элементы, следующие за текущим элементом.
Проверка условия инверсии: Если текущий элемент больше следующего, увеличиваем счетчик инверсий.
Возврат результата: После завершения циклов возвращаем количество найденных инверсий.

Решение (b): Время \(O(n \log n)\)

Это более эффективное решение с использованием модифицированного алгоритма сортировки слиянием.

public class InversionCount {

    // Метод для подсчета инверсий с использованием сортировки слиянием
    public static int countInversionsMergeSort(int[] arr) {
        int[] temp = new int[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    }

    // Рекурсивный метод для сортировки и подсчета инверсий
    private static int mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        int mid, inversionCount = 0;
        if (left < right) {
            mid = (left + right) / 2;

            // Подсчитываем инверсии в левой и правой половинах
            inversionCount += mergeSortAndCount(arr, temp, left, mid);
            inversionCount += mergeSortAndCount(arr, temp, mid + 1, right);

            // Подсчитываем инверсии при слиянии
            inversionCount += mergeAndCount(arr, temp, left, mid, right);
        }
        return inversionCount;
    }

    // Метод для слияния двух половин и подсчета инверсий
    private static int mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        int inversionCount = 0;

        // Слияние двух половин
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                inversionCount += (mid + 1) - i; // Все оставшиеся элементы в левой половине являются инверсиями
            }
        }

        // Копируем оставшиеся элементы из левой половины
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // Копируем оставшиеся элементы из правой половины
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // Копируем временный массив обратно в оригинальный
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }

        return inversionCount;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 5};
        System.out.println("Количество инверсий (O(n log n)): " + countInversionsMergeSort(arr));
    }
}

Объяснение:

Сортировка слиянием: Используем модифицированную сортировку слиянием для подсчета инверсий.
Рекурсивное деление: Разделяем массив на две половины и рекурсивно подсчитываем инверсии в каждой.
Слияние и подсчет: При слиянии двух отсортированных половин подсчитываем инверсии, возникающие между элементами из разных половин.
Копирование: Копируем временный массив обратно в оригинальный массив после каждого слияния.

Этот метод более эффективен для больших массивов, так как его сложность составляет \(O(n \log n)\).