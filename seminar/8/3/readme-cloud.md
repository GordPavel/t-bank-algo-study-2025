Решение задачи: Парковка на n мест

Я решу задачу о парковке на n мест, где каждое место может быть занятым или свободным, и нужно обрабатывать следующие операции:
Пометить место как занятое/свободное
Найти число свободных мест на отрезке от l до r
Найти k-е по порядку свободное место
Найти свободное место, ближайшее к i

Для решения этой задачи я буду использовать дерево отрезков, которое позволит эффективно выполнять все требуемые операции.

import java.util.*;

public class ParkingSystem {
static class SegmentTree {
// Узел дерева отрезков
static class Node {
int freePlaces; // Количество свободных мест в текущем отрезке

            public Node() {
                freePlaces = 0;
            }
            
            public Node(int freePlaces) {
                this.freePlaces = freePlaces;
            }
        }
        
        private Node[] tree; // Массив для хранения дерева отрезков
        private boolean[] parkingSpots; // Массив для хранения состояния парковочных мест (true - свободно, false - занято)
        private int n; // Количество парковочных мест
        
        /**
         Инициализирует дерево отрезков для парковки с n местами.
         Изначально все места свободны.
         * @param size количество парковочных мест
         */
        public SegmentTree(int size) {
            n = size;
            // Выделяем память для дерева отрезков (4*n достаточно для любого n)
            tree = new Node[4 * n];
            for (int i = 0; i  r || end = l && end  tree[0].freePlaces) {
                return -1;
            }
            
            return findKth(0, 0, n - 1, k);
        }
        
        /**
         Рекурсивно находит k-е по порядку свободное место.
         * @param node индекс текущего узла в дереве
         @param start начало отрезка
         @param end конец отрезка
         @param k порядковый номер свободного места
         @return индекс k-го свободного места
         */
        private int findKth(int node, int start, int end, int k) {
            // Если это листовой узел, возвращаем его индекс
            if (start == end) {
                return start;
            }
            
            int mid = (start + end) / 2;
            int leftFree = tree[2 * node + 1].freePlaces;
            
            if (k = 0; j--) {
                if (parkingSpots[j]) {
                    leftNearest = j;
                    break;
                }
            }
            
            // Ищем ближайшее свободное место справа от i
            int rightNearest = -1;
            for (int j = i + 1; j = pos || tree[node].freePlaces == 0) {
                return -1;
            }
            
            // Если это листовой узел
            if (start == end) {
                return parkingSpots[start] ? start : -1;
            }
            
            int mid = (start + end) / 2;
            
            // Сначала ищем в правом поддереве (ближе к pos)
            int rightResult = findLastFreeBefore(2 * node + 2, mid + 1, end, pos);
            if (rightResult != -1) {
                return rightResult;
            }
            
            // Если в правом поддереве не нашли, ищем в левом
            return findLastFreeBefore(2 * node + 1, start, mid, pos);
        }
        
        /**
         Находит первое свободное место после позиции pos.
         * @param node индекс текущего узла в дереве
         @param start начало отрезка
         @param end конец отрезка
         @param pos позиция, после которой ищем свободное место
         @return индекс первого свободного места после pos или -1, если такого места нет
         */
        private int findFirstFreeAfter(int node, int start, int end, int pos) {
            // Если отрезок полностью слева от pos или нет свободных мест
            if (end  r || end = l && end  tree[0].freePlaces) {
        return -1;
    }
    
    return findKth(0, 0, n - 1, k);
}

Метод findKthFreeSpot находит k-е по порядку свободное место:
Проверяем, корректно ли значение k и есть ли достаточно свободных мест
Вызываем метод findKth для поиска k-го свободного места

private int findKth(int node, int start, int end, int k) {
// Если это листовой узел, возвращаем его индекс
if (start == end) {
return start;
}

    int mid = (start + end) / 2;
    int leftFree = tree[2 * node + 1].freePlaces;
    
    if (k = 0; j--) {
        if (parkingSpots[j]) {
            leftNearest = j;
            break;
        }
    }
    
    // Ищем ближайшее свободное место справа от i
    int rightNearest = -1;
    for (int j = i + 1; j = pos || tree[node].freePlaces == 0) {
        return -1;
    }
    
    // Если это листовой узел
    if (start == end) {
        return parkingSpots[start] ? start : -1;
    }
    
    int mid = (start + end) / 2;
    
    // Сначала ищем в правом поддереве (ближе к pos)
    int rightResult = findLastFreeBefore(2 * node + 2, mid + 1, end, pos);
    if (rightResult != -1) {
        return rightResult;
    }
    
    // Если в правом поддереве не нашли, ищем в левом
    return findLastFreeBefore(2 * node + 1, start, mid, pos);
}

Метод findLastFreeBefore рекурсивно ищет последнее свободное место перед позицией pos:
Если текущий отрезок полностью справа от pos или в нем нет свободных мест, возвращаем -1
Если текущий отрезок состоит из одного элемента, проверяем, свободно ли это место
Иначе сначала ищем в правом поддереве (ближе к pos), и если там не нашли, ищем в левом поддереве

private int findFirstFreeAfter(int node, int start, int end, int pos) {
// Если отрезок полностью слева от pos или нет свободных мест
if (end <= pos || tree[node].freePlaces == 0) {
return -1;
}

    // Если это листовой узел
    if (start == end) {
        return parkingSpots[start] ? start : -1;
    }
    
    int mid = (start + end) / 2;
    
    // Сначала ищем в левом поддереве (ближе к pos)
    int leftResult = findFirstFreeAfter(2 * node + 1, start, mid, pos);
    if (leftResult != -1) {
        return leftResult;
    }
    
    // Если в левом поддереве не нашли, ищем в правом
    return findFirstFreeAfter(2 * node + 2, mid + 1, end, pos);
}

Метод findFirstFreeAfter рекурсивно ищет первое свободное место после позиции pos:
Если текущий отрезок полностью слева от pos или в нем нет свободных мест, возвращаем -1
Если текущий отрезок состоит из одного элемента, проверяем, свободно ли это место
Иначе сначала ищем в левом поддереве (ближе к pos), и если там не нашли, ищем в правом поддереве

9. Временная сложность

Построение дерева отрезков: O(n)
Обновление состояния места: O(log n)
Подсчет свободных мест на отрезке: O(log n)
Поиск k-го свободного места: O(log n)
Поиск ближайшего свободного места (простая версия): O(n)
Поиск ближайшего свободного места (оптимизированная версия): O(log n)

10. Пространственная сложность

Дерево отрезков: O(n)
Массив состояний парковочных мест: O(n)

Общая пространственная сложность: O(n)