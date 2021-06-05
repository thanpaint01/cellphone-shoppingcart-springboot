package nlu.fit.cellphoneapp.validator;

import nlu.fit.cellphoneapp.receiver.UpdateInfoForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UpdateInfoForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
