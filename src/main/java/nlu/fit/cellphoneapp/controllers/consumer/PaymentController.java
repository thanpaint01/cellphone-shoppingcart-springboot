package nlu.fit.cellphoneapp.controllers.consumer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nlu.fit.cellphoneapp.config.PaypalPaymentIntent;
import nlu.fit.cellphoneapp.config.PaypalPaymentMethod;
import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.OrderDetail;
import nlu.fit.cellphoneapp.others.Link;
import nlu.fit.cellphoneapp.services.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "/success";
    public static final String URL_PAYPAL_CANCEL = "/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;


    @PostMapping("/pay")
    @ResponseBody
    public String pay(HttpServletRequest request, double totalPrice){
        System.out.println("totalPrice paypal test="+totalPrice);
        String cancelUrl = Link.createAbsolutePath(request, URL_PAYPAL_CANCEL);
        System.out.println("cancelurl="+cancelUrl);
        String successUrl = Link.createAbsolutePath(request, URL_PAYPAL_SUCCESS);
        System.out.println("successurl="+successUrl);
        try {
            System.out.println("Vao try pay");
            Payment payment = paypalService.createPayment(
                    totalPrice/100,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                System.out.println("linkPayment redirect: =="+links.getHref());
                if(links.getRel().equals("approval_url")){
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        System.out.println("Vao Cancel paypal");
        return "consumer/paypal-cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        System.out.println("Vao Success paypal");

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "consumer/paypal-success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

}