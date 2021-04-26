package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.repositories.interfaces.IProductRepository;
import nlu.fit.cellphoneapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IProductRepository productRepo;

    @Override
    public Product findOneByID(int productID) {
        return productRepo.getOne(productID);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findPaginated(int page, int limit) {
        Pageable pageable = PageRequest.of(page-1, limit);

        return productRepo.findAll(pageable);
    }

}
