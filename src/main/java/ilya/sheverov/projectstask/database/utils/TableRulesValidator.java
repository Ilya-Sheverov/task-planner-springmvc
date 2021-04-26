package ilya.sheverov.projectstask.database.utils;

import java.sql.Timestamp;

public class TableRulesValidator {
    /**
     * Проверяет, что число меньше или равно указанному числа.
     *
     * @param maxNumber     максимальное число.
     * @param numberToCheck число которое сравнивают с максимальным.
     * @return true, если numberToCheck <= maxNumber, если нет, то false.
     */
    public static final boolean compareWithTheMaximumNumber(Integer maxNumber, Integer numberToCheck) {
      return numberToCheck <= maxNumber;
    }

    /**
     * Проверяет, что длинна строки не превышает разрешенную.
     *
     * @param maxStringLength максимально развешенная длинна строки.
     * @param stringToCheck   строка которую проверяют.
     * @return true, если длинна строки меньше или равна допустимой, если нет, то false.
     */
    public static final boolean checkTheStringLength(Integer maxStringLength, String stringToCheck) {
      return stringToCheck.length() <= maxStringLength;
    }

    public static final boolean checkTheFormatTimestamp(Timestamp timestamp, String reg) {
      return timestamp.toString().matches(reg);
    }
}
