package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.receiver.RegisterForm;
import nlu.fit.cellphoneapp.services.EmailSenderService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @ResponseBody
    public String login() {
        return "";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)

    public @ResponseBody
    String register(@RequestBody RegisterForm form
    ) {
        List<String> toCheck = new ArrayList<>();
        toCheck.add(form.newemail);
        toCheck.add(form.newfullname);
        toCheck.add(form.newpassword);
        toCheck.add(form.confirmpassword);
        if (!StringHelper.isListNoValue(toCheck)) {
            return "emptyfield";
        } else if (User.validGender(form.newgender)) {
            return "emptyfield";
        } else if (!User.validName(form.newfullname)) {
            return "errname";
        } else if (!User.validPassword(form.newpassword)) {
            return "errpass";
        } else if (!form.newpassword.equals(form.confirmpassword)) {
            return "confirmpass";
        } else if (!User.validEmail(form.newemail)) {
            return "errmail";
        } else if (userService.isEmailUnique(form.newemail)) {
            return "errmailexist";
        } else {
            User user = userService.findOneByEmail(form.newemail, User.ACTIVE.UNVERTIFIED.value());
            if (user == null)
                user = new User();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(form.newpassword);
            user.setEmail(form.newemail);
            user.setGender(User.toStringGender(Integer.valueOf(form.newgender)));
            user.setPassword(encodedPassword);
            user.setFullName(form.newfullname);
            user.setActive(User.ACTIVE.UNVERTIFIED.value());
            user.setRole(User.ACCESS.CONSUMEER.value());
            String token;
            while (userService.isTokenUnique((token = StringHelper.getAlphaNumericString(50)))) ;
            user.setKey(token);
            user.setExpiredKey(DateHelper.addMinute(15));
            if (!userService.save(user)) {
                return "error";
            } else {
                if (!emailSenderService.sendEmail(form.newemail, "hello", "HELLO")) {
                    return "errsendmail";
                }
                return "success";
            }
        }
    }

    @ResponseBody
    public String forgotPass() {
        return "";

    }


}
