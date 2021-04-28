package nlu.fit.cellphoneapp.others;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private BcryptEncoder() {
    }
    public static String encode(String input) {
        return encoder.encode(input);
    }
    public static boolean matches(String firstInput, String secInput) {
        return encoder.matches(firstInput, secInput);
    }
}
