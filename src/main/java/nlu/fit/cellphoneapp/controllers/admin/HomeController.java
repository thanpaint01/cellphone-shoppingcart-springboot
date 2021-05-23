package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.repositories.custom.ReportRepository;
import nlu.fit.cellphoneapp.services.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


@RequestMapping(value = "/admin")
@Controller("AdminHomeController")
public class HomeController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    IReportService reportService;


    @RequestMapping(value = "")
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView("admin/admin-index");
        model.addObject("CONTENT_TITLE", "Trang chủ");
        return model;
    }

    @RequestMapping(value = "/line-bar-chart", method = RequestMethod.POST)
    @ResponseBody
    public String paymentChartData(@RequestParam("type") int type, @RequestParam("fromdate") String from, @RequestParam("todate") String to) {
        Date fromDate = DateHelper.convertToDate(from, "yyyy-MM-dd");
        Date todate = DateHelper.convertToDate(to, "yyyy-MM-dd");
        if (fromDate == null || todate == null) {
            return "formatdate";
        } else if (fromDate.after(new Date()) || todate.after(new Date())) {
            return "not-today";
        } else if (fromDate.after(todate)) {
            return "greater-than-enddate";
        } else {
            return reportService.getPaymentData(type, from, to);
        }
    }

    @RequestMapping(value = "/pie-chart", method = RequestMethod.POST)
    @ResponseBody
    public String categoryChartData(@RequestParam("category") int category, @RequestParam("type") int type, @RequestParam("fromdate") String from, @RequestParam("todate") String to) {
        Date fromDate = DateHelper.convertToDate(from, "yyyy-MM-dd");
        Date todate = DateHelper.convertToDate(to, "yyyy-MM-dd");
        if (fromDate == null || todate == null) {
            return "formatdate";
        } else if (fromDate.after(new Date()) || todate.after(new Date())) {
            return "not-today";
        } else if (fromDate.after(todate)) {
            return "greater-than-enddate";
        } else {
            return reportService.getCategoryData(category, type, from, to);
        }
    }
}
