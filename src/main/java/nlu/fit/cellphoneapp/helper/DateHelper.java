package nlu.fit.cellphoneapp.helper;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static Date addMinute(int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }
}
