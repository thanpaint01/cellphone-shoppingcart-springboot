package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


@SpringBootTest
class CellphoneAppApplicationTests {
    @Autowired
    IProductRepository productRepository;
    @Autowired
    IProductService productService;

    @Test
    void contextLoads() {
    }

    @Test
    void testFunction() {
        Specification<Product> specification = Specification.where(productService.getProductsByBrand(1));
        Page<Product> page = productService.getPage(specification, PageRequest.of(1, 15));
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent().size());
    }
}




