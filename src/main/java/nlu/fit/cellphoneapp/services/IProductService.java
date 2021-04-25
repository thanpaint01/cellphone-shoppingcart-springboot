package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Product findOneByID(int productID);
    List<Product> findAll();
    Page<Product> findPaginated(int page, int limit);
}
