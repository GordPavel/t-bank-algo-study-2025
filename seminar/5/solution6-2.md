Решение задачи LCP за O(log|S|)
Задача заключается в нахождении длины наибольшего общего префикса (LCP - Longest Common Prefix) между двумя подстроками S[l1..r1] и S[l2..r2] за время O(log|S|) на запрос.

Анализ задачи
Для эффективного решения этой задачи можно использовать полиномиальное хеширование (хеширование строк). Идея состоит в следующем:

Предварительно вычислить хеши всех префиксов строки S
Использовать бинарный поиск для нахождения длины LCP
Алгоритм решения
import java.util.*;

public class LCPSolver {
private long[] hash;
private long[] pow;
private final long p = 31;
private final long mod = 1_000_000_007;
private String s;

    public LCPSolver(String s) {
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
    
    // Проверка равенства подстрок
    private boolean areSubstringsEqual(int l1, int r1, int l2, int r2) {
        if (r1 - l1 != r2 - l2) return false;
        return substringHash(l1, r1) == substringHash(l2, r2);
    }
    
    // Нахождение длины LCP
    public int findLCP(int l1, int r1, int l2, int r2) {
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;
        int maxPossibleLCP = Math.min(len1, len2);
        
        // Бинарный поиск для нахождения длины LCP
        int left = 0;
        int right = maxPossibleLCP;
        
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            
            if (areSubstringsEqual(l1, l1 + mid - 1, l2, l2 + mid - 1)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Введите строку S:");
        String s = scanner.nextLine();
        
        LCPSolver solver = new LCPSolver(s);
        
        System.out.println("Введите количество запросов:");
        int q = scanner.nextInt();
        
        for (int i = 0; i < q; i++) {
            System.out.println("Введите l1, r1, l2, r2 (индексы с 0):");
            int l1 = scanner.nextInt();
            int r1 = scanner.nextInt();
            int l2 = scanner.nextInt();
            int r2 = scanner.nextInt();
            
            int lcp = solver.findLCP(l1, r1, l2, r2);
            System.out.println("LCP: " + lcp);
            
            // Вывод общего префикса
            if (lcp > 0) {
                System.out.println("Общий префикс: " + s.substring(l1, l1 + lcp));
            } else {
                System.out.println("Нет общего префикса");
            }
        }
        
        scanner.close();
    }
}
Объяснение решения
1. Предварительная обработка O(|S|)
   Вычисляем полиномиальные хеши всех префиксов строки S
   Сохраняем степени основания хеша p для быстрого вычисления хешей подстрок
2. Функция substringHash
   Возвращает хеш подстроки [l, r] за O(1)
   Использует формулу: hash[r+1] - hash[l] * pow[r-l+1]
3. Функция areSubstringsEqual
   Проверяет равенство двух подстрок за O(1) с помощью хешей
4. Функция findLCP
   Использует бинарный поиск для нахождения максимальной длины общего префикса
   На каждой итерации проверяет, совпадают ли префиксы определенной длины
   Сложность: O(log |S|)
   Анализ сложности
   Временная сложность:
   Предварительная обработка: O(|S|)
   Каждый запрос: O(log |S|)
   Пространственная сложность: O(|S|) для хранения хешей и степеней
   Пример
   Для строки "abcdefg", если мы хотим найти LCP("abc", "abcefg"):

Вычисляем хеши префиксов и степени p
Используем бинарный поиск для нахождения длины LCP:
Проверяем, совпадают ли первые 3 символа (mid = 3): да
Проверяем, совпадают ли первые 4 символа (mid = 4): нет
Результат: LCP = 3 ("abc")
Предостережения
Полиномиальное хеширование может давать коллизии с очень малой вероятностью. Для повышения надежности можно использовать двойное хеширование с разными основаниями.

Для строк с очень большими индексами (больше 10^9) может потребоваться модификация алгоритма.