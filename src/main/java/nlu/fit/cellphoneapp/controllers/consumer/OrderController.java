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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class OrderController {

    public static int oi = 0;

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
    public String ajaxSendDataOrder(String address, String nameClient, String phoneNumber, double totalPrice, String payment, String paypalResponse, HttpSession session) {
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
        order.setOrderStatus(Order.STATUS.PENDING.value());
        order.setPayment(payment);

        System.out.println("UserSession" + user.getId());
        Order order1 = null;
        System.out.println("paypal respone = " + paypalResponse);
        if (!paypalResponse.equals("success")) {
            System.out.println("Click button order!");
            order1 = orderService.insertIntoTable(order);
            System.out.println("Order saved!");
            oi = order1.getId();
            System.out.println("oi saved = " + oi);
            System.out.println("order id = " + order.getId());
        } else {
            //reset data when paypal success
            System.out.println("paypal response is success");
            System.out.println("oi = " + oi);
            user.getOrders().add(orderService.getOne(oi));
            for (Order o : user.getOrders()) {
                cartService.removeAllByUserId(user.getId());
                user.getCartItems().clear();
                if (o.getId() == oi) {
                    System.out.println("Payment success oi = " + oi);
                    o.setPayment("Online");
                    o.setActive(1);
                    orderService.updatePayment(o.getId(), "Online");
                    //cap nhat order detail co order id thanh active
                    orderDetailService.updateActive(o, 1);
                }
                System.out.println("thanh toán bằng paypal order:" + user.getOrders().size());
            }
        }
        if (null != order1) {
            if (!payment.equals("Paypal")) {
                order.setActive(1);
            } else {
                order.setActive(0);
            }
            for (CartItem c : order.getUser().getCartItems()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setSaledPrice(0.0);
                orderDetail.setActive(0);
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
            boolean removed = false;
            //cho cancel paypal
            if (!payment.equals("Paypal")) {
                orderDetailService.updateActive(order, 1);
                removed = cartService.removeAllByUserId(user.getId());
                user.getCartItems().clear();
                user.getOrders().add(order);
                System.out.println("Order tra truc tiep bang tien mat: " + user.getOrders().size());
                return "/user/my-order";
            } else {
                return "/pay";
            }
        } else {
            return "error";
        }
    }

    @PostMapping("/ajax-deny-order")
    public  @ResponseBody String ajaxDenyOrder(int orderID, HttpSession session) {
        System.out.println("DenyOrder id = "+orderID);
        User user = (User) session.getAttribute(User.SESSION);
        for (Order o : user.getOrders()) {
            if(o.getId() == orderID && o.getOrderStatus()==Order.STATUS.PENDING.value()){
                o.setOrderStatus(Order.STATUS.CANCELED.value());
                orderService.updateOrderStatus(o);
                System.out.println("Hủy thành công đơn hàng "+orderID);
                return "Hủy thành công!";
            }
        }
        return "error";
    }
}
