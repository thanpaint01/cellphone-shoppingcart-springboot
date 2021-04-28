package nlu.fit.cellphoneapp.helper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class StringHelper {
    public static boolean isNoValue(String input) {
        return input == null || input.isEmpty() || input.equals("") || input.trim().equals("")
                || input.trim().isEmpty();
    }

    public static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
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
