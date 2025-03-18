Сортировка подстрок за O(m log m log |S|)
Задача заключается в сортировке m подстрок строки S, заданных парами индексов (li, ri), за время O(m log m log |S|).

Анализ задачи
Для сортировки m подстрок нам нужно:

Эффективно сравнивать две подстроки (за время O(log |S|))
Использовать алгоритм сортировки, требующий O(m log m) сравнений
Решение
Воспользуемся техникой полиномиального хеширования для быстрого сравнения подстрок и стандартным алгоритмом сортировки:

import java.util.*;

public class SubstringSort {
private long[] hash;
private long[] pow;
private final long p = 31;
private final long mod = 1_000_000_007;
private String s;

    public SubstringSort(String s) {
        this.s = s;
        int n = s.length();
        hash = new long[n + 1];
        pow = new long[n + 1];
        
        // Предварительная обработка строки
        preprocess();
    }
    
    private void preprocess() {
        int n = s.length();
        
        // Инициализация начальных значений
        hash[0] = 0;
        pow[0] = 1;
        
        // Вычисление хешей префиксов и степеней p
        for (int i = 0; i < n; i++) {
            hash[i + 1] = (hash[i] * p + (s.charAt(i) - 'a' + 1)) % mod;
            pow[i + 1] = (pow[i] * p) % mod;
        }
    }
    
    // Получение хеша подстроки [l, r] (включительно)
    private long substringHash(int l, int r) {
        long result = (hash[r + 1] - (hash[l] * pow[r - l + 1]) % mod + mod) % mod;
        return result;
    }
    
    // Сравнение подстрок s[l1..r1] и s[l2..r2]
    // Возвращает отрицательное число, если первая меньше, 
    // положительное, если первая больше, и 0, если равны
    public int compareSubstrings(int l1, int r1, int l2, int r2) {
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;
        int minLen = Math.min(len1, len2);
        
        // Бинарный поиск для нахождения первого различающегося символа
        int left = 0;
        int right = minLen;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            long hash1 = substringHash(l1, l1 + mid - 1);
            long hash2 = substringHash(l2, l2 + mid - 1);
            
            if (hash1 == hash2) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        // Если общий префикс равен длине одной из строк
        if (left == minLen) {
            return Integer.compare(len1, len2);
        }
        
        // Сравниваем первые различающиеся символы
        return Character.compare(s.charAt(l1 + left), s.charAt(l2 + left));
    }
    
    // Сортировка подстрок
    public List<int[]> sortSubstrings(List<int[]> substrings) {
        Collections.sort(substrings, (a, b) -> compareSubstrings(a[0], a[1], b[0], b[1]));
        return substrings;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Введите строку S:");
        String s = scanner.nextLine();
        
        System.out.println("Введите количество подстрок m:");
        int m = scanner.nextInt();
        
        List<int[]> substrings = new ArrayList<>();
        System.out.println("Введите пары индексов (li, ri) для каждой подстроки:");
        
        for (int i = 0; i < m; i++) {
            int l = scanner.nextInt();
            int r = scanner.nextInt();
            substrings.add(new int[]{l, r});
        }
        
        SubstringSort sorter = new SubstringSort(s);
        List<int[]> sortedSubstrings = sorter.sortSubstrings(substrings);
        
        System.out.println("Отсортированные подстроки:");
        for (int[] indices : sortedSubstrings) {
            System.out.println(s.substring(indices[0], indices[1] + 1));
        }
        
        scanner.close();
    }
}
Объяснение решения
Предварительная обработка (O(|S|)):

Вычисляем полиномиальные хеши всех префиксов строки S
Сохраняем степени основания хеша p
Сравнение двух подстрок (O(log |S|)):

Используем бинарный поиск для нахождения первого различающегося символа
Сравниваем хеши подстрок одинаковой длины за O(1)
Общая сложность сравнения: O(log |S|)
Сортировка подстрок (O(m log m)):

Используем стандартный алгоритм сортировки (например, TimSort в Java)
Требует O(m log m) сравнений в худшем случае
Каждое сравнение занимает O(log |S|)
Анализ сложности
Временная сложность:

Предварительная обработка: O(|S|)
Сортировка: O(m log m) сравнений × O(log |S|) на сравнение = O(m log m log |S|)
Общая сложность: O(|S| + m log m log |S|)
Пространственная сложность: O(|S| + m)

O(|S|) для хранения хешей и степеней
O(m) для хранения подстрок
Оптимизации
Для повышения надежности хеширования можно использовать двойное хеширование с разными основаниями.

Можно предварительно построить LCP-массив (Longest Common Prefix) для всех возможных пар позиций, что даст более быстрое сравнение, но потребует O(|S|²) памяти.

Если m близко к |S|², можно рассмотреть использование суффиксного массива для более эффективного решения.