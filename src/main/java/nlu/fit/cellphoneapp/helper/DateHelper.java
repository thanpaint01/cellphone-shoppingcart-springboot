package nlu.fit.cellphoneapp.helper;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelper {
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
}
