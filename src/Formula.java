/**
 * Класс для хравнения и вычисления формул
 * 
 * @author Khokhlov Dmitrii
 * @version 2.1
 */
public class Formula {
    private String mValue; // название формулы
    private double mmeaning; // значение вычесленной формулы
    private int mnumber; // номер формулы в таблице

    /**
     * Конструктор
     * 
     * @param value - входная строка
     */
    Formula(String value) {
        mValue = value;
        mmeaning = 0;
        mnumber = -1;
    }

    /**
     * Установка значения из вне
     * 
     * @param meaning - некоторе значение
     */
    public void set_meaning(double meaning) {
        mmeaning = meaning;
    }

    /**
     * Проверка входящей строки на формулу
     * 
     * @return true - еси формула, false - иначе
     */
    public boolean isItFormula() {
        boolean result = false;

        switch (mValue) {
            case "abs":
                mnumber = 0;
                break;
            case "sin":
                mnumber = 1;
                break;
            case "cos":
                mnumber = 2;
                break;
            case "tan":
                mnumber = 3;
                break;
            case "asin":
                mnumber = 4;
                break;
            case "acos":
                mnumber = 5;
                break;
            case "atan":
                mnumber = 6;
                break;
        }

        if (mnumber != -1)
            result = true;
        return result;
    }

    /**
     * Посчет формулы соответствеющей строке
     * 
     * @return значение выражения
     */
    public double get_est() {
        double result = 0;

        switch (mnumber) {
            case 0:
                result = Math.abs(mmeaning);
                break;
            case 1:
                result = Math.sin(mmeaning);
                break;
            case 2:
                result = Math.cos(mmeaning);
                break;
            case 3:
                result = Math.tan(mmeaning);
                break;
            case 4:
                result = Math.asin(mmeaning);
                break;
            case 5:
                result = Math.acos(mmeaning);
                break;
            case 6:
                result = Math.atan(mmeaning);
                break;
        }

        return result;
    }
}
