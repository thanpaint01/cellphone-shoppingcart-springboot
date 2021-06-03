package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("amdinUserManagement")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private IUserService userService;


}
