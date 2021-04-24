package nlu.fit.cellphoneapp.repositories;

import nlu.fit.cellphoneapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class IProductRepository implements JpaRepository<Product, Integer> {
}
