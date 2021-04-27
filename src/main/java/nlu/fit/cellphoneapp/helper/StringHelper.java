package nlu.fit.cellphoneapp.helper;

import java.util.List;

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
}
