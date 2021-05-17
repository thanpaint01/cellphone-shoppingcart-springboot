package nlu.fit.cellphoneapp.controllers.admin;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.NumberHelper;
import nlu.fit.cellphoneapp.receiver.BrandProfit;
import nlu.fit.cellphoneapp.repositories.custom.ReportRepository;
import nlu.fit.cellphoneapp.services.IBrandService;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping(value = "/admin")
@Controller("AdminHomeController")
public class HomeController {
    @Autowired
    ReportRepository reportRepository;


    @RequestMapping(value = "")
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView("admin/admin-index");
        model.addObject("CONTENT_TITLE", "Trang chủ");
        return model;
    }

    @RequestMapping(value = "/line-bar-chart", method = RequestMethod.POST)
    @ResponseBody
    public String dataLineBarChart(@RequestParam("type") int type, @RequestParam("fromdate") String from, @RequestParam("todate") String to) {
        Date fromDate = DateHelper.convertToDate(from, "yyyy-MM-dd");
        Date todate = DateHelper.convertToDate(to, "yyyy-MM-dd");
        if (fromDate == null || todate == null) {
            return "formatdate";
        } else if (fromDate.after(new Date()) || todate.after(new Date())) {
            return "not-today";
        } else if (fromDate.after(todate)) {
            return "greater-than-enddate";
        }
//        else if (DateHelper.monthsBetween(fromDate, todate) <= 0) {
//            return "lower-than-onemonth";
//        }
        else {
            StringBuilder sb = new StringBuilder();
            List<String> listMonths = DateHelper.getMonthsBetween(from, to);
            if (listMonths.size() == 1) {
                sb.append(listMonths.get(0) + "\t");
                sb.append(reportRepository.getDataByDate(type, from, to, Order.PAYMENT_TYPE.OFFLINE.value()));
                sb.append(reportRepository.getDataByDate(type, from, to, Order.PAYMENT_TYPE.ONLINE.value()));
            } else {
                for (int i = 0; i < listMonths.size(); i++) {
                    sb.append(listMonths.get(i) + "\t");
                    if (i == 0) {
                        sb.append(reportRepository.getDataByDate(type, from, DateHelper.convertToString(DateHelper.getLastDayMonth(fromDate)), Order.PAYMENT_TYPE.OFFLINE.value()));
                        sb.append("\t");
                        sb.append(reportRepository.getDataByDate(type, from, DateHelper.convertToString(DateHelper.getLastDayMonth(fromDate)), Order.PAYMENT_TYPE.ONLINE.value()));
                        sb.append(System.lineSeparator());
                    } else if (i == listMonths.size() - 1) {
                        sb.append(reportRepository.getDataByDate(type, DateHelper.convertToString(DateHelper.getFristDayMonth(todate)), to, Order.PAYMENT_TYPE.OFFLINE.value()));
                        sb.append("\t");
                        sb.append(reportRepository.getDataByDate(type, DateHelper.convertToString(DateHelper.getFristDayMonth(todate)), to, Order.PAYMENT_TYPE.ONLINE.value()));
                    } else {
                        sb.append(reportRepository.getDataByMonthYear(type, DateHelper.getMonthOfMMYYYY(listMonths.get(i)), DateHelper.getYearhOfMMYYYY(listMonths.get(i)), Order.PAYMENT_TYPE.OFFLINE.value()));
                        sb.append("\t");
                        sb.append(reportRepository.getDataByMonthYear(type, DateHelper.getMonthOfMMYYYY(listMonths.get(i)), DateHelper.getYearhOfMMYYYY(listMonths.get(i)), Order.PAYMENT_TYPE.OFFLINE.value()));
                        sb.append(System.lineSeparator());
                    }
                }
            }
            return sb.toString();
        }
    }

    @RequestMapping(value = "/pie-chart", method = RequestMethod.POST)
    @ResponseBody
    public String datePieChartF(@RequestParam("category") int category, @RequestParam("type") int type, @RequestParam("fromdate") String from, @RequestParam("todate") String to) {
        Date fromDate = DateHelper.convertToDate(from, "yyyy-MM-dd");
        Date todate = DateHelper.convertToDate(to, "yyyy-MM-dd");
        if (fromDate == null || todate == null) {
            return "formatdate";
        } else if (fromDate.after(new Date()) || todate.after(new Date())) {
            return "not-today";
        } else if (fromDate.after(todate)) {
            return "greater-than-enddate";
        } else {
            StringBuilder sb = new StringBuilder();
            List<String> result = reportRepository.getDataCategory(category, type, from, to);
            for (int i = 0; i < result.size(); i++) {
                if (i == result.size() - 1) {
                    sb.append(result.get(i));
                } else {
                    sb.append(result.get(i));
                    sb.append(System.lineSeparator());
                }
            }
            return sb.toString();
        }
    }
}
