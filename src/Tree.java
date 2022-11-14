import java.util.Scanner;

/**
 * Класс Дерево для хранения выражения и его значения
 * 
 * @author Khokhlov Dmitrii
 * @version 3.2
 */
class Tree {
    private Node root; // ссылка на корень дерева
    private double result; // результат вычисления выражения всего поддерева

    // Конструктор
    Tree() {
        root = null;
        result = 0;
    }

    /** @return root - возвращает корень дерева */
    public final Node get_root() {
        return root;
    }

    /** @return result - возвращает резултат вычисления дерева */
    public final double get_result() {
        return result;
    }

    /**
     * Основная функция обработки строки
     * Начинает рекурсивную разработку строки
     * 
     * @see Tree#recursion(String, Scanner)
     *      После заполнения вызывает функцию печати и выводит результат
     * @see Node#print(Node)
     */
    public final void task(String value) {
        Scanner in = new Scanner(System.in);
        recursion(value, in);

        System.out.println("Expression: ");

        if (root.isFiling() == false)
            System.out.println(result);
        else
            root.print(root);
            
        System.out.println("Result: ");
        System.out.println(result);
        in.close();
    }

    /**
     * Рекурсивная обработка строки
     * 
     * @see Tree#filling(String, Scanner)
     * @param result - результат вычисления вражения в поддереве
     * @see Tree#estimation()
     */
    public final void recursion(String value, Scanner in) {
        if (filling(value, in)) {
            if (result == 0)
                result = estimation();
        } else
            System.out.println("Problem with filing!");

    }

    /**
     * Заполнение поддерева
     * 
     * @see cutter#cutter(String)
     * @param comp - экземпляр класса cutter
     * @see cutter#correct()
     * @param res - проверка корректности введенных данных
     * @see Node#Node()
     *      Условие проверяет строка представляет собой выражение или просто число
     * @see cutter#isItExpression()
     * @see cutter#isDigit(String)
     *      Если выражение - заполняем ветки с выражением
     * @see Node#filingBranch(Node, cutter, Scanner)
     *      Если просто число - заполняем результат поддерева этим числом
     */
    public final boolean filling(String value, Scanner in) {
        boolean res = false;
        cutter comp = new cutter(value);

        if (comp.correct() == true) {
            res = true;

            root = new Node();

            if (comp.isItExpression() == true)
                root = root.filingBranch(root, comp, in);
            else
                result = Double.parseDouble(comp.comparator());
            // root.print(root);
        } else
            System.out.println("Error!");

        return res;
    }

    /**
     * Возвращает результат вычисления выражения
     * 
     * @see Node#calculate(Node, double)
     */
    public final double estimation() {
        return root.calculate(root, 0);
    }
}

class Node {
    private Tree subTree; // возможное поддерево
    private Node left; // левая ветвь
    private Node right; // правая ветвь
    private double mvalue; // численное значение
    private String mterm; // Операнд
    private String mFormula; // Формула
    private boolean isFiling; // заполненость уровня

    /** Конструктор без параметров */
    Node() {
        left = null;
        right = null;
        subTree = null;
        mvalue = -1;
        mterm = "}";
        mFormula = ":";
        isFiling = false;
    }

    /** Конструктор с параметром */
    Node(String value) {
        left = null;
        right = null;
        subTree = null;

        if (Character.isDigit(value.charAt(0)))
            mvalue = Double.parseDouble(value);
        else
            mterm = value;

        isFiling = true;
    }

    /**
     * Установка в качестве значения результата вычисление поддерева
     * 
     * @see Tree#get_result()
     */
    public void setmValue() {
        mvalue = subTree.get_result();
    }

    /**
     * Установка значения произвольным числом
     * 
     * @param value - произвольное число
     */
    public void setmValue(double value) {
        mvalue = value;
    }

    /**
     * Установка знака выражения
     * 
     * @param term - знак выражения
     */
    public void setmterm(String term) {
        mterm = term;
    }

    /**
     * возвращает заполненость уровня
     */
    public boolean isFiling() {
        return isFiling;
    }

    /**
     * Создание левой ветви
     * 
     * @param root - корень дерева
     * @see Node#Node()
     */
    public void createLeftBranch(Node root) {
        if (root.left == null)
            root.left = new Node();
    }

    /**
     * Создание правой ветви
     * 
     * @param root - корень дерева
     * @see Node#Node()
     */
    public void createRightBranch(Node root) {
        if (root.right == null)
            root.right = new Node();
    }

    public final Node filingBranch(Node root, cutter comp, Scanner in) {
        String part;

        while (root.isFiling == false) {
            part = comp.comparator();

            if (part.charAt(0) != ')') {
                if (part.charAt(0) == '(') { // встречаем открывающую скобку - спускаемся вниз
                    if (root.left == null) { // проверка на какую ветку спускаться
                        root.createLeftBranch(root);
                        root.left = filingBranch(root.left, comp, in);
                    } else {
                        root.createRightBranch(root);
                        root.right = filingBranch(root.right, comp, in);
                        root.isFiling = true;
                    }
                } else if (comp.isDigit(part)) { // Заполнение цифрой
                    if (root.left == null) {
                        root.createLeftBranch(root);
                        root.left.mvalue = Double.parseDouble(part);
                        root.left.isFiling = true;
                    } else if (root.right == null) {
                        root.createRightBranch(root);
                        root.right.mvalue = Double.parseDouble(part);
                        root.right.isFiling = true;
                        root.isFiling = true;
                    }
                } else if (comp.isOperator(part.charAt(0))) // заполнение значение оператора
                    root.mterm = part;
                else {
                    Formula test = new Formula(part);

                    if (test.isItFormula() == true) {
                        String subString = comp.subStr(); // подстрока внутри формулы

                        if (root.left == null) {
                            root.createLeftBranch(root);
                            root.left.mFormula = part;
                            root.left.subTree = new Tree();
                            root.left.subTree.recursion(subString, in);
                            test.set_meaning(root.left.subTree.get_result());
                            root.left.mvalue = test.get_est();
                            root.left.isFiling = true;
                        } else if (root.right == null) {
                            root.createRightBranch(root);
                            root.right.mFormula = part;
                            root.right.subTree = new Tree();
                            root.right.subTree.recursion(subString, in);
                            test.set_meaning(root.right.subTree.get_result());
                            root.right.mvalue = test.get_est();
                            root.isFiling = true;
                        }
                    } else { // обыкновенная переменная
                        System.out.print("Enter meaning of " + part + ":");
                        String exp = in.nextLine();

                        // записано выражение в переменной помещаем в отдельное дерево
                        if (root.left == null) {
                            root.createLeftBranch(root);
                            root.left.subTree = new Tree();
                            root.left.subTree.recursion(exp, in);
                            root.left.setmValue();
                            root.left.isFiling = true;
                        } else if (root.right == null) {
                            root.createRightBranch(root);
                            root.right.subTree = new Tree();
                            root.right.subTree.recursion(exp, in);
                            root.right.setmValue();
                            root.right.isFiling = true;
                            root.isFiling = true;
                        }
                    }
                }
            }
        }

        return root;
    }

    /**
     * Функция рекурсивного обзода дерева и подсчета выражения
     * 
     * @param root   - корень дерева
     * @param result - итоговое значение выражения
     * @return result
     */
    public final double calculate(Node root, double result) {
        if (root.left == null && root.right == null)
            result = root.mvalue;
        else {
            result = calculate(root.left, result);
            switch (root.mterm) {
                case "+": {
                    result = result + calculate(root.right, result);
                }
                    break;
                case "-": {
                    result = result - calculate(root.right, result);
                }
                    break;
                case "*": {
                    result = result * calculate(root.right, result);
                }
                    break;
                case "/": {
                    result = result / calculate(root.right, result);
                }
                    break;
                case "^": {
                    result = Math.pow(result, calculate(root.right, result));
                }
                    break;
            }
        }

        return result;
    }

    /**
     * Функция рекурсивной печати
     * 
     * @see Tree#get_root()
     * @see Node#isFiling()
     * @param root - корень дерева
     */
    void print(Node root) {
        if (root.left == null && root.left == null)
            if (root.subTree == null || root.subTree.get_root().isFiling() == false)
                System.out.print(Double.toString(root.mvalue));
            else {
                if (root.mFormula != ":")
                    System.out.print(root.mFormula);
                System.out.print("(");
                print(root.subTree.get_root());
                System.out.print(")");
            }
        else {
            System.out.print("(");
            print(root.left);
            System.out.print(root.mterm);
            print(root.right);
            System.out.print(")");
        }
    }
}