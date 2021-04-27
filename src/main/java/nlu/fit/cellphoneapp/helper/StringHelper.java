package nlu.fit.cellphoneapp.helper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class StringHelper {
    public static boolean isNoValue(String input) {
        return input == null || input.isEmpty() || input.equals("") || input.trim().equals("")
                || input.trim().isEmpty();
    }

    public static boolean isListNoValue(List<String> inputs) {
        for (String input : inputs)
            if (isNoValue(input)) return false;
        return true;
    }

    public static String formatNumber(long input) {
        Locale indianLocale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getInstance(indianLocale);
        return numberFormat.format(input);
    }
}
