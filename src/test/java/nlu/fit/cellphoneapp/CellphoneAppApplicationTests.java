package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.specification.ProductSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@SpringBootTest
class CellphoneAppApplicationTests {
    @Autowired
    IProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void search_for_fun() {
        Specification specification = Specification.where(ProductSpecification.productOfBrand(3)).and(ProductSpecification.productHasPin(3));
        List<Product> products = productRepository.findAll(specification);
        for (Product product:products){
            System.out.println(product.getId());
        }
    }
}
