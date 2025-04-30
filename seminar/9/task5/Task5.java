package task5;

import java.util.*;

class Query {
    int l, r, index;

    Query(int l, int r, int index) {
        this.l = l;
        this.r = r;
        this.index = index;
    }
}

public class Task5 {
    static int[] a;
    static int[] count;
    static int currentUniqueCount = 0;

    public static void main(String[] args) {
        a = new int[]{1, 2, 1, 3, 2, 1, 4, 5}; // Пример массива
        int q = 4; // Количество запросов
        Query[] queries = new Query[q];

        // Пример запросов
        queries[0] = new Query(0, 3, 0); // [1, 2, 1, 3]
        queries[1] = new Query(1, 5, 1); // [2, 1, 3, 2, 1]
        queries[2] = new Query(2, 6, 2); // [1, 3, 2, 1, 4]
        queries[3] = new Query(0, 7, 3); // [1, 2, 1, 3, 2, 1, 4, 5]

        int[] results = processQueries(queries);

        // Вывод результатов
        for (int result : results) {
            System.out.println(result);
        }
    }

    public static int[] processQueries(Query[] queries) {
        int n = a.length;
        int[] results = new int[queries.length];
        count = new int[100001]; // Предполагаем, что значения в a <= 100000
        int currentL = 0, currentR = -1;

        // Сортируем запросы по блокам и по правой границе
        Arrays.sort(queries, (q1, q2) -> {
            int blockSize = (int) Math.sqrt(n);
            if (q1.l / blockSize != q2.l / blockSize) {
                return Integer.compare(q1.l / blockSize, q2.l / blockSize);
            }
            return Integer.compare(q1.r, q2.r);
        });

        for (Query query : queries) {
            while (currentR < query.r) {
                currentR++;
                add(a[currentR]);
            }
            while (currentR > query.r) {
                remove(a[currentR]);
                currentR--;
            }
            while (currentL < query.l) {
                remove(a[currentL]);
                currentL++;
            }
            while (currentL > query.l) {
                currentL--;
                add(a[currentL]);
            }
            results[query.index] = currentUniqueCount;
        }

        return results;
    }

    private static void add(int value) {
        if (count[value] == 0) {
            currentUniqueCount++;
        }
        count[value]++;
    }

    private static void remove(int value) {
        if (count[value] == 1) {
            currentUniqueCount--;
        }
        count[value]--;
    }
}