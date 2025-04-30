package task4;

import java.util.*;

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

public class Task4 {
    public static void main(String[] args) {
        List<Rectangle> rectangles = Arrays.asList(
                new Rectangle(1, 1, 3, 3),
                new Rectangle(2, 2, 4, 4),
                new Rectangle(3, 1, 5, 3)
        );

        Point result = findMaxCoveragePoint(rectangles);
        System.out.println("Точка с максимальным покрытием: (" + result.x + ", " + result.y + ")");
    }

    public static Point findMaxCoveragePoint(List<Rectangle> rectangles) {
        List<Event> events = new ArrayList<>();

        // Создаем события для каждого прямоугольника
        for (Rectangle rect : rectangles) {
            events.add(new Event(rect.x1, rect.y1, rect.y2, true));  // Начало
            events.add(new Event(rect.x2, rect.y1, rect.y2, false)); // Конец
        }

        // Сортируем события по x, при равенстве по типу события
        events.sort((a, b) -> {
            if (a.x != b.x) return Integer.compare(a.x, b.x);
            return Boolean.compare(a.isStart, b.isStart);
        });

        // Дерево для хранения активных отрезков по y
        TreeMap<Integer, Integer> activeIntervals = new TreeMap<>();
        int lastX = events.get(0).x;
        int maxCoverage = 0;
        Point maxPoint = new Point(0, 0);

        for (Event event : events) {
            // Вычисляем количество перекрытий на текущем отрезке
            int currentX = event.x;
            int width = currentX - lastX;

            if (width > 0) {
                int height = calculateActiveHeight(activeIntervals);
                if (height > maxCoverage) {
                    maxCoverage = height;
                    maxPoint = new Point(currentX, height);
                }
            }

            // Обновляем активные отрезки
            if (event.isStart) {
                activeIntervals.put(event.y1, activeIntervals.getOrDefault(event.y1, 0) + 1);
                activeIntervals.put(event.y2, activeIntervals.getOrDefault(event.y2, 0) - 1);
            } else {
                activeIntervals.put(event.y1, activeIntervals.get(event.y1) - 1);
                if (activeIntervals.get(event.y1) == 0) {
                    activeIntervals.remove(event.y1);
                }
                activeIntervals.put(event.y2, activeIntervals.get(event.y2) + 1);
            }

            lastX = currentX;
        }

        return maxPoint;
    }

    private static int calculateActiveHeight(TreeMap<Integer, Integer> activeIntervals) {
        int height = 0;
        int currentHeight = 0;
        int lastY = -1;

        for (Map.Entry<Integer, Integer> entry : activeIntervals.entrySet()) {
            int y = entry.getKey();
            currentHeight += entry.getValue();

            if (currentHeight > 0) {
                if (lastY != -1) {
                    height += y - lastY;
                }
            }

            lastY = y;
        }

        return height;
    }
}

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}