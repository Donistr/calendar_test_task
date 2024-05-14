package tasks;

import checkers.WorkingTimeChecker;

import java.time.*;
import java.util.Scanner;

public class Task2 {
    static final ZoneId MOSCOW_ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final int WORK_START_HOUR = 9;
    private static final int WORK_END_HOUR = 18;

    private static final String GMT_STRING = "GMT";

    private static final int YEAR = 2024;
    private static final Month MONTH = Month.MAY;

    private static ZoneId getZoneId(int timezone) {
        if (timezone >= 0) {
            return ZoneId.of(GMT_STRING + "+" + timezone);
        }

        return ZoneId.of(GMT_STRING + timezone);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input day: ");
        int day = scanner.nextInt();
        System.out.println("input GMT timezone(+ or - number): ");
        int timezone = scanner.nextInt();
        System.out.println("input hour: ");
        int hour = scanner.nextInt();
        System.out.println("input minute: ");
        int minute = scanner.nextInt();
        System.out.println("input second: ");
        int second = scanner.nextInt();

        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(YEAR, MONTH, day, hour, minute, second), getZoneId(timezone));

        WorkingTimeChecker workingTimeChecker = new WorkingTimeChecker(MOSCOW_ZONE_ID, WORK_START_HOUR, WORK_END_HOUR);
        if (workingTimeChecker.isFreeTime(dateTime)) {
            System.out.println("free time");
        } else {
            System.out.println("work time");
        }
    }
}
