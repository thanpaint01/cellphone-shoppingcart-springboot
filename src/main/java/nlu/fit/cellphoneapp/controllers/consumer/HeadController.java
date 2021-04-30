package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HeadController {

    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;

    public void getCartOnHeader(HttpSession session, Model model) {
        User user;
        int sumAmount = 0;
        int totalPriceForBox = 0;
        List<CartDTO> cartDTOs = new ArrayList();
        if (null != (user = (User) session.getAttribute(User.SESSION))) {
            System.out.println("userID=" + user.getId());
            cartDTOs = cartService.getAllByUserID(user.getId());
            System.out.println("lenOfArr="+cartDTOs.size());
            for (CartDTO c : cartDTOs) {
                sumAmount += c.getAmount();
                totalPriceForBox += c.getTotalPrice();
            }
        }
        model.addAttribute("sumAmount", sumAmount);
        model.addAttribute("totalPriceForBox", totalPriceForBox);
        model.addAttribute("cartItems", cartDTOs);
    }


}
