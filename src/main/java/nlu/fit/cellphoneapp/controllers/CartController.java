package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;

    /*
     Phương thức thêm sản phẩm vào giỏ
     Tham số nhận vào bao gồm: productID, amount, userID
     Phương thức hiện đang dùng để checkAjax
  */
    @PostMapping("/add-to-cart")
    public @ResponseBody
    boolean ajaxCheckAddToCart(@RequestBody CartDTO infoCartItem,
                               HttpServletResponse resp, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession(true);
        int productID = infoCartItem.getProductID();
        int amount = infoCartItem.getAmount();
        int userID = 19;
//        int userID = session.getAttribute("user").getId();
        resp.setContentType("text/plain");
        infoCartItem.setUserID(userID);
        //kiểm tra số lượng trong kho còn đủ sản phẩm hay không ?
        int amountProductRest = productService.findOneByID(productID).getAmount();
        if (amount > amountProductRest) return false; //không còn hàng để cung ứng

        //còn hàng kiểm tra xem sản phẩm đó đã có ở trong giỏ hàng hay chưa ?
        if (cartService.isInCart(productID, amount, userID)) {
            System.out.println("Sản phẩm " + productID + " đã có trong giỏ hàng!");
            return false;
        }
        /*
            Không gặp bất kỳ lỗi nào thì thực hiện thêm vào csdl
            Tuy nhiên, Spring Data JPA làm việc với entity nên ta cần set lại giá trị cho entity, được thực hiện bởi cart service
         */
        System.out.println("cartInfo:"+infoCartItem);
        CartDTO c = cartService.insertIntoTable(infoCartItem);
        System.out.println("AddToCart "+c);
        return true;
    }
}
