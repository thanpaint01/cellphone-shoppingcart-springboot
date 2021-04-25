package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
public class HomeController {

    @Autowired
    IBrandService brandService;
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;

    @GetMapping({"/", "/home"})
    public String getIndex(Model model) {
        getListBrand(model);
        model.addAttribute("CONTENT_TITLE","Trang chủ");
        return "index";
    }

    //shop là trang danh sách sản phẩm, phương thức lấy danh sách sản phẩm theo id hãng
    @GetMapping("/shop{brand}")
    public String getListProductByBrandID(Model model, @RequestParam("brand") int brandID) {
        getListBrand(model);
        return "shop";
    }


    public List<Product> getListProduct(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return products;
    }

    @GetMapping("/shop")
    public String getShopPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "15") int limit, Model model) {
        getListBrand(model);
        getListProduct(model);
        //default page khi vào trang shop
        return getByPaging(page, limit, model);
    }
    public List<Brand> getListBrand(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return brands;
    }

    public String getByPaging(int page, int limit, Model model) {
        System.out.println("page = " + page + ",limit = " + limit);
        Page<Product> shopPage = productService.findPaginated(page, limit);
        List<Product> products = shopPage.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shopPage.getTotalPages());
        model.addAttribute("totalElements", shopPage.getTotalElements());
        model.addAttribute("products", products);
        return "shop";
    }

    /*
        Phương thức thêm sản phẩm vào giỏ
        Tham số nhận vào bao gồm: productID, amount, userID
        Phương thức hiện đang dùng để checkAjax
     */
    @PostMapping("/add-to-cart")
    public @ResponseBody
    boolean ajaxCheckAddToCart(@RequestBody CartDTO infoCartItem,
                               HttpServletResponse resp) throws IOException {
        int productID = infoCartItem.getProductID();
        int amount = infoCartItem.getAmount();
        int userID = infoCartItem.getUserID();
        resp.setContentType("text/plain");

        //kiểm tra số lượng trong kho còn đủ sản phẩm hay không ?
        int amountProductRest = productService.findOneByID(productID).getAmount();
        if (amount > amountProductRest) return false; //không còn hàng để cung ứng

        //còn hàng kiểm tra xem sản phẩm đó đã có ở trong giỏ hàng hay chưa ?
        if (!cartService.isInCart(productID, amount, userID)) {
            System.out.println("Sản phẩm " + productID + " đã có trong giỏ hàng!");
            return false;
        }
        /*
            Không gặp bất kỳ lỗi nào thì thực hiện thêm vào csdl
            Tuy nhiên, Spring Data JPA làm việc với entity nên ta cần set lại giá trị cho entity, được thực hiện bởi cart service
         */
        cartService.insertIntoTable(infoCartItem);
        return true;
    }

}
