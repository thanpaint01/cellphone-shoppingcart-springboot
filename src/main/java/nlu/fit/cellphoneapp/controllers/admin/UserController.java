package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller("amdinUserManagement")
@RequestMapping("/admin/users-manage")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ModelAndView getAllUsers() {
        ModelAndView mv = new ModelAndView("admin/admin-user-management");
        mv.addObject("users", userService.getAllListUser());
        return mv;
    }
    @PutMapping("block/{id}")
    @ResponseBody
    public String blockUserAccount(@PathVariable("id") int id){
        User user = userService.findOneById(id);
        if(null == user) return "error";
        user.setActive(-1);
        userService.save(user);
        return "success";
    }

}
