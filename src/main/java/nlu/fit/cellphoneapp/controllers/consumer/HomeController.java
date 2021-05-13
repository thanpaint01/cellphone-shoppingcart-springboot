package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.DTOs.CartItemRequest;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashSet;

@Controller
public class HomeController {
    @Autowired
    ICartService cartService;

    @GetMapping({"/", "/home"})
    public String getIndex(Model model, HttpSession session) {
        if(User.checkUserSession(session)) session.setAttribute("cartSession", new HashSet<CartItemRequest>());
        model.addAttribute("CONTENT_TITLE","Trang chủ");
        return "consumer/index";
    }






}
