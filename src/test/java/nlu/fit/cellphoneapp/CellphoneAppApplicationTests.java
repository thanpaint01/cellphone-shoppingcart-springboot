package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.IBrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



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

    @Test
    void contextLoads() {
    }

    @Test
    void testMonth() {

    }

    @Test
    void testProfit() {
//        System.out.println(NumberHelper.format(orderService.profitByMonth(DateHelper.getMonthAgo(1))));

    }

    @Test
    void testLogin() {
        User user = userService.findOneByLogin("daochichaoden@gmail.com", "Vuminhhieu123@");
        System.out.println(user != null);
    }


}




