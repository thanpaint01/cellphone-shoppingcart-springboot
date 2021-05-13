package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Brand;
import nlu.fit.cellphoneapp.receiver.BrandProfit;

import java.util.List;

public interface IBrandService {
    List<Brand> findAll();
    Brand findOneByById(int id);
    List<Brand> findAllByActive(int active);
    List<BrandProfit> getTop5Profit();

}
