package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Order;
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

import java.util.ArrayList;
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
        String from = "2021-05-12";
        String to = "2021-06-17";
        Date fromDate = DateHelper.convertToDate(from, "yyyy-MM-dd");
        Date todate = DateHelper.convertToDate(to, "yyyy-MM-dd");
        System.out.println(!fromDate.before(new Date()) || !todate.before(new Date()));
    }

    @Test
    void testReport() {

    }


    @Test
    void testLogin() {
        User user = userService.findOneByLogin("daochichaoden@gmail.com", "Vuminhhieu123@");
        System.out.println(user != null);
    }


}




