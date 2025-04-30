package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Rectangle {
    int x1, y1, x2, y2;

    Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}

class Event {
    int x;
    int y1, y2;
    boolean isStart;

    Event(int x, int y1, int y2, boolean isStart) {
        this.x = x;
        this.y1 = y1;
        this.y2 = y2;
        this.isStart = isStart;
    }
}

class SegmentTree {
    private int[] tree;
    private int[] lazy;

    public SegmentTree(int size) {
        tree = new int[4 * size];
        lazy = new int[4 * size];
    }

    private void apply(int node, int start, int end) {
        if (lazy[node] > 0) {
            tree[node] = end - start; // Полная высота
        } else {
            tree[node] = 0;
        }
    }

    public void update(int l, int r, int value, int node, int start, int end) {
        if (start >= r || end <= l) return; // Вне диапазона
        if (start >= l && end <= r) {
            lazy[node] += value; // Увеличиваем значение
            apply(node, start, end);
            return;
        }
        int mid = (start + end) / 2;
        update(l, r, value, node * 2, start, mid);
        update(l, r, value, node * 2 + 1, mid, end);
        tree[node] = tree[node * 2] + tree[node * 2 + 1]; // Обновляем текущий узел
    }

    public int query() {
        return tree[1]; // Возвращаем корень дерева
    }
}


public class Task3 {
    public static void main(String[] args) {
        List<Rectangle> rectangles = Arrays.asList(
                new Rectangle(0, 0, 3, 3),
                new Rectangle(2, 2, 3, 3),
                new Rectangle(2, 0, 4, 2),
                // 11
                new Rectangle(30, 30, 31, 31),
                // 12
                new Rectangle(31, 31, 32, 34)
                // 15
        );

        System.out.println("Общая площадь объединения: " + calculateUnionArea(rectangles));
    }

    public static int calculateUnionArea(List<Rectangle> rectangles) {
        List<Event> events = new ArrayList<>();

        // Создаем события для каждого прямоугольника
        for (Rectangle rect : rectangles) {
            events.add(new Event(rect.x1, rect.y1, rect.y2, true));  // Начало
            events.add(new Event(rect.x2, rect.y1, rect.y2, false)); // Конец
        }

        // Сортируем события по x
        events.sort(Comparator.comparingInt(e -> e.x));

        // Создаем дерево отрезков для хранения высот
        SegmentTree segmentTree = new SegmentTree(10000); // Предполагаем, что y <= 10000
        int lastX = events.get(0).x;
        int area = 0;

        for (Event event : events) {
            int currentX = event.x;
            int width = currentX - lastX;

            if (width > 0) {
                int height = segmentTree.query();
                area += width * height;
            }

            // Обновляем активные отрезки
            if (event.isStart) {
                segmentTree.update(event.y1, event.y2, 1, 1, 0, 10000);
            } else {
                segmentTree.update(event.y1, event.y2, -1, 1, 0, 10000);
            }

            lastX = currentX;
        }

        return area;
    }
}