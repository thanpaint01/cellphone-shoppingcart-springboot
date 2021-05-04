package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    HeadController headController;

    @GetMapping("/checkout")
    public String getOrderPage(Model model, HttpSession session){
        headController.getCartOnHeader(session, model);
        model.addAttribute("user", session.getAttribute(User.SESSION));

        if(null == session.getAttribute(User.SESSION)) return "consumer/cart-empty";
        return "consumer/checkout";
    }
}
