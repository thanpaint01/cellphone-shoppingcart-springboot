package nlu.fit.cellphoneapp.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class DateHelper {
    public static int monthsBetween(Date a, Date b) {
        Calendar cal = Calendar.getInstance();
        if (a.before(b)) {
            cal.setTime(a);
        } else {
            cal.setTime(b);
            b = a;
        }
        int c = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.MONTH, 1);
            c++;
        }
        return c - 1;
    }

    public static List<String> getMonthsBetween(Date from, Date to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

        LocalDate start = convertToLocalDate(from);
        LocalDate end = convertToLocalDate(to);
        List<String> months = new ArrayList<>();
        LocalDate date = start;
        if (date.getDayOfMonth() == 1) {
            date = date.minusDays(1);
        }
        while (date.isBefore(end)) {
            if (date.plusMonths(1).with(lastDayOfMonth()).isAfter(end)) {
                break;
            }
            date = date.plusMonths(1).withDayOfMonth(1);
            months.add(date.format(formatter));
        }
        return months;
    }


    public static Date addMinute(int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static Date getMonthAgo(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -month);
        return cal.getTime();
    }

    public static List<Date> getHalfYearAgo() {
        List<Date> dates = new ArrayList<>();
        for (int i = 6; i >= 1; i--) {
            dates.add(getMonthAgo(i));
        }
        return dates;
    }

    public static int getMonth(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
    }

    public static int getYear(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
