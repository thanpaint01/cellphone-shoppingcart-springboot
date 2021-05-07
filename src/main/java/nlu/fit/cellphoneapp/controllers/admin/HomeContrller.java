package nlu.fit.cellphoneapp.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/admin")
@Controller
public class HomeContrller {
    @RequestMapping(value = "")
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView("admin/admin-index");
        model.addObject("CONTENT_TITLE","Trang chủ");
        return model;
    }
}
