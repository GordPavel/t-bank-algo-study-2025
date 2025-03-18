Для проверки, можно ли получить строку $ T $ циклическим сдвигом строки $ S $ за $ O(n) $, мы будем использовать следующую идею: если строка $ T $ является циклическим сдвигом строки $ S $, то $ T $ должна быть подстрокой строки $ S + S $ (конкатенация строки $ S $ с самой собой).

---

### **Логика решения:**

1. **Циклический сдвиг**:
    - Циклический сдвиг строки $ S $ означает, что мы "перемещаем" символы строки по кругу.
    - Например, для строки $ S = "abcd" $ возможные циклические сдвиги: $ "abcd", "bcda", "cdab", "dabc" $.

2. **Конкатенация строки $ S $ с собой**:
    - Если строка $ T $ является циклическим сдвигом строки $ S $, то $ T $ обязательно будет подстрокой строки $ S + S $.
    - Например, для $ S = "abcd" $, $ S + S = "abcdabcd" $. Все возможные циклические сдвиги $ S $ содержатся в $ S + S $.

3. **Проверка подстроки**:
    - Для проверки, является ли $ T $ подстрокой $ S + S $, можно использовать алгоритм Кнута-Морриса-Пратта (КМП), который работает за $ O(n) $.

4. **Сложность**:
    - Построение строки $ S + S $: $ O(n) $.
    - Поиск подстроки $ T $ в $ S + S $: $ O(n) $.
    - Общая сложность: $ O(n) $.

---

### **Реализация на Java:**

```java
import java.util.Scanner;

public class CyclicShiftCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод строк S и T
        String S = scanner.nextLine();
        String T = scanner.nextLine();

        int n = S.length();

        // Проверяем, что длины строк совпадают
        if (T.length() != n) {
            System.out.println("NO");
            return;
        }

        // Создаем строку S + S
        String doubledS = S + S;

        // Проверяем, является ли T подстрокой S + S
        if (doubledS.contains(T)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
```

---

### **Объяснение кода:**

1. **Ввод данных**:
    - Считываем строки $ S $ и $ T $.
    - Проверяем, что их длины совпадают. Если нет, сразу выводим "NO".

2. **Конкатенация строки $ S $**:
    - Создаем строку $ S + S $, чтобы все возможные циклические сдвиги $ S $ содержались в ней.

3. **Проверка подстроки**:
    - Используем метод `contains` для проверки, является ли $ T $ подстрокой $ S + S $.
    - Если $ T $ содержится в $ S + S $, выводим "YES". Иначе — "NO".

---

### **Пример работы программы:**

#### Ввод:
```text
S = "abcd"
T = "cdab"
```

#### Выполнение:
1. Конкатенируем $ S + S = "abcdabcd" $.
2. Проверяем, содержится ли $ T = "cdab" $ в $ S + S $.
    - Да, $ "cdab" $ содержится в $ "abcdabcd" $.

#### Вывод:
```text
YES
```

---

#### Ввод:
```text
S = "abcd"
T = "dcba"
```

#### Выполнение:
1. Конкатенируем $ S + S = "abcdabcd" $.
2. Проверяем, содержится ли $ T = "dcba" $ в $ S + S $.
    - Нет, $ "dcba" $ не содержится в $ "abcdabcd" $.

#### Вывод:
```text
NO
```

---

### **Оптимизация через алгоритм КМП**

Если требуется строго $ O(n) $ без использования встроенных методов, таких как `contains`, можно реализовать алгоритм Кнута-Морриса-Пратта (КМП). Вот его реализация:

```java
import java.util.Scanner;

public class CyclicShiftCheckKMP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод строк S и T
        String S = scanner.nextLine();
        String T = scanner.nextLine();

        int n = S.length();

        // Проверяем, что длины строк совпадают
        if (T.length() != n) {
            System.out.println("NO");
            return;
        }

        // Создаем строку S + S
        String doubledS = S + S;

        // Используем КМП для поиска T в S + S
        if (kmpSearch(doubledS, T)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    // Алгоритм КМП для поиска подстроки pattern в тексте text
    public static boolean kmpSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        // Вычисляем префикс-функцию для pattern
        int[] pi = computePrefixFunction(pattern);

        int j = 0; // Текущая позиция в pattern
        for (int i = 0; i < n; i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == m) {
                return true; // Найдено вхождение
            }
        }

        return false; // Вхождение не найдено
    }

    // Вычисление префикс-функции для строки pattern
    public static int[] computePrefixFunction(String pattern) {
        int m = pattern.length();
        int[] pi = new int[m];
        pi[0] = 0;

        int j = 0;
        for (int i = 1; i < m; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }

        return pi;
    }
}
```

---

### **Сложность алгоритма:**

1. **Предобработка**:
    - Построение строки $ S + S $: $ O(n) $.
    - Вычисление префикс-функции для $ T $: $ O(n) $.

2. **Поиск подстроки**:
    - Поиск $ T $ в $ S + S $ с использованием КМП: $ O(n) $.

3. **Общая сложность**:
    - $ O(n) $.

---

### **Заключение:**
Используя конкатенацию строки $ S $ с собой и алгоритм КМП, мы можем проверить, является ли строка $ T $ циклическим сдвигом строки $ S $, за $ O(n) $. Этот подход эффективен и прост в реализации.