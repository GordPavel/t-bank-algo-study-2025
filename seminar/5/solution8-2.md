Нахождение наибольшей общей подстроки за O(n log n)
Задача: Даны две строки S и T. Требуется найти их наибольшую общую подстроку (LCS - Longest Common Substring) за время O(n log n), где n - суммарная длина строк.

Анализ задачи
Наибольшая общая подстрока - это самая длинная строка, которая является подстрокой обеих исходных строк. Например, для строк "abcdef" и "xbcdyz" наибольшей общей подстрокой будет "bcd".

Подход к решению
Для решения этой задачи за O(n log n) можно использовать бинарный поиск в сочетании с хешированием строк:

Бинарный поиск по длине общей подстроки
Для каждой потенциальной длины L проверяем, существует ли общая подстрока такой длины
Проверку выполняем с помощью хеширования и хеш-таблицы
Алгоритм
public class LongestCommonSubstring {
private static final long P = 31; // Основание хеша
private static final long MOD = 1000000007; // Модуль

    public static String findLCS(String s, String t) {
        int n = s.length();
        int m = t.length();
        
        // Бинарный поиск по длине общей подстроки
        int left = 0;
        int right = Math.min(n, m);
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String candidate = checkLengthL(s, t, mid);
            
            if (!candidate.isEmpty()) {
                // Нашли общую подстроку длины mid
                result = candidate;
                left = mid + 1; // Ищем более длинную
            } else {
                // Общей подстроки длины mid нет
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Проверяет, существует ли общая подстрока длины L
    private static String checkLengthL(String s, String t, int L) {
        if (L == 0) return "";
        
        int n = s.length();
        int m = t.length();
        
        // Предварительные вычисления для хеширования
        long[] hashS = computeHashes(s, L);
        
        // Вычисляем степень P^(L-1)
        long pPow = 1;
        for (int i = 0; i < L - 1; i++) {
            pPow = (pPow * P) % MOD;
        }
        
        // Хеши всех подстрок длины L из первой строки
        Set<Long> hashSet = new HashSet<>();
        Map<Long, Integer> hashToPos = new HashMap<>();
        
        for (int i = 0; i <= n - L; i++) {
            hashSet.add(hashS[i]);
            hashToPos.put(hashS[i], i);
        }
        
        // Проверяем подстроки второй строки
        long[] hashT = computeHashes(t, L);
        
        for (int i = 0; i <= m - L; i++) {
            if (hashSet.contains(hashT[i])) {
                // Потенциальное совпадение, проверяем напрямую
                int pos = hashToPos.get(hashT[i]);
                if (s.substring(pos, pos + L).equals(t.substring(i, i + L))) {
                    return s.substring(pos, pos + L);
                }
            }
        }
        
        return "";
    }
    
    // Вычисляет хеши всех подстрок длины L
    private static long[] computeHashes(String s, int L) {
        int n = s.length();
        long[] hashes = new long[n - L + 1];
        
        // Вычисляем хеш первой подстроки
        long hash = 0;
        for (int i = 0; i < L; i++) {
            hash = (hash * P + (s.charAt(i) - 'a' + 1)) % MOD;
        }
        hashes[0] = hash;
        
        // Вычисляем степень P^(L-1)
        long pPow = 1;
        for (int i = 0; i < L - 1; i++) {
            pPow = (pPow * P) % MOD;
        }
        
        // Вычисляем хеши остальных подстрок используя скользящее окно
        for (int i = 1; i <= n - L; i++) {
            hash = ((hash - ((s.charAt(i - 1) - 'a' + 1) * pPow) % MOD + MOD) % MOD * P + 
                   (s.charAt(i + L - 1) - 'a' + 1)) % MOD;
            hashes[i] = hash;
        }
        
        return hashes;
    }
    
    public static void main(String[] args) {
        String s1 = "abcdefghi";
        String s2 = "defgabcxyz";
        
        String lcs = findLCS(s1, s2);
        
        System.out.println("Строка 1: " + s1);
        System.out.println("Строка 2: " + s2);
        System.out.println("Наибольшая общая подстрока: " + lcs);
    }
}
Анализ сложности
Временная сложность: O(n log n)

Бинарный поиск выполняется O(log n) раз
На каждой итерации мы вычисляем хеши и проверяем совпадения за O(n)
Итого: O(n log n)
Пространственная сложность: O(n)

Хранение хешей подстрок и хеш-таблицы
Альтернативные подходы
1. Суффиксные массивы
   Можно решить эту задачу за O(n log n) с помощью суффиксных массивов:

Объединяем строки S и T с разделителем: S#T
Строим суффиксный массив и LCP массив
Находим максимальный LCP между суффиксами из разных строк
2. Суффиксный автомат
   Суффиксный автомат позволяет решить задачу за линейное время O(n+m):

Строим суффиксный автомат для строки S
Проходим по строке T, отслеживая максимальную длину пути в автомате
Оптимизации
Двойное хеширование: Для уменьшения вероятности коллизий можно использовать два разных хеша с разными основаниями и модулями.

Оптимизация памяти: Вместо хранения всех хешей можно хранить только текущий обрабатываемый набор.

Раннее прерывание: Если длина оставшейся части строки меньше текущего максимума, можно прервать поиск.

Заключение
Алгоритм, основанный на бинарном поиске и хешировании, является эффективным решением для нахождения наибольшей общей подстроки за O(n log n). Он сочетает в себе простоту реализации и хорошую производительность для большинства практических задач.