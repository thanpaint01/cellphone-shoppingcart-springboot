package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
@Controller
public class HomeController {
    @Autowired
    ICartService cartService;

    @GetMapping({"/", "/home"})
    public String getIndex( Model model) {
        model.addAttribute("CONTENT_TITLE","Trang chủ");
        return "consumer/index";
    }






}
