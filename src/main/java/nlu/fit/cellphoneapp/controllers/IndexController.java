package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.services.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    IBrandService brandService;

    @GetMapping({"/", "/home"})
    public String getIndex(Model model) {
        getListBrand(model);
        return "index";
    }

    @GetMapping("/home{brand}")
    public String getListProductByBrandID(Model model, @RequestParam("brand") int brandID) {
        getListBrand(model);
        return "index";
    }


    public List<Brand> getListBrand(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return brands;
    }
}
