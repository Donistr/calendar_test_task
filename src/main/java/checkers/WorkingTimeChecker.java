package checkers;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class WorkingTimeChecker {
    private final WeekendChecker weekendChecker = new WeekendChecker();

    private final ZoneId timeZone;
    private final int workStartHour;
    private final int workEndHour;

    public WorkingTimeChecker(ZoneId timeZone, int workStartHour, int workEndHour) {
        this.timeZone = timeZone;
        this.workStartHour = workStartHour;
        this.workEndHour = workEndHour;
    }

    public boolean isFreeTime(ZonedDateTime dateTime) throws IOException {
        ZonedDateTime currentTimeZoneDateTime = dateTime.withZoneSameInstant(timeZone);

        if (weekendChecker.isFreeDay(currentTimeZoneDateTime.toLocalDate())) {
            return true;
        }

        int hour = currentTimeZoneDateTime.getHour();
        return workStartHour > hour || hour >= workEndHour;
    }
}
