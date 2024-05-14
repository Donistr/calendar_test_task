package tasks;

import checkers.WeekendChecker;

import java.time.LocalDate;
import java.time.Month;

public class Task1 {
    private static final int YEAR = 2024;
    private static final Month MONTH = Month.MAY;

    public static void main(String[] args) throws Exception {
        WeekendChecker weekendChecker = new WeekendChecker();

        for (int i = 1; i <= 31; i++) {
            System.out.print(i + " - ");
            if (weekendChecker.isFreeDay(LocalDate.of(YEAR, MONTH, i))) {
                System.out.println("not working day");
            } else {
                System.out.println("working day");
            }
        }
    }
}
