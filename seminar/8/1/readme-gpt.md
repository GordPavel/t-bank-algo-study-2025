Давайте разберем первую задачу, которая связана с использованием дерева отрезков для выполнения операций над массивом.

Задача 1: Дерево отрезков

У нас есть массив a из n целых чисел, и мы должны поддерживать две операции:

Присвоить значение: a[i] = x.
Найти минимум на отрезке от l до r, а также число элементов, равных этому минимуму.

Решение

Для решения этой задачи мы будем использовать дерево отрезков (Segment Tree). Дерево отрезков позволяет эффективно выполнять запросы на отрезках массива, такие как нахождение минимума, суммы и т.д.

Шаги решения:

Построение дерева отрезков:
Каждый узел дерева будет хранить минимум на соответствующем отрезке и количество элементов, равных этому минимуму.

Обновление элемента:
При обновлении элемента массива a[i] = x, мы обновляем соответствующий узел в дереве отрезков и все его предки.

Запрос минимума на отрезке:
Для нахождения минимума на отрезке от l до r, мы будем использовать дерево отрезков для объединения информации из соответствующих узлов.

Вот пример реализации на Java:

class SegmentTree {
private int[] min;
private int[] count;
private int n;

    public SegmentTree(int[] array) {
        n = array.length;
        min = new int[4 * n];
        count = new int[4 * n];
        build(array, 0, 0, n - 1);
    }

    private void build(int[] array, int node, int start, int end) {
        if (start == end) {
            min[node] = array[start];
            count[node] = 1;
        } else {
            int mid = (start + end) / 2;
            build(array, 2 * node + 1, start, mid);
            build(array, 2 * node + 2, mid + 1, end);
            merge(node);
        }
    }

    private void merge(int node) {
        if (min[2 * node + 1]  min[2 * node + 2]) {
            min[node] = min[2 * node + 2];
            count[node] = count[2 * node + 2];
        } else {
            min[node] = min[2 * node + 1];
            count[node] = count[2 * node + 1] + count[2 * node + 2];
        }
    }

    public void update(int idx, int value, int node, int start, int end) {
        if (start == end) {
            min[node] = value;
            count[node] = 1;
        } else {
            int mid = (start + end) / 2;
            if (idx  right[0]) {
            return right;
        } else {
            return new int[]{left[0], left[1] + right[1]};
        }
    }
}

public class Main {
public static void main(String[] args) {
int[] array = {1, 3, 2, 7, 9, 11};
SegmentTree st = new SegmentTree(array);

        // Пример запроса минимума на отрезке от 1 до 4
        int[] result = st.query(1, 4, 0, 0, array.length - 1);
        System.out.println("Минимум: " + result[0] + ", Количество: " + result[1]);

        // Пример обновления элемента
        st.update(2, 0, 0, 0, array.length - 1);

        // Повторный запрос после обновления
        result = st.query(1, 4, 0, 0, array.length - 1);
        System.out.println("Минимум: " + result[0] + ", Количество: " + result[1]);
    }
}

Объяснение:

Построение дерева: Мы строим дерево отрезков, где каждый узел хранит минимум и количество элементов, равных этому минимуму, на соответствующем отрезке.
Обновление: При обновлении элемента мы обновляем соответствующий узел и все его предки.
Запрос: Для выполнения запроса мы объединяем результаты из соответствующих узлов дерева.

Если у вас есть вопросы по коду или вы хотите рассмотреть другую задачу, дайте знать!