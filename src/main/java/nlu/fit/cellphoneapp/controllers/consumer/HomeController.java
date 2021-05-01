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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
public class HomeController {


    @Autowired
    ICartService cartService;
    @Autowired
    HeadController headController;

    @GetMapping({"/", "/home"})
    public String getIndex(HttpSession session, Model model) {
        model.addAttribute("CONTENT_TITLE","Trang chủ");
        headController.getCartOnHeader(session, model);
        return "consumer/index";
    }
//    public void showCartBox(HttpSession session, Model model){
//        User user = (User) session.getAttribute(User.SESSION);
//        int userID = 0;
//        if(null!=user) userID = user.getId();
//        List<CartDTO> carts = cartService.getAllByUserID(userID);
//        model.addAttribute("cartItems", carts);
//    }
//
//
//    @GetMapping("/shop/{brand}")
//    public String getListProductByBrandID(HttpSession session,Model model, @RequestParam("brand") int brandID) {
//        headController.getCartOnHeader(session, model);
//        return "/consumer/shop";
//    }
//
//    public List<Product> getListProduct(Model model) {
//        List<Product> products = productService.findAllByActive(1);
//        model.addAttribute("products", products);
//        return products;
//    }
//
//    @GetMapping("/shop")
//    public String getShopPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "15") int limit, Model model, HttpSession session) {
//        model.addAttribute("CONTENT_TITLE","Danh sách sản phẩm");
//        getListProduct(model);
//        headController.getCartOnHeader(session, model);
//        //default page khi vào trang shop
//        return getByPaging(page, limit, model);
//    }
//
//
//    public String getByPaging(int page, int limit, Model model) {
//        System.out.println("page = " + page + ",limit = " + limit);
//        Page<Product> shopPage = productService.findPaginated(page, limit);
//        List<Product> products = shopPage.getContent();
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", shopPage.getTotalPages());
//        model.addAttribute("totalElements", shopPage.getTotalElements());
//        model.addAttribute("products", products);
//        return "/consumer/shop";
//    }





}
