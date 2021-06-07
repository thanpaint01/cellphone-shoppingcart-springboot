package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.repositories.custom.ReportRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IBrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


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
    @Autowired
    IUserRepository userRepository;

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
    void login() {
        System.out.println(BcryptEncoder.encode("Vuminhhieu123@2"));
        if (userRepository==null){
            System.out.println("NULL");
        }else{
            System.out.println("NOT");
        }
    }

    @Test
    void testReport() {
        System.out.println(userService.isEmailUnique("daochichaoden1@gmail.com"));
    }


}




