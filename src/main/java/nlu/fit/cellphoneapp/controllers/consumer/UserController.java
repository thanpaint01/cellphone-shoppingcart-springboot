package nlu.fit.cellphoneapp.controllers.consumer;

import nlu.fit.cellphoneapp.entities.*;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.StringHelper;
import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.others.Link;
import nlu.fit.cellphoneapp.receiver.RegisterForm;
import nlu.fit.cellphoneapp.services.EmailSenderService;
import nlu.fit.cellphoneapp.services.ICartService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    ICartService cartService;

    @RequestMapping(value = "my-account", method = RequestMethod.GET)
    public ModelAndView myAccountPage(HttpSession session) {
        User user = (User) session.getAttribute(User.SESSION);
        ModelAndView model = new ModelAndView("/consumer/my-account");
        model.addObject("CONTENT_TITLE", "Tài Khoản Của Tôi");
        if (user.getActive() == User.ACTIVE.UNVERTIFIED.value()) {
            model.addObject("NO_ACTIVE", true);
        }
        return model;
    }

    @RequestMapping(value = "/email/verify/{token}")
    public ModelAndView vertificateEmail(@PathVariable("token") String token, HttpSession session) {
        User u;
        if ((u = userService.vertifyToken(token)) == null)
            return new ModelAndView("redirect:/");
        else {
            u.setKey(null);
            u.setExpiredKey(null);
            u.setActive(User.ACTIVE.ACTIVE.value());
            if (session.getAttribute(User.SESSION) != null) session.setAttribute(User.SESSION, u);
            userService.save(u);
            return new ModelAndView("/consumer/email-vertification");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.setAttribute(User.SESSION, null);
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, HttpSession session) {
        User user;
        if (StringHelper.isNoValue(email) || StringHelper.isNoValue(password))
            return "emptyfield";
        else if ((user = userService.findOneByLogin(email, password)) != null) {
            session.setAttribute(User.SESSION, user);
            if (user.getCartItems().size() == 0) {
                List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItemsSession");
                if (null != cartItems) {
                    for (CartItem cartItem : cartItems) {
                        cartItem.setUser(user);
                        cartService.insertIntoTable(cartItem);
                    }
                    user.setCartItems(cartItems);
                    session.setAttribute("cartItemsSession", null);
                }
            } else {
                session.setAttribute("cartItemsSession", null);
            }
            return "success";
        } else
            return "failed";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String register(@RequestBody RegisterForm form, HttpServletRequest request
    ) {
        List<String> toCheck = new ArrayList<>();
        toCheck.add(form.newemail);
        toCheck.add(form.newfullname);
        toCheck.add(form.newpassword);
        toCheck.add(form.confirmpassword);
        if (StringHelper.isNoValue(toCheck)) {
            return "emptyfield";
        } else if (User.validGender(form.newgender)) {
            return "emptyfield";
        } else if (!User.validName(form.newfullname)) {
            return "errname";
        } else if (!User.validPassword(form.newpassword)) {
            return "errpass";
        } else if (!form.newpassword.equals(form.confirmpassword)) {
            return "confirmpass";
        } else if (!User.validEmail(form.newemail)) {
            return "errmail";
        } else if (userService.isEmailUnique(form.newemail)) {
            return "errmailexist";
        } else {
            User user = userService.findOneByEmail(form.newemail, User.ACTIVE.UNVERTIFIED.value());
            if (user == null)
                user = new User();
            user.setEmail(form.newemail);
            user.setGender(User.toStringGender(Integer.valueOf(form.newgender)));
            user.setPassword(BcryptEncoder.encode(form.newpassword));
            user.setFullName(form.newfullname);
            user.setActive(User.ACTIVE.UNVERTIFIED.value());
            user.setRole(User.ROLE.CONSUMEER.value());
            if (!userService.save(user)) {
                return "error";
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(User.SESSION, user);
                return "success";
            }
        }
    }

    @RequestMapping(value = "request-vertify-email", method = RequestMethod.POST)
    @ResponseBody
    public String requestVerityEmail(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(User.SESSION);
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

    @RequestMapping(value = "/forgot-pass", method = RequestMethod.POST)
    public @ResponseBody
    String forgotPass(@RequestParam(name = "email") String email) {
        User user;
        if (StringHelper.isNoValue(email)) return "emptyfield";
        else if (!userService.isEmailUnique(email))
            return "notexistemail";
        else if ((user = userService.findOneByEmail(email, User.ACTIVE.ACTIVE.value())) == null) {
            return "unactive";
        } else {
            String token;
            while (userService.isTokenUnique((token = StringHelper.getAlphaNumericString(10)))) ;
            user.setKey(token);
            user.setExpiredKey(DateHelper.addMinute(15));
            userService.save(user);
            if (emailSenderService.sendEmailResetPassword(email, user.getFullName(), token))
                return "success";
            else
                return "failed";
        }
    }

    @RequestMapping(value = "/reset-pass", method = RequestMethod.POST)
    @ResponseBody
    public String resetPass(@RequestParam(name = "resetcode") String
                                    resetcode, @RequestParam(name = "newpass") String newpass, @RequestParam(name = "confirmpass") String
                                    confirmpass) {
        User user;
        List<String> toCheck = new ArrayList<>();
        toCheck.add(resetcode);
        toCheck.add(newpass);
        toCheck.add(confirmpass);
        if (StringHelper.isNoValue(toCheck)) {
            return "emptyfield";
        } else if (!User.validPassword(newpass))
            return "validpass";
        else if (!newpass.equals(confirmpass))
            return "notequate";
        else if ((user = userService.vertifyToken(resetcode)) == null) {
            return "failcode";
        } else {
            user.setPassword(BcryptEncoder.encode(newpass));
            user.setKey(null);
            user.setExpiredKey(null);
            userService.save(user);
            return "success";
        }
    }


    //UserMyAccountManage
    @GetMapping("my-order")
    public String goToMyOrderManagementPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute(User.SESSION);
        model.addAttribute("CONTENT_TITLE", "Quản lý đơn hàng");
        if (null == user || (user.getOrders().size() == 0)) {

            return "consumer/my-order-empty";
        } else {
            return "consumer/my-order";
        }
    }


    @GetMapping("ajax-load-by-status")
    @ResponseBody
    public void ajaxLoadWithStatusOrder(
            @RequestParam(value = "statusOrder", required = false, defaultValue = "all") String statusOrder,
            @RequestParam(value = "orderID", required = false, defaultValue = "null") String orderID,
            HttpSession session, HttpServletResponse resp) throws IOException {
        User user = (User) session.getAttribute(User.SESSION);
        //Method response html
        System.out.println("statusOrder client fill=" + statusOrder);
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
                if (order.getOrderStatus()==Integer.parseInt(statusOrder)) {
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