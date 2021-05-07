package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.OrderDetail;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IOrderDetailService;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class OrderController {

    @Autowired
    IOrderService orderService;
    @Autowired
    ICartService cartService;
    @Autowired
    IOrderDetailService orderDetailService;

    @GetMapping("/checkout")
    public String goToCheckoutPage(Model model, HttpSession session) {
        User user;
        if (null == (user = (User) session.getAttribute(User.SESSION)) || user.getCartItems().size() == 0)
            return "consumer/cart-empty";
        return "consumer/checkout";
    }

    @GetMapping("/order")
    public String goToOrderPage(HttpSession session, Model model) {
        User userDelivery;
        if (null != (userDelivery = (User) model.getAttribute("userDelivery")) || userDelivery.getCartItems().size() == 0) {
            return "redirect:/cart";
        }
        if (null == session.getAttribute(User.SESSION)) return "redirect:/home";
        return "order";
    }

    @PostMapping("/order")
    @ResponseBody
    public String ajaxSendDataOrder(String address, String nameClient, String phoneNumber, double totalPrice, String payment, HttpSession session) {
        System.out.println("totalPriceOrder=" + totalPrice);
        Order order = new Order();
        Date createDate = new Date();
        createDate.setTime(System.currentTimeMillis());
        order.setCreatedDate(createDate);
        order.setAddress(address);
        order.setActive(1);
        order.setNameOfClient(nameClient);
        order.setPhoneNumberOfClient(phoneNumber);
        order.setTotalPrice(totalPrice);
        User user = (User) (session.getAttribute(User.SESSION));
        order.setUser(user);
        order.setOrderStatus("Đang tiếp nhận");
        order.setPayment(payment);
        if(!payment.equals("Paypal")) {
            System.out.println("UserSession" + user.getId());
            if (null != orderService.insertIntoTable(order)) {
                for (CartItem c : order.getUser().getCartItems()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setActive(1);
                    orderDetail.setSaledPrice(0.0);
                    System.out.println("CartItem cua User khi luu vao detailOrder=" + c.toString());
                    orderDetail.setAmount(c.getAmount());
                    orderDetail.setInitialPrice(c.getProduct().getPrice() * c.getAmount());
                    orderDetail.setProduct(c.getProduct());
                    orderDetail.setPrice(c.getProduct().getPrice());
                    orderDetail.setTotalPrice(c.getTotalPrice());
                    OrderDetail orderDetail1 = orderDetailService.insertIntoTable(orderDetail);
                    System.out.println(orderDetail1.toString());
                    order.getOrderDetails().add(orderDetail1);
                }
                boolean removed = cartService.removeAllByUserId(user.getId());
                user.getCartItems().clear();
                user.getOrders().add(order);
                return removed ? "/user/my-order" : "error";
            }
            return "error";
        }else{
            return "/pay";
        }

    }


}
