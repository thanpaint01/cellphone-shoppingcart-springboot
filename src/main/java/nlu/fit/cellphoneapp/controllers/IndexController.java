package nlu.fit.cellphoneapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/trang-chu"})
    public String goToIndexPage(){return "index";}
}
