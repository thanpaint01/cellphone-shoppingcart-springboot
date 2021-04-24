package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Product;

import java.util.List;

public interface IProductService {
    Product findOneByID(int productID);
    List<Product> findAll();
}
