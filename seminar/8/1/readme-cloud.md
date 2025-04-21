Решение задачи: найти минимальное i на отрезке от l до r, для которого ai ≥ k

Для решения этой задачи я буду использовать дерево отрезков. Нам нужно найти минимальный индекс i на отрезке [l, r], для которого ai ≥ k.

Вот решение на Java:

import java.util.*;

public class SegmentTree {
static class Node {
int minIndex; // Индекс минимального элемента в отрезке
int minValue; // Значение минимального элемента
int firstGreaterOrEqual; // Первый индекс, где значение >= некоторого k

        public Node() {
            minIndex = -1;
            minValue = Integer.MAX_VALUE;
            firstGreaterOrEqual = -1;
        }
    }
    
    private Node[] tree;
    private int[] array;
    private int n;
    
    public SegmentTree(int[] arr) {
        array = arr;
        n = arr.length;
        // Размер дерева отрезков
        tree = new Node[4 * n];
        for (int i = 0; i = k
    public int findFirstGreaterOrEqual(int l, int r, int k) {
        return findFirstGreaterOrEqual(0, 0, n - 1, l, r, k);
    }
    
    private int findFirstGreaterOrEqual(int node, int start, int end, int l, int r, int k) {
        // Если отрезок вне запроса
        if (start > r || end = k, то первый элемент >= k
        if (tree[node].minValue >= k && start >= l && end = l && end = k
            if (start == end) {
                return -1; // Нет элементов >= k
            }
        }
        
        // Разделяем отрезок
        int mid = (start + end) / 2;
        int leftResult = findFirstGreaterOrEqual(2 * node + 1, start, mid, l, r, k);
        
        // Если нашли в левом поддереве, возвращаем
        if (leftResult != -1) {
            return leftResult;
        }
        
        // Иначе ищем в правом поддереве
        return findFirstGreaterOrEqual(2 * node + 2, mid + 1, end, l, r, k);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите размер массива: ");
        int n = scanner.nextInt();
        
        int[] arr = new int[n];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i = " + k + " на отрезке [" + l + ", " + r + "]");
                } else {
                    System.out.println("Минимальный индекс i на отрезке [" + l + ", " + r + "], для которого a[i] >= " + k + ": " + result);
                }
            }
        }
        
        scanner.close();
    }
}

Пояснение решения:

Я создал структуру дерева отрезков, где каждый узел хранит:
minIndex - индекс минимального элемента в отрезке
minValue - значение минимального элемента
firstGreaterOrEqual - первый индекс, где значение >= некоторого k

Метод findFirstGreaterOrEqual рекурсивно ищет минимальный индекс i на отрезке [l, r], для которого a[i] >= k:
Если отрезок вне запроса, возвращаем -1
Если минимум на отрезке >= k, то первый элемент >= k
Если отрезок полностью внутри запроса, но минимум = k
Разделяем отрезок и ищем сначала в левом поддереве, затем в правом

Программа поддерживает два типа запросов:
Обновление значения элемента массива
Поиск минимального индекса i на отрезке [l, r], для которого a[i] >= k

Временная сложность:
Построение дерева: O(n)
Запрос обновления: O(log n)
Запрос поиска: O(log n)

Пространственная сложность: O(n)