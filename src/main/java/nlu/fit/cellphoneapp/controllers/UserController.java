package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.receiver.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @ResponseBody
    public String login() {
        return "";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody RegisterForm form) {

        return "";
    }
    @ResponseBody
    public String forgotPass() {
        return "";

    }
}
