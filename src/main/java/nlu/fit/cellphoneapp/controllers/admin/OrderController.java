package nlu.fit.cellphoneapp.controllers.admin;


import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/orders-manage")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("pending")
    public ModelAndView getListPendingOrder(){
        ModelAndView mv = new ModelAndView("admin/admin-order-pending-management");
        List<Order> listOrderPending = orderService.listOrderByStatus("Đang tiếp nhận");
        mv.addObject("orders", listOrderPending);
        return mv;
    }
    @GetMapping("delivering")
    public ModelAndView getListDeliveringOrder(){
        ModelAndView mv = new ModelAndView("admin/admin-order-delivering-management");
        mv.addObject("orders", orderService.listOrderByStatus("Đang giao hàng"));
        return mv;
    }
    @GetMapping("finish")
    public ModelAndView getListFinishOrder(){
        ModelAndView mv = new ModelAndView("admin/admin-order-finish-management");
        mv.addObject("orders", orderService.listOrderByStatus("Giao thành công"));
        return mv;
    }
    @GetMapping("deny")
    public ModelAndView getListDenyOrder(){
        ModelAndView mv = new ModelAndView("admin/admin-order-deny-management");
        mv.addObject("orders", orderService.listOrderByStatus("Đã hủy"));
        return mv;
    }
}
