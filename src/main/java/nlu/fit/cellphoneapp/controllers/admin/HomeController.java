package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.NumberHelper;
import nlu.fit.cellphoneapp.receiver.BrandProfit;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping(value = "/admin")
@Controller("AdminHomeController")
public class HomeController {
    @Autowired
    IOrderService orderService;
    @Autowired
    IBrandService brandService;


    @RequestMapping(value = "")
    public ModelAndView homePage() {
        List<Date> dates = DateHelper.getHalfYearAgo();
        List<String> barChartProfits = new ArrayList<>();
        List<String> pieChartProfits = new ArrayList<>();
        List<BrandProfit> brandProfits = brandService.getTop5Profit();
        for (Date date : dates) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateHelper.getMonth(date) + "\t");
            sb.append(DateHelper.getYear(date) + "\t");
            sb.append(NumberHelper.format(orderService.profitByMonth(date, Order.PAYMENT_TYPE.OFFLINE.value())) + "\t");
            sb.append(NumberHelper.format(orderService.profitByMonth(date, Order.PAYMENT_TYPE.ONLINE.value())));
            barChartProfits.add(sb.toString());
        }
        for (BrandProfit profit : brandProfits) {
            StringBuilder sb = new StringBuilder();
            sb.append(profit.getId() + "\t");
            sb.append(profit.getBrandname() + "\t");
            sb.append(profit.getImg() + "\t");
            sb.append(profit.getActive() + "\t");
            sb.append(NumberHelper.format(profit.getProfit()));
            pieChartProfits.add(sb.toString());
        }
        pieChartProfits.add(NumberHelper.format(orderService.getProfitHalfYearAgo()));
        ModelAndView model = new ModelAndView("admin/admin-index");
        model.addObject("CONTENT_TITLE", "Trang chủ");
        model.addObject("barChartProfits", barChartProfits);
        model.addObject("pieChartProfits", pieChartProfits);
        return model;
    }
}
