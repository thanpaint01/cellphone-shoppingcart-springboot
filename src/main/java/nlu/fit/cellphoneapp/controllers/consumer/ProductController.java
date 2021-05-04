package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    IProductService productService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listProductPage
            (@RequestParam(value = "page", required = false, defaultValue = "1") int page
                    , @RequestParam(value = "brand", required = false, defaultValue = "-1") int brand
                    , @RequestParam(value = "ram", required = false, defaultValue = "-1") int ram
                    , @RequestParam(value = "rom", required = false, defaultValue = "-1") int rom
                    , @RequestParam(value = "pin", required = false, defaultValue = "-1") int pin
                    , @RequestParam(value = "name", required = false, defaultValue = "") String name, HttpSession session, Model md) {
        ModelAndView model = new ModelAndView("consumer/product-list");
        Page<Product> shopPage = productService.findPaginated(page, 15);
        List<Product> products = shopPage.getContent();
        model.addObject("currentPage", page);
        model.addObject("totalPages", shopPage.getTotalPages());
        model.addObject("totalRecords", shopPage.getTotalElements());
        model.addObject("products", products);
        model.addObject("CONTENT_TITLE", "DANH SÁCH SẢN PHẨM");

        return model;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView listProductPageSearch
            (@RequestParam(value = "page", required = false, defaultValue = "1") int page
                    , @RequestParam(value = "brand", required = false, defaultValue = "-1") int brand
                    , @RequestParam(value = "ram", required = false, defaultValue = "-1") int ram
                    , @RequestParam(value = "rom", required = false, defaultValue = "-1") int rom
                    , @RequestParam(value = "pin", required = false, defaultValue = "-1") int pin
                    , @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        ModelAndView model = new ModelAndView("consumer/product-list");
        model.addObject("listProduct", productService.findAll());
        return model;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView productDetailPage(@PathVariable(name = "id") int id) {
        Product product;
        if ((product = productService.findOneForConsumer(id)) != null) {
            ModelAndView model = new ModelAndView("consumer/product-detail");
            model.addObject("product", product);
            model.addObject("CONTENT_TITLE", product.getName());
            return model;
        } else {
            return new ModelAndView("redirect:/");
        }

    }
}
