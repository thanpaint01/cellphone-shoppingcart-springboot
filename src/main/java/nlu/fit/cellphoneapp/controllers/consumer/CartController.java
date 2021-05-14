package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.DTOs.CartItemRequest;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    List<CartItem> cartItemsSession = new ArrayList<>();
    Set<CartItem> cartItemsSessionV2 = new HashSet<>();
    Set<CartItemRequest> cartSession = new HashSet<>();


//    @GetMapping("/user/cart/all")
//    public @ResponseBody
//    Collection<CartItem> getAllByUser(int userID) {
//        return cartService.getAllByUserID(userID);
//    }

    @PostMapping(value = "/add-to-cart", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Collection<CartItemRequest> addToCart(@RequestBody CartItemRequest cartItem, @RequestHeader(value = "action") String action, HttpSession session, HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");

        System.out.println("cartRequest ID ="+cartItem.getId());
        //create a new cartItem entity from cartItemRequest
        CartItem c = new CartItem();
        c.setId(cartItem.getId());
        Product item = productService.findOneByID(cartItem.getProductID());
        c.setProduct(item);
        c.setAmount(cartItem.getAmount());
        c.setTotalPrice(item.getPrice());
        c.setActive(cartItem.getActive());

        cartSession = (Set<CartItemRequest>) revertToCartItemResponse(updateCart(c, action, session));
        //add this session for user when login (append cart session to list cart user)
        session.setAttribute("cartSession", cartItemsSessionV2);
        return cartSession;
    }

    void print() {
        for (CartItemRequest c : cartSession) {
            System.out.println("product = " + c.getProductID() + ", amount =" + c.getAmount());
        }
    }

    public Collection<CartItem> updateCart(CartItem cartItem, String action, HttpSession session) {
        Collection<CartItem> cartItems = new HashSet<>();
        if (checkUserSession(session)) {
            User user = (User) session.getAttribute(User.SESSION);
            cartItems = user.getCartItems();
        } else {
            cartItems = cartItemsSessionV2;
        }
        if (action.equals("add")) {
            //click add at product list page again, amount++
            for (CartItem c : cartItems) {
                System.out.println("double clicked out side list product page!");
                if (c.getProduct().getId() == cartItem.getProduct().getId()) {
                    c.setAmount(c.getAmount() + 1);
                    c.updateTotalPrice();
                    //cart user update inside db
                    if (User.checkUserSession(session) == true) {
                        User user = (User) session.getAttribute(User.SESSION);
                        cartItem = cartService.getOneByUserAndProduct(user.getId(), cartItem.getProduct().getId());
                        cartItem.setAmount(c.getAmount());
                        cartItem.setTotalPrice(c.getTotalPrice());
                        cartItem.setUser((User) session.getAttribute(User.SESSION));
                        cartService.insertIntoTable(cartItem);
                    }
                    return cartItems;
                }
            }
            cartItems.add(cartItem);
            if (User.checkUserSession(session) == true) {
                cartItem.setUser((User) session.getAttribute(User.SESSION));
                cartService.insertIntoTable(cartItem);
            }
        } else if (action.equals("update")) {
            for (CartItem c : cartItems) {
                if (c.getProduct().getId() == cartItem.getProduct().getId()) {
                    c.setAmount(cartItem.getAmount());
                    c.updateTotalPrice();
                    if (User.checkUserSession(session) == true) cartService.insertIntoTable(c);
                    break;
                }
            }
        }
        return cartItems;
    }

    @DeleteMapping("/cart/delete")
    @ResponseBody
    public String ajaxDeleteCartItem(@RequestParam int productID, HttpSession session) {
        Collection<CartItemRequest> listCart = deleteCart(productID, session);
        if (listCart.size() == 0) return "/cart"; //load again with empty cart
        return "success";
    }

    public Collection<CartItemRequest> deleteCart(int productID, HttpSession session) {
        if (User.checkUserSession(session) == true) {
            //remove on cart user
            System.out.println("delete on cart user " + productID);
            User user = (User) session.getAttribute(User.SESSION);
            for (CartItem c : user.getCartItems()) {
                if (c.getProduct().getId() == productID) {
                    System.out.println("Da xoa thanh cong " + c.getId());
                    user.getCartItems().remove(c);
                    cartService.deleteOne(c.getId());
                    deleteOneCartSessionV2(productID);
                    break;
                }
            }
            return revertToCartItemResponse(user.getCartItems());
        } else {
            //remove on cart session
            for (CartItemRequest c : cartSession) {
                if (c.getProductID() == productID) {
                    System.out.println("remove on session is success=" + productID);
                    cartSession.remove(c);
                    deleteOneCartSessionV2(productID);
                    break;
                }
            }
            System.out.println("after remove");
            print();
            System.out.println("cartItem v2");
            for (CartItem c : cartItemsSessionV2) {
                System.out.println("con lai = " + c.getProduct().getId());
            }
            return revertToCartItemResponse(cartItemsSessionV2);
        }
    }

    //delete on session
    public boolean deleteOneCartSessionV2(int productID) {
        for (CartItem c : cartItemsSessionV2) {
            if (c.getProduct().getId() == productID) {
                cartItemsSessionV2.remove(c);
                return true;
            }
        }
        return false;
    }

    //check user session
    public boolean checkUserSession(HttpSession session) {
        if (null == session.getAttribute(User.SESSION)) return false;
        return true;
    }

    public Collection<CartItemRequest> getCart(HttpSession session) {
        if (null != session.getAttribute("cartSession")) {
            return (Collection<CartItemRequest>) session.getAttribute("cartSession");
        } else {
            return revertToCartItemResponse(((User) session.getAttribute(User.SESSION)).getCartItems());
        }
    }

    @GetMapping("/cart/all")
    @ResponseBody
    public Collection<CartItemRequest> loadCart(HttpSession session, HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
        System.out.println("into load cart");
        if (checkUserSession(session) == true) {
            User user = (User) session.getAttribute(User.SESSION);
            return revertToCartItemResponse(user.getCartItems());
        } else {
            return cartSession;
        }
    }

    public Collection<CartItemRequest> revertToCartItemResponse(Collection<CartItem> cartItems) {
        Collection<CartItemRequest> cartItemResponse = new HashSet<>();
        for (CartItem i : cartItems) {
            CartItemRequest ci = new CartItemRequest();
            ci.setActive(i.getActive());
            ci.setAmount(i.getAmount());
            ci.setProductID(i.getProduct().getId());
            ci.setProductImg(i.getProduct().getImg().getHost() + i.getProduct().getImg().getRelativePath());
            ci.setProductName(i.getProduct().getName());
            ci.updateTotalPrice(i.getProduct().getPrice());
            cartItemResponse.add(ci);
        }
        return cartItemResponse;
    }

    @GetMapping("/cart")
    public ModelAndView getViewCart(HttpSession session) {
        ModelAndView mv = new ModelAndView("consumer/cart");
        User user = (User) session.getAttribute(User.SESSION);
        if (User.checkUserSession(session) == true && user.getCartItems().size() == 0) {
            //th co user nhung cart dang empty
            mv.setViewName("/consumer/cart-empty");
        } else if (User.checkUserSession(session) == false && cartSession.size() == 0) {
            //th chua co user va cart session dang empty
            mv.setViewName("/consumer/cart-empty");
        } else {
            //da hoac chua co va cartSession size > 0
            mv.addObject("cartSession", cartSession);
        }
        return mv;
    }

    @GetMapping("/checkout")
    public ModelAndView goToCheckoutPage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User user = user = (User) session.getAttribute(User.SESSION);
        Collection<CartItem> cs = (Set<CartItem>) session.getAttribute("cartSession");
        if (null == user && (null != cs && cs.size() > 0)) {
            mv.addObject("cartSession", cartSession);
            mv.setViewName("consumer/checkout");
        } else if (null != user) {
            if (user.getActive() == 1) {
                mv.setViewName("consumer/checkout");
            } else {
                mv.setViewName("consumer/active-account");
            }
        } else {
            mv.setViewName("consumer/cart-empty");
        }
        return mv;
    }
}
