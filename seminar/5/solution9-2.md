Нахождение количества палиндромных подстрок за O(n log n)
Постановка задачи
Дана строка длины n. Требуется найти количество подстрок, которые являются палиндромами, за время O(n log n).

Что такое палиндром
Палиндром — это строка, которая читается одинаково слева направо и справа налево. Например: "aba", "racecar", "level", "a" (одиночный символ также считается палиндромом).

Подходы к решению
Существует несколько алгоритмов для решения этой задачи:

Наивный подход: проверять каждую подстроку — O(n³)
Метод центров: рассматривать каждую позицию как центр палиндрома — O(n²)
Алгоритм Манакера: специализированный алгоритм для поиска палиндромов — O(n)
Хеширование и бинарный поиск: комбинированный подход — O(n log n)
Рассмотрим решение за O(n log n), использующее хеширование строк и бинарный поиск.

Решение за O(n log n)
Основная идея подхода:

Используем полиномиальное хеширование для проверки палиндромности подстрок
Для каждой позиции как центра палиндрома применяем бинарный поиск для нахождения максимальной длины палиндрома
Шаг 1: Полиномиальное хеширование
Для эффективной проверки палиндромности используем два типа хешей:

Прямой хеш: hash(s[0...i])
Обратный хеш: hash(s[n-1...i] в обратном порядке)
class StringHashing {
private final long[] forwardHash;
private final long[] reverseHash;
private final long[] powers;
private final long p = 31;
private final long mod = 1_000_000_007;
private final String s;

    public StringHashing(String s) {
        this.s = s;
        int n = s.length();
        forwardHash = new long[n + 1];
        reverseHash = new long[n + 1];
        powers = new long[n + 1];
        
        precomputeHashes();
    }
    
    private void precomputeHashes() {
        int n = s.length();
        powers[0] = 1;
        
        for (int i = 0; i < n; i++) {
            // Прямой хеш
            forwardHash[i + 1] = (forwardHash[i] * p + (s.charAt(i) - 'a' + 1)) % mod;
            
            // Обратный хеш
            reverseHash[i + 1] = (reverseHash[i] * p + (s.charAt(n - 1 - i) - 'a' + 1)) % mod;
            
            // Степени основания
            powers[i + 1] = (powers[i] * p) % mod;
        }
    }
    
    // Проверяет, является ли подстрока s[l...r] палиндромом
    public boolean isPalindrome(int l, int r) {
        int len = r - l + 1;
        int reverseL = s.length() - 1 - r;
        int reverseR = s.length() - 1 - l;
        
        long forwardSubstringHash = getSubstringHash(forwardHash, l, r);
        long reverseSubstringHash = getSubstringHash(reverseHash, reverseL, reverseR);
        
        return forwardSubstringHash == reverseSubstringHash;
    }
    
    private long getSubstringHash(long[] hash, int l, int r) {
        return (hash[r + 1] - (hash[l] * powers[r - l + 1]) % mod + mod) % mod;
    }
}
Шаг 2: Бинарный поиск для нахождения максимальной длины палиндрома
Для каждой позиции i как центра палиндрома:

Выполняем бинарный поиск для нахождения максимальной длины нечетного палиндрома (центр - один символ)
Выполняем бинарный поиск для нахождения максимальной длины четного палиндрома (центр - между двумя символами)
public class PalindromicSubstrings {

    public static int countPalindromicSubstrings(String s) {
        int n = s.length();
        if (n == 0) return 0;
        
        StringHashing hashing = new StringHashing(s);
        int count = 0;
        
        // Для каждой позиции как центра палиндрома
        for (int i = 0; i < n; i++) {
            // Нечетные палиндромы (центр - один символ)
            count += countPalindromesWithCenter(hashing, i, i, n);
            
            // Четные палиндромы (центр - между символами)
            if (i < n - 1) {
                count += countPalindromesWithCenter(hashing, i, i + 1, n);
            }
        }
        
        return count;
    }
    
    private static int countPalindromesWithCenter(StringHashing hashing, int left, int right, int n) {
        // Бинарный поиск для нахождения максимальной длины палиндрома
        int low = 0;
        int high = Math.min(left, n - 1 - right);
        int result = 0;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (hashing.isPalindrome(left - mid, right + mid)) {
                result = mid + 1; // +1 потому что mid начинается с 0
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        String s = "aabaa";
        int count = countPalindromicSubstrings(s);
        System.out.println("Количество палиндромных подстрок: " + count);
        
        // Ожидаемый результат для "aabaa": 9
        // Палиндромы: "a", "a", "a", "a", "aa", "aa", "aba", "aba", "aabaa"
    }
}
Анализ сложности
Временная сложность: O(n log n)

Предварительное вычисление хешей: O(n)
Для каждой из n позиций выполняем бинарный поиск: O(log n)
Итого: O(n log n)
Пространственная сложность: O(n)

Хранение прямых и обратных хешей, а также степеней основания
Оптимизации и альтернативные подходы
1. Алгоритм Манакера (O(n))
   Алгоритм Манакера решает задачу за линейное время O(n). Он основан на использовании свойств симметрии палиндромов для сокращения числа проверок:

public int countPalindromesManacher(String s) {
if (s == null || s.isEmpty()) return 0;

    // Преобразуем строку, добавляя разделители
    StringBuilder sb = new StringBuilder();
    sb.append('#');
    for (char c : s.toCharArray()) {
        sb.append(c).append('#');
    }
    String t = sb.toString();
    
    int n = t.length();
    int[] p = new int[n]; // p[i] - радиус палиндрома с центром в i
    int center = 0, right = 0;
    
    for (int i = 0; i < n; i++) {
        if (right > i) {
            p[i] = Math.min(right - i, p[2 * center - i]);
        }
        
        // Расширяем палиндром с центром в i
        while (i - p[i] - 1 >= 0 && i + p[i] + 1 < n && 
               t.charAt(i - p[i] - 1) == t.charAt(i + p[i] + 1)) {
            p[i]++;
        }
        
        // Обновляем center и right
        if (i + p[i] > right) {
            center = i;
            right = i + p[i];
        }
    }
    
    // Подсчитываем количество палиндромов
    int count = 0;
    for (int radius : p) {
        count += (radius + 1) / 2;
    }
    
    return count;
}
2. Динамическое программирование (O(n²))
   Для небольших строк можно использовать DP-подход:

public int countPalindromesDP(String s) {
int n = s.length();
boolean[][] dp = new boolean[n][n]; // dp[i][j] - является ли s[i..j] палиндромом
int count = 0;

    // Все одиночные символы - палиндромы
    for (int i = 0; i < n; i++) {
        dp[i][i] = true;
        count++;
    }
    
    // Проверяем палиндромы длины 2
    for (int i = 0; i < n - 1; i++) {
        if (s.charAt(i) == s.charAt(i + 1)) {
            dp[i][i + 1] = true;
            count++;
        }
    }
    
    // Проверяем палиндромы длины 3 и более
    for (int len = 3; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                dp[i][j] = true;
                count++;
            }
        }
    }
    
    return count;
}
Заключение
Для решения задачи нахождения количества палиндромных подстрок за O(n log n) мы использовали комбинацию полиномиального хеширования и бинарного поиска. Этот подход эффективен для больших строк и имеет относительно простую реализацию.

Для получения оптимального решения за O(n) следует использовать алгоритм Манакера, который является специализированным алгоритмом для работы с палиндромами.