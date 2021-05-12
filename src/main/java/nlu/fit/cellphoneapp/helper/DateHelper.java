package nlu.fit.cellphoneapp.helper;

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
        cal.add(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }
}
