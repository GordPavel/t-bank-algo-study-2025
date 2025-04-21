Давайте разберем третью задачу, которая связана с управлением парковкой и выполнением различных операций над местами.

Задача 3: Парковка

У нас есть парковка на n мест, каждое место может быть занятым или свободным. Мы должны поддерживать следующие операции:

Пометить место как занятое/свободное.
Найти число свободных мест на отрезке от l до r.
Найти k-е по порядку свободное место.
Найти свободное место, ближайшее к i.

Решение

Для решения этой задачи мы будем использовать дерево отрезков (Segment Tree) для эффективного выполнения операций над отрезками.

Шаги решения:

Построение дерева отрезков:
Каждый узел дерева будет хранить количество свободных мест на соответствующем отрезке.

Обновление места:
При изменении состояния места (занятое/свободное), мы обновляем соответствующий узел в дереве отрезков и все его предки.

Запрос числа свободных мест на отрезке:
Для нахождения числа свободных мест на отрезке от l до r, мы будем использовать дерево отрезков.

Поиск k-го свободного места:
Для поиска k-го свободного места, мы будем использовать дерево отрезков для поиска по отрезкам.

Поиск ближайшего свободного места:
Для поиска ближайшего свободного места к i, мы будем использовать дерево отрезков для поиска по отрезкам.

Вот пример реализации на Java:

class ParkingLot {
private int[] tree;
private int n;

    public ParkingLot(int n) {
        this.n = n;
        tree = new int[4 * n];
        build(0, 0, n - 1);
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = 1; // Все места изначально свободны
        } else {
            int mid = (start + end) / 2;
            build(2 * node + 1, start, mid);
            build(2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    public void update(int idx, boolean isOccupied, int node, int start, int end) {
        if (start == end) {
            tree[node] = isOccupied ? 0 : 1;
        } else {
            int mid = (start + end) / 2;
            if (idx = k) {
            return findKthFreeSpace(k, 2 * node + 1, start, mid);
        } else {
            return findKthFreeSpace(k - tree[2 * node + 1], 2 * node + 2, mid + 1, end);
        }
    }

    public int findNearestFreeSpace(int idx, int node, int start, int end) {
        if (tree[node] == 0) {
            return -1; // Нет свободных мест
        }
        if (start == end) {
            return start;
        }
        int mid = (start + end) / 2;
        if (idx <= mid) {
            int leftResult = findNearestFreeSpace(idx, 2 * node + 1, start, mid);
            if (leftResult != -1) {
                return leftResult;
            }
            return findNearestFreeSpace(idx, 2 * node + 2, mid + 1, end);
        } else {
            int rightResult = findNearestFreeSpace(idx, 2 * node + 2, mid + 1, end);
            if (rightResult != -1) {
                return rightResult;
            }
            return findNearestFreeSpace(idx, 2 * node + 1, start, mid);
        }
    }
}

public class Main {
public static void main(String[] args) {
int n = 10; // Количество мест на парковке
ParkingLot parkingLot = new ParkingLot(n);

        // Пример обновления места (занятое/свободное)
        parkingLot.update(3, true, 0, 0, n - 1); // Занять место 3
        parkingLot.update(5, false, 0, 0, n - 1); // Освободить место 5

        // Пример запроса числа свободных мест на отрезке от 2 до 6
        int freeSpaces = parkingLot.queryFreeSpaces(2, 6, 0, 0, n - 1);
        System.out.println("Число свободных мест на отрезке от 2 до 6: " + freeSpaces);

        // Пример поиска 2-го свободного места
        int kthFreeSpace = parkingLot.findKthFreeSpace(2, 0, 0, n - 1);
        System.out.println("2-е свободное место: " + kthFreeSpace);

        // Пример поиска ближайшего свободного места к 4
        int nearestFreeSpace = parkingLot.findNearestFreeSpace(4, 0, 0, n - 1);
        System.out.println("Ближайшее свободное место к 4: " + nearestFreeSpace);
    }
}

Объяснение:

Построение дерева: Мы строим дерево отрезков, где каждый узел хранит количество свободных мест на соответствующем отрезке.
Обновление: При изменении состояния места мы обновляем соответствующий узел и все его предки.
Запрос: Для выполнения запроса мы объединяем результаты из соответствующих узлов дерева.
Поиск k-го свободного места: Мы используем дерево отрезков для поиска k-го свободного места.
Поиск ближайшего свободного места: Мы используем дерево отрезков для поиска ближайшего свободного места к заданному индексу.

Если у вас есть вопросы по коду или вы хотите рассмотреть другую задачу, дайте знать!