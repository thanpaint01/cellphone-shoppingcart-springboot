package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.DTOs.CartItemRequest;
import nlu.fit.cellphoneapp.DTOs.CustomResponseCart;
import nlu.fit.cellphoneapp.DTOs.CustomResponseOrder;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.OrderDetail;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.security.MyUserDetail;
import nlu.fit.cellphoneapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/order")
public class OrderControllerVer2 {

    //Order clone for paypal
    public static Order orderPayPal;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;
    @Autowired
    IProductService productService;
    @Autowired
    ICartService cartService;
    @Autowired
    IUserService userService;

    @PostMapping
    @ResponseBody
    public CustomResponseOrder createdNewOrder(@RequestBody Order orderInfo, @RequestHeader(required = false, value = "paypalSuccess") String paypalSuccess) {
        User user = MyUserDetail.getUserIns();
        if (null == user) {
            return new CustomResponseOrder();
        }
        Order order;
        if (orderInfo.getPayment().equals("cash")) {
            order = orderByCash(orderInfo);
        } else {
            if (paypalSuccess.equals("success")) {
                order = orderByPayPal(orderPayPal);
            } else {
                return null;
            }
        }
        //insertIntoTable
        order.setUser(user);
        order.setCreatedDate(new Date());
        order.setActive(1);
        order.setOrderDetails(createdOrderDetail(order));
        order = orderService.insertIntoTable(order);
        boolean insertedDetail = insertDBOrderDetail(order);
        if (insertedDetail == false) return null;
        //response
        CustomResponseOrder orderResponse = new CustomResponseOrder();
        orderResponse.setStatus(order.getOrderStatus());
        orderResponse.setActive(order.getActive());
        orderResponse.setListOrders(CustomResponseCart.cloneCart());
        orderResponse.setAmountItems(order.getOrderDetails().size());
        orderResponse.updateTotalAll();
        orderResponse.updateLastPrice();
        orderResponse.setId(order.getId());
        orderResponse.setName(order.getNameOfClient());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setPayment(order.getPayment());
        orderResponse.setPhone(order.getPhoneNumberOfClient());
        DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        orderResponse.setCreatedDate(inputFormatter.format(new Date()));


        for (CartItem c : user.getCartItems()) {
            cartService.deleteOne(c.getId());
        }
        user.getOrders().add(order);
        CustomResponseCart.clearCart();
        user.getCartItems().clear();
        return orderResponse;
    }

    //chi goi 1 lan duy khi chon paypal de thanh toan
    @PostMapping("post-order-paypal")
    @ResponseBody
    public String postOrderPayPal(@RequestBody Order orderInfo) {
        User user = MyUserDetail.getUserIns();
        if (null == user) return "error";
        orderPayPal = orderByPayPal(orderInfo);
        orderPayPal.setUser(user);
        orderPayPal.setCreatedDate(new Date());
        orderPayPal.setActive(1);
        orderPayPal.setOrderDetails(createdOrderDetail(orderPayPal));
        return "success";
    }

    @GetMapping
    @ResponseBody
    public List getAllOrderByUser(@RequestParam(required = false, defaultValue = "all") String status, @RequestParam(required = false, defaultValue = "0") String id) {
        User user;
        Collection<Order> orderCollection = null;
        if (null == MyUserDetail.getUserIns()) {
            return null;
        } else {
            user = MyUserDetail.getUserIns();
            checkAndSetOrderUserDB(user);
            orderCollection = user.getOrders();
        }
        Collection<CustomResponseOrder> listOrderResponse = new HashSet<>();
        for (Order order : orderCollection) {
            CustomResponseOrder orderResponse = CustomResponseOrder.toResponseOrder(order);
            listOrderResponse.add(orderResponse);
        }
        return toList(loadByFilter(listOrderResponse, status, Integer.parseInt(id)));
    }

    public Collection<Order> checkAndSetOrderUserDB(User user) {
        Collection<Order> collectionOrderUserDB = orderService.getListOrderOfUser(user.getId());
        Collection<Order> listOrderDeletedDB = new HashSet<>();
        if (collectionOrderUserDB.size() < user.getOrders().size()) {
            int i = 0;
            while (i < user.getOrders().size()) {
                boolean isEquals = false;
                Order cq = (Order) user.getOrders().toArray()[i];
                for (Order c : collectionOrderUserDB) {
                    if (cq.getId() == c.getId()) {
                        isEquals = true;
                    }
                }
                i++;
                if (isEquals == false) {
                    listOrderDeletedDB.add(cq);
                }
            }
            for (Order order : listOrderDeletedDB) {
                System.out.println("USER DELETE ORDER ID = " + order.getId());
                user.removeCartItem(order.getId());
            }
        }
        int i = 0;
        while (i < collectionOrderUserDB.size()) {
            Order order = (Order) collectionOrderUserDB.toArray()[i];
            for (Order c : user.getOrders()) {
                if (c.getId() == order.getId()) {
                    if (!c.equals(order)) {
                        user.updateOrderInfo(c, order);
                    }
                }
            }
            i++;
        }
        return user.getOrders();
    }

    public Order orderByCash(Order orderInfo) {
        orderInfo.setOrderStatus("Đang tiếp nhận");
        orderInfo.setPayment("Trực tiếp");
        return orderInfo;
    }

    public Order orderByPayPal(Order orderInfo) {
        orderInfo.setOrderStatus("Đang tiếp nhận");
        orderInfo.setPayment("Online");
        return orderInfo;
    }

    public Collection<OrderDetail> createdOrderDetail(Order order) {
        Collection<OrderDetail> listOrderDetails = new HashSet<>();
        for (CartItemRequest c : CustomResponseCart.listItems) {
            OrderDetail od = new OrderDetail();
            od.setOrder(order);
            od.setProduct(productService.findOneByID(c.getProductID()));
            od.setAmount(c.getAmount());
            od.setActive(c.getActive());
            od.setPrice(c.getPriceProduct());
            od.setInitialPrice(c.getTotalPrice());
            od.updateSalePrice(0);
            od.updateTotalPrice();
            listOrderDetails.add(od);

        }
        return listOrderDetails;
    }

    public boolean insertDBOrderDetail(Order order) {
        for (OrderDetail od : order.getOrderDetails()) {
            od = orderDetailService.insertIntoTable(od);
            if (od == null) return false;
        }
        return true;
    }

    public Collection<CustomResponseOrder> loadByFilter(Collection<CustomResponseOrder> list, String status, int id) {
        Collection<CustomResponseOrder> listResult = new ArrayList<>();
        if (status.equals("all") && id == 0) {
            System.out.println("DEFAULT LIST");
            return list;
        } else if (!status.equals("all") && id == 0) {
            for (CustomResponseOrder orderResponse : list) {
                if (orderResponse.getStatus().equals(status)) {
                    listResult.add(orderResponse);
                }
            }
        } else if (!status.equals("all") && id != 0) {
            for (CustomResponseOrder orderResponse : list) {
                if (orderResponse.getStatus().equals(status) && orderResponse.getId() == id) {
                    listResult.add(orderResponse);
                }
            }
        } else if (status.equals("all") && id != 0) {
            for (CustomResponseOrder orderResponse : list) {
                if (orderResponse.getId() == id) {
                    listResult.add(orderResponse);
                }
            }
        }
        return listResult;
    }

    public List toList(Collection<CustomResponseOrder> list) {
        List listResult = new ArrayList<CustomResponseOrder>(list);
        Collections.sort(listResult, Collections.reverseOrder());
        return listResult;
    }

    @PostMapping("deny")
    @ResponseBody
    public String denyOrder(int orderID) {
        User user;
        if (null == MyUserDetail.getUserIns()) {
            return null;
        } else {
            user = MyUserDetail.getUserIns();
            for (Order o : user.getOrders()) {
                if (o.getId() == orderID && o.getOrderStatus().equals("Đang tiếp nhận")) {
                    o.setOrderStatus("Đã hủy");
                    orderService.updateOrderStatus(o);
                    return "Hủy thành công!";
                }
            }
        }
        return "error";
    }
}
