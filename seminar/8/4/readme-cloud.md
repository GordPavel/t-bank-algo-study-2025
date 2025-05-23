Решение задачи: Проверка правильности скобочной последовательности

Я решу задачу о строке из n круглых скобок, где нужно обрабатывать запросы:
Изменить i-ю скобку
Проверить, является ли правильной скобочной последовательностью подстрока с l до r

Для решения этой задачи я буду использовать дерево отрезков с дополнительной информацией для эффективной проверки правильности скобочной последовательности.

import java.util.*;

public class BracketSequence {
static class Node {
int open;       // Количество открывающих скобок, которые не имеют пары
int close;      // Количество закрывающих скобок, которые не имеют пары
boolean isValid; // Является ли последовательность правильной

        public Node() {
            open = 0;
            close = 0;
            isValid = true;
        }
        
        public Node(int open, int close, boolean isValid) {
            this.open = open;
            this.close = close;
            this.isValid = isValid;
        }
        
        // Метод для объединения двух узлов (результатов для левого и правого поддеревьев)
        public static Node merge(Node left, Node right) {
            Node result = new Node();
            
            // Количество закрывающих скобок без пары в левом поддереве
            int unpaired_close_left = left.close;
            
            // Количество открывающих скобок без пары в правом поддереве
            int unpaired_open_right = right.open;
            
            // Количество пар, которые можно образовать между левым и правым поддеревьями
            int paired = Math.min(unpaired_close_left, unpaired_open_right);
            
            // Оставшиеся открывающие скобки без пары
            result.open = left.open + (right.open - paired);
            
            // Оставшиеся закрывающие скобки без пары
            result.close = (left.close - paired) + right.close;
            
            // Последовательность правильная, если обе подпоследовательности правильные
            // и все скобки между ними образуют пары
            result.isValid = left.isValid && right.isValid && (left.close == 0 || right.open == 0);
            
            return result;
        }
    }
    
    private Node[] tree;
    private char[] brackets;
    private int n;
    
    /**
     Инициализирует дерево отрезков для строки скобок.
     * @param s строка скобок
     */
    public BracketSequence(String s) {
        brackets = s.toCharArray();
        n = brackets.length;
        
        // Выделяем память для дерева отрезков (4*n достаточно для любого n)
        tree = new Node[4 * n];
        
        // Строим дерево отрезков
        build(0, 0, n - 1);
    }
    
    /**
     Рекурсивно строит дерево отрезков.
     * @param node индекс текущего узла в дереве
     @param start начало отрезка
     @param end конец отрезка
     */
    private void build(int node, int start, int end) {
        if (start == end) {
            // Листовой узел - одна скобка
            tree[node] = new Node();
            if (brackets[start] == '(') {
                tree[node].open = 1;
                tree[node].close = 0;
            } else {
                tree[node].open = 0;
                tree[node].close = 1;
            }
            tree[node].isValid = (tree[node].open == 0 && tree[node].close == 0); // Одна скобка не может быть правильной последовательностью
            return;
        }
        
        int mid = (start + end) / 2;
        
        // Рекурсивно строим левое и правое поддеревья
        build(2 * node + 1, start, mid);
        build(2 * node + 2, mid + 1, end);
        
        // Объединяем информацию из поддеревьев
        tree[node] = Node.merge(tree[2 * node + 1], tree[2 * node + 2]);
    }
    
    /**
     Изменяет i-ю скобку.
     * @param idx индекс скобки (0-indexed)
     @param bracket новая скобка ('(' или ')')
     */
    public void update(int idx, char bracket) {
        if (brackets[idx] == bracket) {
            return; // Скобка не изменилась
        }
        
        brackets[idx] = bracket;
        update(0, 0, n - 1, idx);
    }
    
    /**
     Рекурсивно обновляет дерево отрезков после изменения скобки.
     * @param node индекс текущего узла в дереве
     @param start начало отрезка
     @param end конец отрезка
     @param idx индекс обновляемой скобки
     */
    private void update(int node, int start, int end, int idx) {
        if (start == end) {
            // Листовой узел - обновляем информацию о скобке
            if (brackets[start] == '(') {
                tree[node].open = 1;
                tree[node].close = 0;
            } else {
                tree[node].open = 0;
                tree[node].close = 1;
            }
            tree[node].isValid = (tree[node].open == 0 && tree[node].close == 0);
            return;
        }
        
        int mid = (start + end) / 2;
        if (idx  r || end = l && end  r || end = l && end <= r) {
        return tree[node];
    }
    
    // Если отрезок частично пересекается с запрашиваемым диапазоном
    int mid = (start + end) / 2;
    Node leftResult = query(2 * node + 1, start, mid, l, r);
    Node rightResult = query(2 * node + 2, mid + 1, end, l, r);
    
    return Node.merge(leftResult, rightResult);
}

Метод isValid проверяет, является ли подстрока с l до r правильной скобочной последовательностью:
Вызывает метод query для получения информации о подстроке
Проверяет, что:
Последовательность правильная (result.isValid)
Нет избытка открывающих скобок (result.open == 0)
Нет избытка закрывающих скобок (result.close == 0)

Метод query рекурсивно выполняет запрос на получение информации о подстроке:
Если текущий отрезок полностью вне запрашиваемого диапазона, возвращаем пустую последовательность (которая считается правильной)
Если текущий отрезок полностью внутри запрашиваемого диапазона, возвращаем информацию из соответствующего узла дерева
Иначе разделяем отрезок пополам и рекурсивно выполняем запрос для левого и правого поддеревьев, затем объединяем результаты с помощью метода merge

7. Временная сложность

Построение дерева отрезков: O(n)
Обновление скобки: O(log n)
Проверка правильности скобочной последовательности: O(log n)

8. Пространственная сложность

Дерево отрезков: O(n)
Массив скобок: O(n)

Общая пространственная сложность: O(n)

9. Пример использования

В функции main я реализовал интерактивный интерфейс для работы со строкой скобок:
Пользователь вводит исходную строку скобок
Затем пользователь может выполнять запросы:
Изменить i-ю скобку
Проверить, является ли правильной скобочной последовательностью подстрока с l до r

10. Дополнительные пояснения

Почему мы храним open и close?

Для проверки правильности скобочной последовательности нам нужно знать:
Сбалансирована ли последовательность (количество открывающих скобок равно количеству закрывающих)
Нет ли на каком-либо префиксе избытка закрывающих скобок

Храня количество непарных открывающих и закрывающих скобок, мы можем эффективно объединять информацию о подпоследовательностях.

Почему мы проверяем left.close == 0 || right.open == 0 в методе merge?

Это условие гарантирует, что при объединении двух подпоследовательностей не возникает ситуация, когда закрывающие скобки из левой части идут раньше открывающих скобок из правой части. Такая ситуация нарушает правильность скобочной последовательности.

Почему пустая последовательность считается правильной?

По определению, пустая последовательность не содержит скобок, поэтому в ней нет непарных скобок, и она считается правильной.

Почему одна скобка не может быть правильной последовательностью?

Одна скобка (открывающая или закрывающая) не может образовать пару, поэтому она всегда будет непарной, что нарушает условие правильности скобочной последовательности.