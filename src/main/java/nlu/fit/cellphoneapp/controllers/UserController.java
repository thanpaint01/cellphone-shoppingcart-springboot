package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.dto.UserDTO;
import nlu.fit.cellphoneapp.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @ResponseBody
    public String login() {
        return "";
    }

    @ResponseBody
    public String register() {
        return "";
    }

    @ResponseBody
    public String forgotPass() {
        return "";

    }

    @PostMapping("/user/new")
    public User createUser(UserDTO userDTO) {
        User user = new User();
        return user;
    }
}
