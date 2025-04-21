Решение задачи: Массив с операциями присвоения и поиска

Я решу вторую задачу: есть массив a из n целых чисел и две операции: присвоить значение: ai = x, и найти минимальное i на отрезке от l до r, для которого ai ≥ k.

Вот решение на Java с использованием дерева отрезков:

import java.util.*;

public class MinIndexGreaterOrEqual {
static class SegmentTree {
private int[] array;      // Исходный массив
private int[] tree;       // Дерево отрезков
private int n;            // Размер массива

        /**
         Инициализирует дерево отрезков для массива arr.
         * @param arr исходный массив
         */
        public SegmentTree(int[] arr) {
            array = arr;
            n = arr.length;
            // Выделяем память для дерева отрезков (4*n достаточно для любого n)
            tree = new int[4 * n];
            // Строим дерево отрезков
            build(0, 0, n - 1);
        }
        
        /**
         Рекурсивно строит дерево отрезков.
         В каждом узле храним минимальный индекс элемента на отрезке.
         * @param node индекс текущего узла в дереве
         @param start начало отрезка
         @param end конец отрезка
         */
        private void build(int node, int start, int end) {
            if (start == end) {
                // Листовой узел - просто сохраняем индекс
                tree[node] = start;
                return;
            }
            
            int mid = (start + end) / 2;
            // Рекурсивно строим левое и правое поддеревья
            build(2 * node + 1, start, mid);
            build(2 * node + 2, mid + 1, end);
            
            // Выбираем индекс с меньшим значением
            if (array[tree[2 * node + 1]] = k.
         * @param l левая граница отрезка
         @param r правая граница отрезка
         @param k пороговое значение
         @return минимальный индекс i на отрезке [l, r], для которого a[i] >= k, или -1, если такого нет
         */
        public int findMinIndexGreaterOrEqual(int l, int r, int k) {
            // Ищем минимальный индекс на отрезке [l, r], для которого a[i] >= k
            return findMinIndexGreaterOrEqual(0, 0, n - 1, l, r, k);
        }
        
        /**
         Рекурсивно ищет минимальный индекс i на отрезке [l, r], для которого a[i] >= k.
         * @param node индекс текущего узла в дереве
         @param start начало отрезка
         @param end конец отрезка
         @param l левая граница запрашиваемого отрезка
         @param r правая граница запрашиваемого отрезка
         @param k пороговое значение
         @return минимальный индекс i на отрезке [l, r], для которого a[i] >= k, или -1, если такого нет
         */
        private int findMinIndexGreaterOrEqual(int node, int start, int end, int l, int r, int k) {
            // Если отрезок полностью вне запрашиваемого диапазона
            if (start > r || end = k
                return (array[start] >= k) ? start : -1;
            }
            
            int mid = (start + end) / 2;
            
            // Сначала ищем в левом поддереве, так как нам нужен минимальный индекс
            int leftResult = findMinIndexGreaterOrEqual(2 * node + 1, start, mid, l, r, k);
            
            // Если нашли подходящий индекс в левом поддереве, возвращаем его
            if (leftResult != -1) {
                return leftResult;
            }
            
            // Иначе ищем в правом поддереве
            return findMinIndexGreaterOrEqual(2 * node + 2, mid + 1, end, l, r, k);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите размер массива (n): ");
        int n = scanner.nextInt();
        
        int[] array = new int[n];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i = " + k);
                } else {
                    System.out.println("Минимальный индекс i на отрезке [" + l + ", " + r + "], для которого a[i] >= " + k + ": " + result);
                    System.out.println("Значение a[" + result + "] = " + array[result]);
                }
            } else {
                System.out.println("Неверный тип операции!");
            }
        }
        
        scanner.close();
    }
}

Подробное объяснение решения:

1. Структура данных

Для решения этой задачи я использую дерево отрезков. Это эффективная структура данных для выполнения запросов на отрезках массива.

2. Класс SegmentTree

Класс SegmentTree содержит:
array - исходный массив
tree - массив для хранения дерева отрезков
n - размер исходного массива

3. Построение дерева отрезков

private void build(int node, int start, int end) {
if (start == end) {
// Листовой узел - просто сохраняем индекс
tree[node] = start;
return;
}

    int mid = (start + end) / 2;
    // Рекурсивно строим левое и правое поддеревья
    build(2 * node + 1, start, mid);
    build(2 * node + 2, mid + 1, end);
    
    // Выбираем индекс с меньшим значением
    if (array[tree[2 * node + 1]] = k

public int findMinIndexGreaterOrEqual(int l, int r, int k) {
// Ищем минимальный индекс на отрезке [l, r], для которого a[i] >= k
return findMinIndexGreaterOrEqual(0, 0, n - 1, l, r, k);
}

private int findMinIndexGreaterOrEqual(int node, int start, int end, int l, int r, int k) {
// Если отрезок полностью вне запрашиваемого диапазона
if (start > r || end = k
return (array[start] >= k) ? start : -1;
}

    int mid = (start + end) / 2;
    
    // Сначала ищем в левом поддереве, так как нам нужен минимальный индекс
    int leftResult = findMinIndexGreaterOrEqual(2 * node + 1, start, mid, l, r, k);
    
    // Если нашли подходящий индекс в левом поддереве, возвращаем его
    if (leftResult != -1) {
        return leftResult;
    }
    
    // Иначе ищем в правом поддереве
    return findMinIndexGreaterOrEqual(2 * node + 2, mid + 1, end, l, r, k);
}

Для поиска минимального индекса i на отрезке [l, r], для которого a[i] >= k:
Если текущий отрезок полностью вне запрашиваемого диапазона, возвращаем -1
Если это листовой узел, проверяем, удовлетворяет ли элемент условию a[i] >= k
Иначе сначала ищем в левом поддереве (так как нам нужен минимальный индекс)
Если в левом поддереве нашли подходящий индекс, возвращаем его
Иначе ищем в правом поддереве

6. Временная сложность

Построение дерева отрезков: O(n)
Обновление значения элемента: O(log n)
Поиск минимального индекса: O(log n)

7. Пространственная сложность

Дерево отрезков: O(n)
Исходный массив: O(n)

Общая пространственная сложность: O(n)

8. Пример использования

В функции main я реализовал интерактивный интерфейс для работы с массивом:
Пользователь вводит размер массива и его элементы
Затем пользователь может выполнять операции:
Присвоить значение элементу массива
Найти минимальный индекс i на отрезке [l, r], для которого a[i] >= k

Это решение эффективно обрабатывает обе операции с логарифмической сложностью.