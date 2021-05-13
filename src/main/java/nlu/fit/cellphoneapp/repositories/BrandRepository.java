package nlu.fit.cellphoneapp.repositories;

import nlu.fit.cellphoneapp.entities.Brand;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BrandRepository {
    @PersistenceContext
    EntityManager em;

    public BrandRepository(EntityManager em) {
        this.em = em;
    }


}
