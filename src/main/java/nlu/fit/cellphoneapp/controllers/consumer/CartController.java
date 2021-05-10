package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    List<CartItem> cartItemsSession = new ArrayList<>();


    /*
     Phương thức thêm sản phẩm vào giỏ
     Tham số nhận vào bao gồm: productID, amount, userID
     Phương thức hiện đang dùng để checkAjax
  */
    @PostMapping("/add-to-cart")
    @ResponseBody
    public String ajaxCheckAddToCart(String id, int productID, int amount, HttpSession session, Model model) throws IOException {
        int cartItemIDUpdate = 0;
        CartItem cartItemSession = new CartItem();
        if (null != id) cartItemIDUpdate = Integer.parseInt(id);
        System.out.println("Add To Cart: " + productID);
        StringBuilder sb = new StringBuilder();
        User user = (User) session.getAttribute(User.SESSION);
        session.setAttribute("cartItemsSession", cartItemsSession);
        if (null != user) {
            System.out.println(user);
            if (user.checkCartItemExist(productID) && cartItemIDUpdate == 0) {
                System.out.println("CartItem is existed ! Check by User entity");
                return "error";
            } else {
                Product product = productService.findOneByID(productID);
                CartItem cartItem = new CartItem();
                if (cartItemIDUpdate != 0) cartItem.setId(cartItemIDUpdate);
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setAmount(amount);
                cartItem.setActive(1);
                cartItem.setTotalPrice(product.getPrice());
                cartItem = cartService.insertIntoTable(cartItem);
                if (cartItemIDUpdate == 0) {
                    user.getCartItems().add(cartItem);
                } else {
                    for (CartItem c : user.getCartItems()) {
                        if (c.getId() == cartItemIDUpdate) {
                            c.setAmount(amount);
                        }
                    }
                }
                sb.append(
                        "<li class=\"cart-item\">" +
                                "<a href=\"#\" class=\"photo\"><img src=\"" + cartItem.getProduct().getImg().getHost() + cartItem.getProduct().getImg().getRelativePath() + "\" class=\"cart-thumb\"/></a>" +
                                "<h6><a href=\"#\">" + cartItem.getProduct().getName() + "</a></h6>" +
                                "<p>1x - <span class=\"product-price li-price\">" + StringHelper.formatNumber((long) cartItem.getTotalPrice()) + " </span></p>" +
                                "</li>\n"
                );
            }
            return sb.toString();
        } else {
            if (cartItemsSession.size() > 0) {
                for (CartItem c : this.cartItemsSession) {
                    System.out.println("CART_ITEM IN SESSION = " + c.getProduct().getId());
                    if (c.getProduct().getId() == productID) {
                        System.out.println("ProductID = " + c.getProduct().getId() + " da co luu tren session");
                        return "error";
                    }
                }
            }
            System.out.println("cartItemsSession hien dang trong");
            cartItemSession.setAmount(amount);
            cartItemSession.setActive(1);
            cartItemSession.setTotalPrice(productService.findOneByID(productID).getPrice());
            cartItemSession.setProduct(productService.findOneByID(productID));
            cartItemsSession.add(cartItemSession);


            sb.append(
                    "<li class=\"cart-item\">" +
                            "<a href=\"#\" class=\"photo\"><img src=\"" + cartItemSession.getProduct().getImg().getHost() + cartItemSession.getProduct().getImg().getRelativePath() + "\" class=\"cart-thumb\"/></a>" +
                            "<h6><a href=\"#\">" + cartItemSession.getProduct().getName() + "</a></h6>" +
                            "<p>1x - <span class=\"product-price li-price\">" + StringHelper.formatNumber((long) cartItemSession.getTotalPrice()) + " </span></p>" +
                            "</li>\n"
            );
            return sb.toString();
        }
    }

    @GetMapping("/cart")
    public String goToCartView(HttpSession session, Model model) {
        User user = (User) session.getAttribute(User.SESSION);
        model.addAttribute("CONTENT_TITLE", "Quản lý giỏ hàng");
        int userID = 0;
        if (null != user) {
            userID = user.getId();
            List<CartItem> carts = cartService.getAllByUserID(userID);
            for (CartItem c : carts) {
                System.out.println(c);
            }
            if (carts.size() == 0) {
                return "/consumer/cart-empty";
            }
            return "/consumer/cart";
        } else {
            return "/consumer/cart-empty";
        }
    }

    @DeleteMapping("/cart")
    public @ResponseBody
    String ajaxDeleteCartItem(@RequestParam int id, HttpServletRequest req) {
        System.out.println("cartItem id=" + id);
        int userID = 0;
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(User.SESSION);
        if (null != user) userID = user.getId();
        if (null != cartService.getOneCartItem(id) && userID != 0) {
            for (CartItem c : user.getCartItems()) {
                System.out.println("CartItem of user = " + c.getId());
                if (c.getId() == id) {
                    System.out.println("CartItem will be removed by user = " + c.getId());
                    boolean removedCartSession = user.getCartItems().remove(c);
                    System.out.println("removedCartSession=" + removedCartSession);
                    cartService.deleteOne(id);
                    return "Xoá thành công!";
                }
            }
        }
        return "error";
    }


}
