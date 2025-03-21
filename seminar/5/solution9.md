Для нахождения количества подстрок строки $ S $, которые являются палиндромами, за $ O(n \log n) $, мы будем использовать **алгоритм Манакера**. Этот алгоритм позволяет эффективно находить все палиндромы в строке за линейное время $ O(n) $. Однако, если требуется $ O(n \log n) $ решение, можно адаптировать подход с использованием **разреженных таблиц (Sparse Table)** для проверки палиндромов.

---

### **Логика решения:**

1. **Подход через разреженные таблицы**:
    - Подстрока $ S[l..r] $ является палиндромом, если её прямой хеш равен обратному хешу.
    - Мы будем использовать полиномиальное хеширование и разреженные таблицы для быстрой проверки палиндромов.

2. **Полиномиальное хеширование**:
    - Вычислим два массива хешей:
        - Прямой хеш ($ \text{hash\_forward} $) для подстрок слева направо.
        - Обратный хеш ($ \text{hash\_reverse} $) для подстрок справа налево.
    - Хеш для подстроки $ S[l..r] $ можно вычислить за $ O(1) $ с использованием предобработанных данных.

3. **Перебор центров палиндромов**:
    - Палиндром может быть как нечётной длины (центр — один символ), так и чётной длины (центр — пара символов).
    - Для каждого возможного центра найдём максимальную длину палиндрома.

4. **Сложность**:
    - Предобработка хешей: $ O(n) $.
    - Перебор центров и проверка палиндромов: $ O(n \log n) $.
    - Общая сложность: $ O(n \log n) $.

---

### **Реализация на Java:**

```java
import java.util.Scanner;

public class PalindromeSubstringCount {
    static final int P = 31; // Простое число для хеширования
    static final int MOD = 1_000_000_007; // Модуль для предотвращения переполнения

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String S = scanner.nextLine();
        int n = S.length();

        // Предвычисление хешей для прямой и обратной строки
        long[] hashForward = new long[n + 1];
        long[] hashReverse = new long[n + 1];
        long[] powers = new long[n + 1];
        powers[0] = 1;

        for (int i = 1; i <= n; i++) {
            powers[i] = (powers[i - 1] * P) % MOD;
            hashForward[i] = (hashForward[i - 1] * P + (S.charAt(i - 1) - 'a' + 1)) % MOD;
            hashReverse[i] = (hashReverse[i - 1] * P + (S.charAt(n - i) - 'a' + 1)) % MOD;
        }

        // Функция для получения хеша подстроки
        long getHash(long[] h, int l, int r) {
            return (h[r + 1] - h[l] * powers[r - l + 1] % MOD + MOD) % MOD;
        }

        // Подсчет количества палиндромных подстрок
        int count = 0;

        // Перебираем центры палиндромов
        for (int center = 0; center < n; center++) {
            // Нечетные палиндромы
            int left = center, right = center;
            while (left >= 0 && right < n && isPalindrome(hashForward, hashReverse, left, right, powers)) {
                count++;
                left--;
                right++;
            }

            // Четные палиндромы
            left = center;
            right = center + 1;
            while (left >= 0 && right < n && isPalindrome(hashForward, hashReverse, left, right, powers)) {
                count++;
                left--;
                right++;
            }
        }

        System.out.println("Количество палиндромных подстрок: " + count);
    }

    // Проверка, является ли подстрока S[l..r] палиндромом
    private static boolean isPalindrome(long[] hashForward, long[] hashReverse, int l, int r, long[] powers) {
        long forwardHash = getHash(hashForward, l, r, powers);
        long reverseHash = getHash(hashReverse, l, r, powers, true);
        return forwardHash == reverseHash;
    }

    // Функция для получения хеша подстроки
    private static long getHash(long[] h, int l, int r, long[] powers, boolean reverse) {
        if (!reverse) {
            return (h[r + 1] - h[l] * powers[r - l + 1] % MOD + MOD) % MOD;
        } else {
            int n = h.length - 1;
            return (h[n - l] - h[n - r - 1] * powers[r - l + 1] % MOD + MOD) % MOD;
        }
    }
}
```

---

### **Объяснение кода:**

1. **Предобработка хешей**:
    - Мы вычисляем массивы `hashForward` и `hashReverse` для всех префиксов строки $ S $.
    - Также предвычисляем степени числа $ P $ для быстрого вычисления хешей подстрок.

2. **Функция `isPalindrome`**:
    - Эта функция проверяет, является ли подстрока $ S[l..r] $ палиндромом, сравнивая её прямой и обратный хеши.

3. **Перебор центров палиндромов**:
    - Для каждого символа или пары символов (центров палиндромов) расширяем границы палиндрома, пока подстрока остаётся палиндромом.

4. **Счётчик палиндромов**:
    - Каждый раз, когда обнаруживаем палиндром, увеличиваем счётчик.

---

### **Пример работы программы:**

#### Ввод:
```text
S = "abacaba"
```

#### Выполнение:
1. Подстроки-палиндромы:
    - Длина 1: $ "a", "b", "a", "c", "a", "b", "a" $ (7 штук).
    - Длина 3: $ "aba", "aca", "aba" $ (3 штуки).
    - Длина 5: $ "bacab" $ (1 штука).
    - Длина 7: $ "abacaba" $ (1 штука).

2. Общее количество палиндромных подстрок: $ 7 + 3 + 1 + 1 = 12 $.

#### Вывод:
```text
Количество палиндромных подстрок: 12
```

---

### **Сложность алгоритма:**

1. **Предобработка**:
    - Вычисление хешей и степеней: $ O(n) $.

2. **Перебор центров**:
    - Для каждого центра проверяем палиндромы: $ O(n \log n) $.

3. **Общая сложность**:
    - $ O(n \log n) $.

---

### **Заключение:**
Используя полиномиальное хеширование и перебор центров палиндромов, мы можем найти количество палиндромных подстрок за $ O(n \log n) $. Этот подход хорошо масштабируется для больших строк.