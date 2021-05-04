package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @GetMapping("/checkout")
    public String goToCheckoutPage(@ModelAttribute("userDelivery") User user, Model model, HttpSession session) {
        if (null == session.getAttribute(User.SESSION)) return "consumer/cart-empty";
        return "consumer/checkout";
    }

    @GetMapping("/order")
    public String goToOrderPage(HttpSession session, Model model) {
        User userDelivery;
        if(null != (userDelivery = (User) model.getAttribute("userDelivery"))){
            //            //checkAttr

        }
        if (null == session.getAttribute(User.SESSION)) return "redirect:/home";
            return "order";
    }
}
