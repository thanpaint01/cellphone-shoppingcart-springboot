package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.entities.User;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.services.IProductService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


@SpringBootTest
class CellphoneAppApplicationTests {
    @Autowired
    IUserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testLogin() {
        User user = userService.findOneByLogin("daochichaoden@gmail.com", "Vuminhhieu123@");
        System.out.println(user != null);
    }

    @Test
    void testFunction() {

    }
}




