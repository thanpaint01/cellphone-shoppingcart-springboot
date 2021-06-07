package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.others.Link;
import nlu.fit.cellphoneapp.security.MyUserDetail;
import nlu.fit.cellphoneapp.receiver.UpdateInfoForm;
import nlu.fit.cellphoneapp.receiver.UpdatePasswordForm;
import nlu.fit.cellphoneapp.services.EmailSenderService;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IUserService;
import nlu.fit.cellphoneapp.validator.ValidUpdateInfo;
import nlu.fit.cellphoneapp.validator.UpdatePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import static nlu.fit.cellphoneapp.security.MyUserDetail.getUserIns;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    ICartService cartService;
    //    @Autowired
//    ValidUpdateInfo updateInfoValidator;
    @Autowired
    UpdatePasswordValidator updatePasswordValidator;
    IOrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView myAccountPage() {
        User user = getUserIns();
        ModelAndView model = new ModelAndView("/consumer/my-account");
        model.addObject("CONTENT_TITLE", "Tài Khoản Của Tôi");
        if (user.getActive() == User.ACTIVE.UNVERTIFIED.value()) {
            model.addObject("NO_ACTIVE", true);
        }
        return model;
    }

    @RequestMapping(value = "/email/verify/{token}")
    public ModelAndView vertificateEmail(@PathVariable("token") String token) {
        User u;
        if ((u = userService.findOneByToken(token)) == null)
            return new ModelAndView("redirect:/");
        else {
            u.setKey(null);
            u.setExpiredKey(null);
            u.setActive(User.ACTIVE.ACTIVE.value());
            User user = getUserIns();
            user.setKey(null);
            user.setExpiredKey(null);
            user.setActive(User.ACTIVE.ACTIVE.value());
            if (!userService.save(u)) {
                return new ModelAndView("redirect:/");
            }
            return new ModelAndView("/consumer/email-vertification");
        }
    }


    @RequestMapping(value = "request-vertify-email", method = RequestMethod.POST)
    @ResponseBody
    public String requestVerityEmail(HttpServletRequest request) {
        User user = getUserIns();
        String token;
        while (userService.isTokenUnique((token = StringHelper.getAlphaNumericString(50)))) ;
        user.setKey(token);
        user.setExpiredKey(DateHelper.addMinute(15));
        if (!userService.save(user)) {
            return "error";
        } else if (!emailSenderService.sendEmailVertification(user.getEmail(), user.getFullName(), Link.createAbsolutePath(request, "/user/email/verify/" + token))) {
            return "errsendmail";
        } else {
            return "success";
        }
    }

    @RequestMapping(value = "update-password", method = RequestMethod.GET)
    public ModelAndView updatePasswordPage() {
        ModelAndView model = new ModelAndView("/consumer/update-password");
        model.addObject("CONTENT_TITLE", "Đổi Mật Khẩu");
        return model;
    }

    @RequestMapping(value = "update-password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordForm form, Errors errors) {
        updatePasswordValidator.validate(form, errors);
        if (errors.hasErrors()) {
            Map<String, String> errMessages = new HashMap<>();
            for (ObjectError objectError : errors.getAllErrors()) {
                String fieldErrors = ((FieldError) objectError).getField();
                errMessages.put(fieldErrors, objectError.getCode());
            }
            return ResponseEntity.ok().body(errMessages);
        } else {
            User user = getUserIns();
            user.setPassword(BcryptEncoder.encode(form.newPassword));
            if (userService.save(user)) {
                return ResponseEntity.ok(HttpStatus.ACCEPTED);
            } else {
                return ResponseEntity.ok().body("failed");
            }
        }
    }

    @RequestMapping(value = "update-infor", method = RequestMethod.GET)
    public ModelAndView updateInforPage() {
        ModelAndView model = new ModelAndView("/consumer/update-infor");
        model.addObject("CONTENT_TITLE", "Cập Nhật Thông Tin Cá Nhân");
        return model;
    }

    @RequestMapping(value = "update-infor", method = RequestMethod.POST)
    public ResponseEntity updateInfor(@Valid @RequestBody UpdateInfoForm form) {
        User user = getUserIns();
        user.updateInfo(form);
        if (userService.save(user)) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else {
            return ResponseEntity.ok().body("failed");
        }
    }

    //UserMyAccountManage
    @GetMapping("my-order")
    public String goToMyOrderManagementPage(Model model, HttpSession session) {
        User user = MyUserDetail.getUserIns();
        model.addAttribute("CONTENT_TITLE", "Quản lý đơn hàng");
        if (null == user || (user.getOrders().size() == 0)) {
            return "consumer/my-order-empty";
        } else {
            checkAndSetOrderUserDB(user);
            session.setAttribute(User.SESSION, user);
            return "consumer/my-order";
        }
    }

    //

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


    //


    @GetMapping("ajax-load-by-status")
    @ResponseBody
    public void ajaxLoadWithStatusOrder(
            @RequestParam(value = "statusOrder", required = false, defaultValue = "all") String statusOrder,
            @RequestParam(value = "orderID", required = false, defaultValue = "null") String orderID,
            HttpSession session, HttpServletResponse resp) throws IOException {
        User user = (User) session.getAttribute(User.SESSION);
        //        //Method response html
        StringBuilder sb = new StringBuilder();
        resp.setCharacterEncoding("UTF-8");
        if (statusOrder.equals("all")) {

            for (Order order : user.getOrders()) {
                if (orderID.equals("null")) {
                    sb.append(loadResultForAjaxLoadWithStatusOrder(order));
                } else {
                    if (order.getId() == Integer.parseInt(orderID)) {
                        sb.append(loadResultForAjaxLoadWithStatusOrder(order));
                    }
                }
            }
        } else {
            for (Order order : user.getOrders()) {
                if (order.getOrderStatus().equals(statusOrder)) {
                    if (orderID.equals("null")) {
                        sb.append(loadResultForAjaxLoadWithStatusOrder(order));
                    } else {
                        if (order.getId() == Integer.parseInt(orderID)) {
                            sb.append(loadResultForAjaxLoadWithStatusOrder(order));
                        }
                    }
                }
            }
        }
        resp.getWriter().write(sb.toString());
    }

    public String loadResultForAjaxLoadWithStatusOrder(Order order) {
        StringBuilder sb = new StringBuilder();
        if (order.getActive() == 1) {
            sb.append(
                    " <tr class=\"order-item\">\n" +
                            "<td class=\"data-fields col1 align-middle\"><b class=\"order-id\">" + order.getId() + "</b></td>\n" +
                            "                            <td class=\"data-fields col3\">\n" +
                            "                                <a class=\"go-detail-mobile\" href=\"/product/detail?id=" + order.getOrderDetails().get(0).getProduct().getId() + "\">\n" +
                            "                                    <div class=\"d-flex\">\n" +
                            "                                        <div class=\"img-product\">\n" +
                            "                                            <img src=\"" + order.getOrderDetails().get(0).getProduct().getImg().getHost() + order.getOrderDetails().get(0).getProduct().getImg().getRelativePath() + "\" height=\"80\" width=\"80\">\n" +
                            "                                        </div>\n" +
                            "                                        <div class=\"text-left\">\n" +
                            "                                            <p>Đơn hàng bao gồm <b class=\"first-product-name\">" + order.getOrderDetails().get(0).getProduct().getName() + "</b> và <b class=\"size-1-order-amount\" \">" + (order.getOrderDetails().size() - 1) + "</b> sản\n" +
                            "                                                phẩm khác\n" +
                            "                                            </p>\n" +
                            "                                        </div>\n" +
                            "                                    </div>\n" +
                            "                                </a>\n" +
                            "                            </td>\n" +
                            "                            <td class=\"data-fields col2 order-date\"\">" + order.getCreatedDate() + "</td>\n" +
                            "                            <td class=\"data-fields col2\"><b class=\"product-price order-total-price\">" + StringHelper.formatNumber((long) order.getTotalPrice()) + " ₫</b></td>\n" +
                            "                            <td class=\"data-fields col2 status-order-" + order.getId() + "\">" + order.getOrderStatus() + "</td>\n" +
                            "                            <td class=\"data-fields col1 text-center\">\n" +
                            "                                <a href=\"#\" class=\"view\" title=\"\" data-toggle=\"tooltip\"\n" +
                            "                                   data-original-title=\"Xem chi tiết\"><i data-toggle=\"modal\"\n" +
                            "                                                                         data-target=\"#exampleModal" + order.getId() + "\"\n" +
                            "                                                                         class=\"fas fa-arrow-circle-right icon modal-del\" id=\"modal-" + order.getId() + "\"></i></a>\n" +
                            "                            </td>\n" +
                            "                        </tr>\n"
            );
            return sb.toString();
        }
        return sb.toString();
    }


}