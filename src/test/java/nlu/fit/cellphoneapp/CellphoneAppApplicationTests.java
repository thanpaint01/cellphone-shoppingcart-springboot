package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.helper.NumberHelper;
import nlu.fit.cellphoneapp.receiver.BrandProfit;
import nlu.fit.cellphoneapp.repositories.BrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IBrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IProductService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    @Test
    void contextLoads() {
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




