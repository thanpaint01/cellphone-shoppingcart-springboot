package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.receiver.RegisterForm;
import nlu.fit.cellphoneapp.services.IUserService;
import nlu.fit.cellphoneapp.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;

    @ResponseBody
    public String login() {
        return "";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody RegisterForm form) {
        return "hello";
//        List<String> toCheck = new ArrayList<>();
//        toCheck.add(form.getFullname());
//        toCheck.add(form.getEmail());
//        toCheck.add(form.getPassword());
//        toCheck.add(form.getConfirmPassword());
//        userService.isEmailUnique(form.getEmail());
//        if (!StringHelper.isListNoValue(toCheck)) {
//            return "emptyfiled";
//        } else if (User.validGender(form.getGender())) {
//            return "emptyfiled";
//        } else if (!User.validName(form.getFullname())) {
//            return "errname";
//        } else if (!User.validPassword(form.getPassword())) {
//            return "errpass";
//        } else if (form.getPassword().equals(form.getConfirmPassword())) {
//            return "confirmpass";
//        } else if (!User.validEmail(form.getEmail())) {
//            return "errmail";
//        } else if (userService.isEmailUnique(form.getEmail())) {
//            return "errmailexist";
//        } else if ( )){
//            return "error";
//        } else{
//            // Email send
//            return "success";
//        }
    }

    @ResponseBody
    public String forgotPass() {
        return "";

    }
}
