# Тесты к курсу «Парадигмы программирования»

[Условия домашних заданий](https://www.kgeorgiy.info/courses/paradigms/homeworks.html)

## Домашнее задание 14. Дерево поиска на Prolog

Модификации
 * *Базовая*
    * Код должен находиться в файле `prolog-solutions/tree-map.pl`.
    * [Исходный код тестов](prolog/prtest/tree/TreeTest.java)
        * Запускать c указанием сложности (`easy` или `hard`) и модификации
 * *RemoveCeiling* (36, 37)
    * Добавьте правила:
        * `map_ceilingEntry(Map, Key, Entry)`,
            возвращающее пару, соответствующую минимальному ключу,
            большему либо равному заданному;
        * `map_removeCeiling(Map, Key, Result)`,
            удаляющее пару, соответствующую минимальному ключу,
            большему либо равному заданному (если такой существует).


## Домашнее задание 13. Простые числа на Prolog

Модификации
 * *Базовая*
    * Код должен находиться в файле `prolog-solutions/primes.pl`.
    * [Исходный код тестов](prolog/prtest/primes/PrimesTest.java)
        * Запускать c указанием сложности (`easy`, `hard` или `bonus`) и модификации.
 * *Gcd* 
    * Добавьте правило `gcd(A, B, GCD)`,
      подсчитывающее НОД(`A`, `B`) через разложение на простые множители:
      `gcd(4, 6, 2)`.


Для запуска тестов можно использовать скрипты
[TestProlog.cmd](prolog/TestProlog.cmd) и [TestProlog.sh](prolog/TestProlog.sh)
 * Репозиторий должен быть скачан целиком.
 * Скрипты должны находиться в каталоге `prolog`
    (их нельзя перемещать, но можно вызывать из других каталогов).
 * Полное имя класса теста указывается в качестве первого аргумента командной строки,
    например, `prtest.primes.PrimesTest`.
 * Тестируемое решение должно находиться в текущем каталоге.


## Исходный код к лекциям по Prolog

Запуск Prolog
 * [Windows](prolog/RunProlog.cmd)
 * [*nix](prolog/RunProlog.sh)



## Домашнее задание 12. Комбинаторные парсеры

Модификации
 * *Base*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/parsing/ParserTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *Complex*. Сделать модификацию *Variables* и дополнительно реализовать поддержку:
    * Правоассоциативных бинарных операциий над комплексными числами в форме `im op re`:
        * `AbsC` (`absc`) – модуль `3 absc 4` равно 5;
        * `PhiC` (`phic`) – аргумент `841 phic 540` примерно равно 1.

## Домашнее задание 11. Объектные выражения на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/object/ObjectTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *MeanVarn*. Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Mean` (`mean`) – математическое ожидание аргументов, `(mean 1 2 6)` равно 3;
        * `Varn` (`varn`) – дисперсия аргументов, `(varn 2 5 11)` равно 14;

## Домашнее задание 10. Функциональные выражения на Clojure

Модификации
 * *Base*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/functional/FunctionalTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).

 * *MeanVarn*. Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `mean` – математическое ожидание аргументов, `(mean 1 2 6)` равно 3;
        * `varn` – дисперсия аргументов, `(varn 2 5 11)` равно 14.

## Домашнее задание 9. Линейная алгебра на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `clojure-solutions/linear.clj`.
    * [Исходный код тестов](clojure/cljtest/linear/LinearTest.java)
        * Запускать c указанием сложности (`easy` или `hard`) и модификации.
 * *Simplex*
    * Назовем _симплексом_ многомерную таблицу чисел,
      такую что для некоторого `n` в ней существуют все значения
      с суммой индексов не превышающей `n` и только эти значения.
    * Добавьте операции поэлементного
        сложения (`x+`), вычитания (`x-`), умножения (`x*`) и деления (`xd`)
        симплексов.
        Например, `(x+ [[1 2] [3]] [[5 6] [7]])`
        должно быть равно `[[6 8] [10]]`.
    * [Исходный код тестов](clojure/cljtest/linear/SimplexTester.java)


## Исходный код к лекциям по Clojure

Документация
 * [Clojure Reference](https://clojure.org/reference/documentation)
 * [Clojure Cheat Sheet](https://clojure.org/api/cheatsheet)

Запуск Clojure
 * Консоль: [Windows](clojure/RunClojure.cmd), [*nix](clojure/RunClojure.sh)
    * Интерактивный: `RunClojure`
    * С выражением: `RunClojure --eval "<выражение>"`
    * Скрипт: `RunClojure <файл скрипта>`
    * Справка: `RunClojure --help`
 * IDE
    * IntelliJ Idea: [плагин Cursive](https://cursive-ide.com/userguide/)
    * Eclipse: [плагин Counterclockwise](https://marketplace.eclipse.org/content/counterclockwise)



## Домашнее задание 8. Обработка ошибок на JavaScript

Модификации
 * *Base*
    * Код должен находиться в файле `javascript-solutions/objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/prefix/ParserTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *Postfix*. Дополнительно реализовать поддержку:
    * Выражений в постфиксной записи:
        * `(2 3 +)` равно 5
        * функция `parsePostfix`
        * метод `postfix`
    * [Исходный код тестов](javascript/jstest/prefix/PostfixTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *Postfix*: *MeanVar*. Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Mean` (`mean`) – математическое ожидание аргументов, `(1 2 6 mean)` равно 3;
        * `Var` (`var`) – дисперсия аргументов, `(2 5 11 var)` равно 14.


## Домашнее задание 7. Объектные выражения на JavaScript

Модификации
 * *Base*
    * Код должен находиться в файле `javascript-solutions/objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/object/ObjectTest.java)
        * Запускать c указанием модификации и сложности (`easy`, `hard` или `bonus`).
 * *ArcTan*. Дополнительно реализовать поддержку:
    * функций:
        * `ArcTan` (`atan`) – арктангенс, `1256 atan` примерно равно 1.57;
        * `ArcTan2` (`atan2`) – арктангенс, `841 540 atan2` примерно равно 1.


## Домашнее задание 6. Функциональные выражения на JavaScript

Модификации
 * *Базовая*
    * Код должен находиться в файле `javascript-solutions/functionalExpression.js`.
    * [Исходный код тестов](javascript/jstest/functional/FunctionalTest.java)
        * Запускать c аргументом `hard` или `easy`.
 * *Pie*. Дополнительно реализовать поддержку:
    * констант:
        * `pi` – π;
        * `e` – основание натурального логарифма;
 * *PieMinMax*. Дополнительно реализовать поддержку:
    * модификации *Pie*
    * операций:
        * `min5` – минимальный из пяти аргументов, `3 1 4 1 5 min5` равно 1;
        * `max3` – максимальный из трёх аргументов, `3 1 4 max3` равно 4.

   

Запуск тестов
 * Для запуска тестов используется [GraalJS](https://github.com/graalvm/graaljs)
   (часть проекта [GraalVM](https://www.graalvm.org/)), но вам не требуется их скачивать.
 * Для запуска тестов рекомендуется использовать скрипты 
   [TestJS.cmd](javascript/TestJS.cmd) и [TestJS.sh](javascript/TestJS.sh)
    * Репозиторий должен быть скачан целиком.
    * Скрипты должны находиться в каталоге `javascript` (их нельзя перемещать, но можно вызывать из других каталогов).
    * В качестве аргументов командной строки указывается полное имя класса теста и модификация,
      например `jstest.example.ExampleTest hard base`.
 * Для самостоятельно запуска из консоли необходимо использовать командную строку вида:
    `java -ea --module-path=<js>/graal --class-path <js> jstest.example.ExampleTest {hard|easy} <variant>`, где
    * `-ea` – включение проверок времени исполнения;
    * `--module-path=<js>/graal` путь к модулям Graal (здесь и далее `<js>` путь к каталогу `javascript` этого репозитория);
    * `--class-path <js>` путь к откомпилированным тестам;
    * {`hard`|`easy`} указание тестируемой сложности;
    * `<variant>`} указание тестируемой модификации.
 * При запуске из IDE, обычно не требуется указывать `--class-path`, так как он формируется автоматически.
   Остальные опции все равно необходимо указать.
 * Troubleshooting
    * `Error occurred during initialization of boot layer java.lang.module.FindException: Module org.graalvm.truffle not found, required by jdk.internal.vm.compiler` 
      – неверно указан `--module-path`;
    * `Graal.js not found` – неверно указаны `--module-path`
    * `Error: Could not find or load main class jstest.example.ExampleTest` 
      – неверно указан `--class-path`;
    * `Exception in thread "main" java.lang.AssertionError: You should enable assertions by running 'java -ea jstest.functional.FunctionalExpressionTest'` 
      – не указана опция `-ea`;
    * `Exception in thread "main" jstest.EngineException: Script 'example.js' not found` 
      – в текущем каталоге отсутствует решение (`example.js`)


## Домашнее задание 5. Вычисление в различных типах

Модификации
 * *Base*
    * Класс `GenericTabulator` должен реализовывать интерфейс
      [Tabulator](java/expression/generic/Tabulator.java) и
      строить трехмерную таблицу значений заданного выражения.
        * `mode` – режим вычислений:
           * `i` – вычисления в `int` с проверкой на переполнение;
           * `d` – вычисления в `double` без проверки на переполнение;
           * `bi` – вычисления в `BigInteger`.
        * `expression` – выражение, для которого надо построить таблицу;
        * `x1`, `x2` – минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` – аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
    * [Исходный код тестов](java/expression/generic/GenericTest.java)
        * Первый аргумент: `easy` или `hard`
        * Последующие аргументы: модификации
 * *CmmUbb* 
    * Дополнительно реализуйте поддержку режимов:
        * `u` – вычисления в `int` без проверки на переполнение;
        * `b` – вычисления в `byte` без проверки на переполнение,
        * `bool` – вычисления в `boolean`.



## Домашнее задание 4. Очереди

Модификации
 * *Базовая*
    * [Исходный код тестов](java/queue/QueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/QueueTest.jar)
    * Для работы тестов необходимо добавить опцию JVM `--add-opens java.base/java.util=ALL-UNNAMED`
 * *FlatMap* 
    * Добавить в интерфейс очереди и реализовать метод
      `flatMap(function)` – создать очередь, содержащую результаты применения
      [функции](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html)
      из элемента в список элементов
    * Исходная очередь должна остаться неизменной
    * Тип возвращаемой очереди должен соответствовать типу исходной очереди
    * Взаимный порядок элементов должен сохраняться
    * Дублирования кода быть не должно


## Домашнее задание 3. Очередь на массиве

Модификации
 * *Базовая*
    * Классы должны находиться в пакете `queue`
    * [Исходный код тестов](java/queue/ArrayQueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueTest.jar)
 * *Deque*
    * Дополнительно реализовать методы
        * `push` – добавить элемент в начало очереди;
        * `peek` – вернуть последний элемент в очереди;
        * `remove` – вернуть и удалить последний элемент из очереди.
 * *DequeCountIf* 
    * Реализовать модификацию *Deque*;
    * Реализовать метод `countIf`, возвращающий число элеменов очереди, удовлетворяющих
      [предикату](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Predicate.html).


Если при тестировании вы получаете ошибку
`... module java.base does not "opens java.util" to unnamed module ...`
(характерно для Java 17+), то при запуске тестов добавьте опции
`--add-opens` и `java.base/java.util=ALL-UNNAMED`.


## Домашнее задание 2. Бинарный поиск

Модификации
 * *Базовая*
    * Класс `BinarySearch` должен находиться в пакете `search`
    * [Исходный код тестов](java/search/BinarySearchTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchTest.jar)
 * *ClosestI* 
    * На вход подаётся число `x` и непустой массив, отсортированный по неубыванию.
    * Требуется вывести минимальный индекс элемента массива наименее отличающегося от `x`.
    * Требуется вывести минимальный индекс элемента массива наименее отличающегося от `x`.
    * Класс должен иметь имя `BinarySearchClosestI`


Для того, чтобы протестировать базовую модификацию домашнего задания:

 1. Скачайте тесты ([BinarySearchTest.jar](artifacts/search/BinarySearchTest.jar))
 1. Откомпилируйте `BinarySearch.java`
 1. Проверьте, что создался `BinarySearch.class`
 1. В каталоге, в котором находится `search/BinarySearch.class` выполните команду

    ```
       java -jar <путь к BinarySearchTest.jar> Base
    ```

    Например, если `BinarySearchTest.jar` находится в текущем каталоге,
    а `BinarySearch.class` в каталоге `search`, выполните команду

    ```
        java -jar BinarySearchTest.jar Base
    ```


## Домашнее задание 1. Обработка ошибок

Модификации
 * *Base*
    * Класс `ExpressionParser` должен реализовывать интерфейс
        [TripleParser](java/expression/exceptions/TripleParser.java)
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс
        [TripleExpression](java/expression/TripleExpression.java)
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
    * [Исходный код тестов](java/expression/exceptions/ExceptionsTest.java)
        * Первый аргумент: `easy` или `hard`
        * Последующие аргументы: модификации
 * *MinMax* 
    * Дополнительно реализуйте бинарные операции (минимальный приоритет):
        * `min` – минимум, `2 min 3` равно 2;
        * `max` – максимум, `2 max 3` равно 3.
 * *Parens* 
    * Дополнительно реализуйте поддержку квадратных и фигурных скобок:
        * `([{1 + 2} * 3] + 5)` равно 14;
        * скобки дожны быть парными, `(1 + 2]` — ошибка.
 * *List* 
    * Класс `ExpressionParser` дополнительно должен реализовывать интерфейс
        [ListParser](java/expression/exceptions/ListParser.java)
        * порядок значений переменных, передаваемых в `evaluate`
          соответствует порядку переменных в вызове `parse`;
        * например, `parse("a + b * b", List.of("a", "b")).evaluate(List.of(2, 3))`
          равно 11;
        * у класса `Variable` должен быть конструктор от `int` — номера переменной.
