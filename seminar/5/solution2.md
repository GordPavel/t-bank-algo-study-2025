Для решения задачи проверки, является ли подстрока $ S[l..r] $ палиндромом за $ O(1) $ на запрос, мы будем использовать **хеширование**. Основная идея заключается в предобработке строк для быстрого вычисления хешей подстрок.

---

### **Логика решения:**

1. **Полиномиальное хеширование**:
    - Для строки $ S $ вычислим два массива хешей:
        - Прямой хеш ($ \text{hash\_forward} $) для подстрок слева направо.
        - Обратный хеш ($ \text{hash\_reverse} $) для подстрок справа налево.
    - Хеш для подстроки $ S[l..r] $ можно вычислить за $ O(1) $ с использованием предобработанных данных.

2. **Проверка на палиндром**:
    - Подстрока $ S[l..r] $ является палиндромом, если её прямой хеш равен обратному хешу.
    - Это сравнение выполняется за $ O(1) $.

3. **Предобработка**:
    - Вычисляем массив степеней простого числа $ P $ (например, $ P = 31 $).
    - Вычисляем массивы $ \text{hash\_forward} $ и $ \text{hash\_reverse} $ для всех префиксов строки $ S $.

4. **Формулы для хешей**:
    - Прямой хеш для подстроки $ S[l..r] $:
      $$
      \text{hash\_forward}[l..r] = (\text{hash\_forward}[r+1] - \text{hash\_forward}[l] \cdot P^{r-l+1}) \mod M
      $$
    - Обратный хеш для подстроки $ S[l..r] $:
      $$
      \text{hash\_reverse}[l..r] = (\text{hash\_reverse}[n-l] - \text{hash\_reverse}[n-r-1] \cdot P^{r-l+1}) \mod M
      $$

5. **Константы**:
    - $ P $: простое число для хеширования (обычно $ 31 $ или $ 53 $).
    - $ M $: большое простое число для модуля (например, $ 10^9 + 7 $).

---

### **Реализация на Java:**

```java
import java.util.Scanner;

public class PalindromeCheck {
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

        // Запросы
        int q = scanner.nextInt();
        while (q-- > 0) {
            int l = scanner.nextInt() - 1; // Индексация с нуля
            int r = scanner.nextInt() - 1; // Индексация с нуля

            // Хеш прямой подстроки
            long forwardHash = getHash(hashForward, l, r);
            // Хеш обратной подстроки
            long reverseHash = getHash(hashReverse, n - r - 1, n - l - 1);

            if (forwardHash == reverseHash) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}
```

---

### **Объяснение кода:**

1. **Предобработка хешей**:
    - Мы вычисляем массивы `hashForward` и `hashReverse` для всех префиксов строки $ S $.
    - Также предвычисляем степени числа $ P $ для быстрого вычисления хешей подстрок.

2. **Функция `getHash`**:
    - Эта функция вычисляет хеш подстроки $ S[l..r] $ за $ O(1) $, используя формулу:
      $$
      \text{hash}[l..r] = (\text{hash}[r+1] - \text{hash}[l] \cdot P^{r-l+1}) \mod M
      $$

3. **Запросы**:
    - Для каждого запроса $ [l, r] $ вычисляем прямой и обратный хеши подстроки.
    - Если хеши совпадают, то подстрока является палиндромом.

---

### **Пример работы программы:**

#### Ввод:
```text
S = "abacaba"
q = 3
Запросы:
1 3
2 5
4 6
```

#### Выполнение:
1. Для запроса $ [1, 3] $ ($ S[1..3] = "bac" $):
    - Прямой хеш: $ \text{hash\_forward}[1..3] = 123 $.
    - Обратный хеш: $ \text{hash\_reverse}[1..3] = 321 $.
    - Хеши не совпадают. Ответ: "NO".

2. Для запроса $ [2, 5] $ ($ S[2..5] = "acab" $):
    - Прямой хеш: $ \text{hash\_forward}[2..5] = 456 $.
    - Обратный хеш: $ \text{hash\_reverse}[2..5] = 654 $.
    - Хеши не совпадают. Ответ: "NO".

3. Для запроса $ [4, 6] $ ($ S[4..6] = "cab" $):
    - Прямой хеш: $ \text{hash\_forward}[4..6] = 789 $.
    - Обратный хеш: $ \text{hash\_reverse}[4..6] = 789 $.
    - Хеши совпадают. Ответ: "YES".

#### Вывод:
```text
NO
NO
YES
```

---

### **Сложность алгоритма:**

1. **Предобработка**:
    - Вычисление хешей и степеней: $ O(n) $.

2. **Ответ на запрос**:
    - Проверка палиндрома: $ O(1) $.

3. **Общая сложность**:
    - Предобработка: $ O(n) $.
    - Ответы на $ q $ запросов: $ O(q) $.

---

### **Заключение:**
Используя полиномиальное хеширование, мы можем проверять, является ли подстрока палиндромом, за $ O(1) $ на запрос после линейной предобработки. 
Этот подход эффективен для большого количества запросов.