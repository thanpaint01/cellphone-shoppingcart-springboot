package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.repositories.custom.ReportRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IBrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


@SpringBootTest
class CellphoneAppApplicationTests {
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    IBrandRepository brandRepository;
    @Autowired
    ReportRepository reportRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testMonth() {
        String from = "2020-05-30";
        String to = "2020-06-01";
        System.out.println(DateHelper.monthsBetween(DateHelper.convertToDate(from, "yyyy-MM-dd"), DateHelper.convertToDate(to, "yyyy-MM-dd")));
        List<String> months = DateHelper.getMonthsBetween(from, to);
        for (int i = 0; i < months.size(); i++) {
            System.out.println(DateHelper.getMonthOfMMYYYY(months.get(i)));
        }
    }

    @Test
    void testProfit() {
//        System.out.println(NumberHelper.format(orderService.profitByMonth(DateHelper.getMonthAgo(1))));
        System.out.println(reportRepository.test());
    }

    @Test
    void testLogin() {
        User user = userService.findOneByLogin("daochichaoden@gmail.com", "Vuminhhieu123@");
        System.out.println(user != null);
    }


}




