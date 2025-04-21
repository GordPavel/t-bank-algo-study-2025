Давайте разберем шестую задачу, которая связана с определением количества точек, лежащих внутри прямоугольников.

Задача 6: Прямоугольники и точки

У нас есть n прямоугольников и m точек. Для каждого прямоугольника требуется определить, сколько из данных m точек лежат внутри этого прямоугольника. Время работы должно быть \(O((n + m) \log(n + m))\).

Решение

Для решения этой задачи мы будем использовать метод "событий" (sweep line) в сочетании с деревом отрезков. Основная идея заключается в том, чтобы обрабатывать события, связанные с началом и концом прямоугольников, а также с точками, и использовать дерево отрезков для подсчета точек, находящихся внутри активных прямоугольников.

Шаги решения:

Представление событий:
Каждое событие будет представлено как тройка (x, type, y), где x — координата события, type — тип события (начало прямоугольника, конец прямоугольника, точка), y — координата y точки или прямоугольника.

Сортировка событий:
События сортируются по x. Если x одинаковое, то порядок определяется типом события: точка, начало прямоугольника, конец прямоугольника.

Обработка событий:
Используем дерево отрезков для подсчета активных прямоугольников на каждой координате y.

Подсчет точек:
Для каждой точки проверяем, сколько активных прямоугольников содержат эту точку.

Вот пример реализации на Java:

import java.util.*;

class Event implements Comparable {
int x;
int type; // 0 = точка, 1 = начало прямоугольника, -1 = конец прямоугольника
int y;
int index; // индекс точки или прямоугольника

    public Event(int x, int type, int y, int index) {
        this.x = x;
        this.type = type;
        this.y = y;
        this.index = index;
    }

    @Override
    public int compareTo(Event other) {
        if (this.x != other.x) {
            return Integer.compare(this.x, other.x);
        }
        return Integer.compare(this.type, other.type);
    }
}

class SegmentTree {
private int[] tree;
private int n;

    public SegmentTree(int size) {
        n = size;
        tree = new int[4 * n];
    }

    public void update(int idx, int value, int node, int start, int end) {
        if (start == end) {
            tree[node] += value;
        } else {
            int mid = (start + end) / 2;
            if (idx  events = new ArrayList();
        for (int i = 0; i < rectangles.length; i++) {
            int[] rect = rectangles[i];
            events.add(new Event(rect[0], 1, rect[1], i)); // начало прямоугольника
            events.add(new Event(rect[2], -1, rect[1], i)); // конец прямоугольника
        }
        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            events.add(new Event(point[0], 0, point[1], i)); // точка
        }

        Collections.sort(events);

        int maxY = 0;
        for (int[] rect : rectangles) {
            maxY = Math.max(maxY, rect[3]);
        }
        for (int[] point : points) {
            maxY = Math.max(maxY, point[1]);
        }

        SegmentTree segmentTree = new SegmentTree(maxY + 1);
        int[] result = new int[points.length];

        for (Event event : events) {
            if (event.type == 0) { // точка
                result[event.index] = segmentTree.query(0, event.y, 0, 0, maxY);
            } else if (event.type == 1) { // начало прямоугольника
                segmentTree.update(event.y, 1, 0, 0, maxY);
            } else if (event.type == -1) { // конец прямоугольника
                segmentTree.update(event.y, -1, 0, 0, maxY);
            }
        }

        for (int i = 0; i < result.length; i++) {
            System.out.println("Точка " + Arrays.toString(points[i]) + " находится внутри " + result[i] + " прямоугольников.");
        }
    }
}

Объяснение:

События: Мы создаем события для начала и конца каждого прямоугольника, а также для каждой точки.
Сортировка: События сортируются по x, чтобы обрабатывать их в порядке возрастания x.
Дерево отрезков: Используется для подсчета активных прямоугольников на каждой координате y.
Обработка: Для каждой точки мы используем дерево отрезков, чтобы подсчитать количество активных прямоугольников, содержащих эту точку.

Если у вас есть вопросы по коду или вы хотите рассмотреть другую задачу, дайте знать!