/**
 * Класс cutter(аналог конкатенатора)
 * 
 * @author Khokhlov Dmitrii
 * @version 2.0
 */

public class cutter {
    private String msymbol; // строка символов
    private int i; // позиция итератора в данной строке
    private int size; // размер строки (чтобы каждый раз не обращаться в функцию)
    boolean isReady; // Флаг для завершения обработки подстроки

    /**
     * Конструктор класса
     * 
     * @param value - входящая строка
     */
    cutter(String value) {
        msymbol = value;
        i = 0;
        size = msymbol.length();
        isReady = false;
    }

    /**
     * 
     * @return значение итератора
     */
    public int get_i() {
        return i;
    }

    /**
     * 
     * @return размер
     */
    public int get_size() {
        return size;
    }

    /**
     * Установка итератора на определенный индекс
     * @param ind
     */
    public void set_i(int ind){
        i = ind;
    }

    /**
     * Функция проверки корректности написанной формулы
     * 
     * @return верное или не верное выражение
     */
    public boolean correct() {
        int cLeft = 0, cRight = 0; // открывающие скобки
        boolean result = false;
        char symbol = '&';

        while (i < size && cLeft >= cRight) {
            symbol = msymbol.charAt(i);
            if (symbol == '(')
                cLeft++;
            if (symbol == ')')
                cRight++;
            i++;
        }
        i = 0;

        if (cLeft == cRight)
            result = true;

        /* Функция проверки лишней пары открывающей и закрывающей скобки */
        if (msymbol.charAt(0) == '(' && msymbol.charAt(msymbol.length() - 1) == ')') {
            String test = msymbol.substring(1, msymbol.length() - 1);
            cutter ex = new cutter(test);

            if (ex.correct()) {
                msymbol = test;
                size = size - 2;
            }
        }

        return result;
    }

    /**
     * Функция проверки строки на выражение
     * 
     * @see cutter#isOperator(char)
     * @return true - если выражение, иначе false
     */
    public boolean isItExpression() {
        boolean flag = false;
        i = 0;
        char symbol = '&';

        while (i < size && flag == false) {
            symbol = msymbol.charAt(i);

            if (isOperator(symbol) == true && i!=0)
                flag = true;
            else
                i++;
        }

        i = 0;
        return flag;
    }

    /**
     * Функция проверки входжной строки на выражение
     * 
     * @param value - входная строка
     * @see cutter#isOperator(char)
     * @return true - если выражение, иначе false
     */
    public boolean isItExpression(String value) {
        boolean flag = false;
        int ind = 0, msize = value.length();
        char symbol = '&';

        while (ind < msize && flag == false) {
            symbol = value.charAt(ind);

            if (isOperator(symbol) == true && i!=0)
                flag = true;
            else
                ind++;
        }

        return flag;
    }

    /**
     * Выделение подстроки путем подсчета открывающих и изакрывающих скобок
     * 
     * @see cutter#isItExpression()
     * @return подстроку
     */
    public String subStr() {
        String result = "";
        int cLeft = 0, cRight = 0;
        char symbol = '&';

        do {
            symbol = msymbol.charAt(i);

            if (symbol == '(')
                cLeft++;
            else if (symbol == ')')
                cRight++;

            result = result + symbol;
            i++;
        } while (cLeft != cRight);

        cutter check = new cutter(result);
        if (check.isItExpression() == false || (result.charAt(0) == '(' && result.charAt(result.length() - 1) == ')'))
            result = result.substring(1, result.length() - 1);

        return result;
    }

    /**
     * Выделение подстроки путем подсчета открывающих и изакрывающих скобок
     * 
     * @param ind - начиная с которого индекса надо выделить подстроку
     * @see cutter#isItExpression()
     * @return подстроку
     */
    public String subStr(int ind) {
        String result = "";
        int cLeft = 0, cRight = 0;
        char symbol = '&';

        do {
            symbol = msymbol.charAt(ind);

            if (symbol == '(')
                cLeft++;
            else if (symbol == ')')
                cRight++;

            result = result + symbol;
            ind++;
        } while (cLeft != cRight);

        cutter check = new cutter(result);
        if (check.isItExpression() == false || (result.charAt(0) == '(' && result.charAt(result.length() - 1) == ')'))
            result = result.substring(1, result.length() - 1);

        return result;
    }

    /**
     * Проверка символна на оператор
     * 
     * @param c - входной символ
     * @return true - если оператор
     */
    public boolean isOperator(char c) {
        return ((c == '+') || (c == '-') || (c == '/') || (c == '*') || (c == '^'));
    }

    /**
     * Проверка: я вляется ли строка числом
     * 
     * @param value -строка
     * @return true - если число, false - если нет
     */
    public boolean isDigit(String value) {
        boolean result = false;
        char first = value.charAt(0), second = '?';

        if (value.length() > 1)
            second = value.charAt(1);

        if (first == '-' && Character.isDigit(second) || Character.isDigit(first))
            result = true;

        return result;
    }

    /**
     * Главная функция для поэтапного разбиения строки на состовляющие:
     * числа - отрицательные и нет
     * буквенные выражения
     * знаки операций
     * скобки
     * 
     * @see cutter#isOperator(char)
     * @return
     */
    public String comparator() {
        char symbol = '#', prev = '?', next = '&'; // символ сейчас, предыдущий и следущий
        String result = ""; // итоговое выражение
        boolean isNegative = false; // указатель, что число отрицательное, а не просто знак

        while (i < size && isReady == false) {
            symbol = msymbol.charAt(i);

            if (i < size - 1)
                next = msymbol.charAt(i + 1);
            if (i > 0)
                prev = msymbol.charAt(i - 1);

            if (symbol != ' ' && ((symbol == '(' && next == '-') != true) && ((symbol == '-' && i==0)!=true)) { // пропускаем пробелы и лишние минусы перед
                                                                             // отрицательным числом
                result += symbol;

                // У цифр - знак, десятичность, и размер
                // 15-5 - знак минуса тут операнд
                // 25/(-7) - знак минуса тут это число
                // -12 - это тоже число

                if (Character.isDigit(symbol)) {
                    if (prev == '-' && i == 1)
                        result = '-' + result;

                    if (i < size - 1 && (Character.isDigit(next) || (next == '.')))
                        while (i < size - 1 && (Character.isDigit(next) || (next == '.'))) { // "сборка числа"
                            i++;
                            prev = symbol;
                            symbol = next;
                            if (i <= size - 2)
                                next = msymbol.charAt(i + 1);
                            else
                                next = '&';
                            result += symbol;
                        }
                    isReady = true;
                } else if (Character.isLetter(symbol)) {
                    if (i < size - 1 && Character.isLetter(next))
                        while (i < size - 1 && Character.isLetter(next)) { // "сборка букв"
                            i++;
                            prev = symbol;
                            symbol = next;
                            if (i <= size - 2)
                                next = msymbol.charAt(i + 1);
                            else
                                next = '#';
                            result += symbol;
                        }
                    isReady = true;
                } else if (symbol == '(') {
                    result = "(";
                    isReady = true;
                } else if (symbol == ')') {
                    if (isNegative == true)
                        isNegative = false;
                    else {
                        result = ")";
                        isReady = true;
                    }
                } else if (isOperator(symbol) && isNegative == false)
                    isReady = true;

                i++;
                prev = symbol;

            } else {
                isNegative = true;
                prev = symbol;
                symbol = next;
                if (i < size - 1)
                    next = msymbol.charAt(i + 1);
                i++;
            }

        }

        isReady = false;
        return result;
    }
}
