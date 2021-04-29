package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.others.Link;
import nlu.fit.cellphoneapp.receiver.RegisterForm;
import nlu.fit.cellphoneapp.services.EmailSenderService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Param("email") String email, @Param("password") String password, HttpSession session) {
        User user;
        if (StringHelper.isNoValue(email) || StringHelper.isNoValue(password))
            return "emptyfield";
        else if ((user = userService.findOneByLogin(email, password)) != null) {
            session.setAttribute(User.SESSION, user);
            return "success";
        } else
            return "failed";

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String register(@RequestBody RegisterForm form, HttpServletRequest request
    ) {
        List<String> toCheck = new ArrayList<>();
        toCheck.add(form.newemail);
        toCheck.add(form.newfullname);
        toCheck.add(form.newpassword);
        toCheck.add(form.confirmpassword);
        if (StringHelper.isNoValue(toCheck)) {
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
            user.setEmail(form.newemail);
            user.setGender(User.toStringGender(Integer.valueOf(form.newgender)));
            user.setPassword(BcryptEncoder.encode(form.newpassword));
            user.setFullName(form.newfullname);
            user.setActive(User.ACTIVE.UNVERTIFIED.value());
            user.setRole(User.ROLE.CONSUMEER.value());
            String token;
            while (userService.isTokenUnique((token = StringHelper.getAlphaNumericString(50)))) ;
            user.setKey(token);
            user.setExpiredKey(DateHelper.addMinute(15));
            if (!userService.save(user)) {
                return "error";
            } else {
                if (!emailSenderService.sendEmailVertification(form.newemail, form.newfullname, Link.createAbsolutePath(request, "/user/email/verify/" + token))) {
                    return "errsendmail";
                }
                return "success";
            }
        }
    }

    @RequestMapping(value = "/email/verify/{token}")
    public ModelAndView vertificateEmail(@PathVariable("token") String token) {
        User u;
        if ((u = userService.vertifyToken(token)) == null)
            return new ModelAndView("redirect:/");
        else {
            u.setKey(null);
            u.setExpiredKey(null);
            u.setActive(User.ACTIVE.ACTIVE.value());
            userService.save(u);
            return new ModelAndView("email-vertification");
        }
    }


    @RequestMapping(value = "/forgot-pass", method = RequestMethod.POST)
    public @ResponseBody
    String forgotPass(@Param("email") String email) {
        User user;
        if (StringHelper.isNoValue(email)) return "emptyfield";
        else if (!userService.isEmailUnique(email))
            return "notexistemail";
        else if ((user = userService.findOneByEmail(email, User.ACTIVE.ACTIVE.value())) == null) {
            return "unactive";
        } else {
            String token;
            while (userService.isTokenUnique((token = StringHelper.getAlphaNumericString(10)))) ;
            user.setKey(token);
            user.setExpiredKey(DateHelper.addMinute(15));
            userService.save(user);
            if (emailSenderService.sendEmailResetPassword(email, user.getFullName(), token))
                return "success";
            else
                return "failed";
        }
    }

    @RequestMapping(value = "/reset-pass", method = RequestMethod.POST)
    @ResponseBody
    public String resetPass(@Param("resetcode") String resetCode, @Param("newpass") String newpass, @Param("confirmpass") String confirmpass) {
        User user;
        List<String> toCheck = new ArrayList<>();
        toCheck.add(resetCode);
        toCheck.add(newpass);
        toCheck.add(confirmpass);
        if (StringHelper.isNoValue(toCheck)) {
            return "emptyfield";
        } else if (!User.validPassword(newpass))
            return "validpass";
        else if (newpass.equals(confirmpass))
            return "notequate";
        else if ((user = userService.vertifyToken(resetCode)) == null) {
            return "failcode";
        } else {
            user.setPassword(BcryptEncoder.encode(newpass));
            user.setKey(null);
            user.setExpiredKey(null);
            userService.save(user);
            return "success";
        }
    }


}
