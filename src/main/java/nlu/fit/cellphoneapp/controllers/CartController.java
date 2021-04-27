package nlu.fit.cellphoneapp.controllers;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.others.Link;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
    public void ajaxCheckAddToCart(@RequestBody CartDTO infoCartItem,
                                   HttpServletResponse resp, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession(true);
        int productID = infoCartItem.getProductID();
        int amount = infoCartItem.getAmount();
        int userID = 19;
        //int userID = session.getAttribute("user").getId();
        infoCartItem.setUserID(userID);
        CartDTO c = null;
        //còn hàng kiểm tra xem sản phẩm đó đã có ở trong giỏ hàng hay chưa ?
        if (cartService.isInCart(productID, amount, userID)) {
            System.out.println("Sản phẩm " + productID + " đã có trong giỏ hàng!");
            resp.getWriter().print("error");
        } else {
        /*
            Không gặp bất kỳ lỗi nào thì thực hiện thêm vào csdl
            Tuy nhiên, Spring Data JPA làm việc với entity nên ta cần set lại giá trị cho entity, được thực hiện bởi cart service
         */
            c = cartService.insertIntoTable(infoCartItem);
            System.out.println("AddToCart " + c);
            resp.getWriter().print(
                    "<li class=\"cart-item\">" +
                            "<a href=\"#\" class=\"photo\"><img src=\"" + c.getProductImg() + "\" class=\"cart-thumb\"/></a>" +
                            "<h6><a href=\"#\">" + c.getProductName() + "</a></h6>" +
                            "<p>1x - <span class=\"product-price\">" + StringHelper.formatNumber((long) c.getProductPrice()) + "đ </span></p>" +
                    "</li>"
            );
        }
    }

    @GetMapping("/cart")
    public ModelAndView goToCartView(){
        ModelAndView mv = new ModelAndView("cart");
        int userID = 19;
        //lấy user từ session
        List<CartDTO> carts = cartService.getAllByUserID(userID);
        for (CartDTO c: carts) {
            System.out.println(c);
        }
        mv.addObject("carts", carts);
        return mv;
    }


}
