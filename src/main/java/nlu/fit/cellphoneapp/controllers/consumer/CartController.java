package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.dto.CartDTO;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    HeadController headController;

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
        User user = (User) session.getAttribute(User.SESSION);
        int userID = 0;
        if (null != user){
            System.out.println(user);
            userID = user.getId();
            infoCartItem.setUserID(userID);


            CartDTO c = null;
            //còn hàng kiểm tra xem sản phẩm đó đã có ở trong giỏ hàng hay chưa ?
            if (cartService.isInCart(productID, amount, userID) && infoCartItem.getId() == 0) {
                System.out.println("Lỗi khi thêm sản phẩm đã có trong giỏ");
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
                                "<p>1x - <span class=\"\">" + StringHelper.formatNumber((long) c.getProductPrice()) + "đ </span></p>" +
                                "</li>"
                );
            }
        } else {
            resp.getWriter().print("warning");
        }
    }

    @GetMapping("/cart")
    public String goToCartView(HttpSession session, Model model) {
        User user = (User) session.getAttribute(User.SESSION);
        model.addAttribute("CONTENT_TITLE", "Quản lý giỏ hàng");
        int userID = 0;
        if (null != user) {
            userID = user.getId();
            //lấy user từ session
            List<CartDTO> carts = cartService.getAllByUserID(userID);
            for (CartDTO c : carts) {
                System.out.println(c);
            }
            headController.getCartOnHeader(session, model);
            return "/consumer/cart";
        }else{
            return "/consumer/cart-empty";
        }
    }

    @DeleteMapping("/cart")
    public @ResponseBody
    String ajaxDeleteCartItem(@RequestParam int id, HttpServletRequest req) {
        String rs = "";
        int userID = 0;
        //tránh trường hợp nhập trên url
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(User.SESSION);
        if (null != user) userID = user.getId();
        if (null != cartService.getOneCartItem(id) && userID != 0) {
            cartService.deleteOne(id);
            rs = "Xoá thành công!";
        }
        return rs;
    }


}
