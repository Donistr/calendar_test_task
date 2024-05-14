package checkers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeekendChecker {
    private final Map<Integer, Map<Month, Set<Integer>>> weekends = new HashMap<>();

    private static final String ADD_YEAR_URL = "https://www.consultant.ru/law/ref/calendar/proizvodstvennye/";
    private static final Map<String, Month> MONTHS_MAPPING = new HashMap<>();
    static {
        MONTHS_MAPPING.put("Январь", Month.JANUARY);
        MONTHS_MAPPING.put("Февраль", Month.FEBRUARY);
        MONTHS_MAPPING.put("Март", Month.MARCH);
        MONTHS_MAPPING.put("Апрель", Month.APRIL);
        MONTHS_MAPPING.put("Май", Month.MAY);
        MONTHS_MAPPING.put("Июнь", Month.JUNE);
        MONTHS_MAPPING.put("Июль", Month.JULY);
        MONTHS_MAPPING.put("Август", Month.AUGUST);
        MONTHS_MAPPING.put("Сентябрь", Month.SEPTEMBER);
        MONTHS_MAPPING.put("Октябрь", Month.OCTOBER);
        MONTHS_MAPPING.put("Ноябрь", Month.NOVEMBER);
        MONTHS_MAPPING.put("Декабрь", Month.DECEMBER);
    }

    public boolean isFreeDay(LocalDate date) throws IOException {
        if (shouldAddYear(date.getYear())) {
            addYear(date.getYear());
        }

        return isWeekend(date);
    }

    private boolean shouldAddYear(int year) {
        return !weekends.containsKey(year);
    }

    private boolean isWeekend(LocalDate date) {
        return weekends.get(date.getYear())
                .get(date.getMonth())
                .contains(date.getDayOfMonth());
    }

    private void addYear(int year) throws IOException {
        Document document = Jsoup.connect(ADD_YEAR_URL + year + "/").get();

        Elements months = document.getElementsByClass("cal");

        for (Element month : months) {
            String monthName = month
                    .getElementsByTag("thead").first()
                    .getElementsByTag("tr").first()
                    .getElementsByTag("th").first().text();
            Month currentMonth = MONTHS_MAPPING.get(monthName);

            Elements rows = month
                    .getElementsByTag("tbody").first()
                    .getElementsByTag("tr");

            for (Element row : rows) {
                Elements holidays = row.getElementsByClass("holiday weekend");
                Elements weekends = row.getElementsByClass("weekend");

                for (Element holiday : holidays) {
                    int currentDay = Integer.parseInt(holiday.text());
                    addWeekend(year, currentMonth, currentDay);
                }
                for (Element weekend : weekends) {
                    int currentDay = Integer.parseInt(weekend.text());
                    addWeekend(year, currentMonth, currentDay);
                }
            }
        }
    }

    private void addWeekend(int year, Month month, int day) {
        if (shouldAddYear(year)) {
            weekends.put(year, new HashMap<>());
        }

        if (!weekends.get(year).containsKey(month)) {
            weekends.get(year).put(month, new HashSet<>());
        }

        weekends.get(year).get(month).add(day);
    }
}
