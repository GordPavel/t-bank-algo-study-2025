Есть массив a (он не меняется). Отвечать на запросы: сколько различных чисел на отрезке arl..rs?
Не хуже Oplog2
nq на запрос.

Для решения задачи о подсчете количества различных чисел на отрезке массива с использованием запросов за O(log² n) можно использовать структуру данных, известную как дерево отрезков (Segment Tree) с дополнительной информацией о количестве уникальных элементов. Однако, более эффективным и распространенным подходом для этой задачи является использование Mo's алгоритма.

Mo's алгоритм
Mo's алгоритм позволяет обрабатывать запросы на подсчет уникальных элементов на отрезках массива за O((n + q) * sqrt(n)), где n — размер массива, а q — количество запросов. Это достигается за счет сортировки запросов и использования двух указателей для поддержания текущего отрезка.

Класс Query: Представляет запрос с левым и правым индексами и индексом запроса.
Метод processQueries: Обрабатывает запросы, используя Mo's алгоритм.
Сортировка запросов: Запросы сортируются по блокам и правой границе для оптимизации.
Методы add и remove: Обновляют количество уникальных элементов при добавлении или удалении элемента из текущего отрезка.