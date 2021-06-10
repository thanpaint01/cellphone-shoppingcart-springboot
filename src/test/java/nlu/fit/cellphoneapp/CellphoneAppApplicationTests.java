package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Review;
import nlu.fit.cellphoneapp.helper.DateHelper;
import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.repositories.custom.ReportRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IBrandRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.repositories.interfaces.IUserRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import nlu.fit.cellphoneapp.services.IReviewService;
import nlu.fit.cellphoneapp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;


@SpringBootTest
class CellphoneAppApplicationTests {
    @Autowired
    IReviewService service;

    @Test
    void contextLoads() {
        Specification<Review> spec = Specification.where(service.getByActiveUser(28));
        Pageable pageable = PageRequest.of(1 - 1, 10);
        Page<Review> pages = service.findAllBySpec(spec, pageable);
        System.out.println(pages.getContent().get(0).getId());
    }


}




