package nlu.fit.cellphoneapp.receiver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    String email;
    String fullname;
    int gender;
    String password;
    String confirmPassword;
}
