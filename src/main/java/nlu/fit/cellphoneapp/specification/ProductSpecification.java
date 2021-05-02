package nlu.fit.cellphoneapp.specification;

import nlu.fit.cellphoneapp.entities.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;


public final class ProductSpecification {
    //            Join<Product, Brand> joinBrand = root.join(Product_.BRAND);
    public static Specification<Product> productIsActive() {
        return (root, query, cb) -> {
            Predicate activeBrand = cb.equal(root.get(Product_.BRAND).get(Brand_.ACTIVE), 1);
            Predicate activePin = cb.equal(root.get(Product_.PIN).get(Pin_.ACTIVE), 1);
//            Predicate activeRom=cb.equal(root.get(Product_.))
//            Predicate activeRam
            return null;
        };
    }

    public static Specification<Product> productOfBrand(int id) {
        return (root, query, cb) -> cb.equal(root.get(Product_.BRAND).get(Brand_.ID), id);
    }

    public static Specification<Product> productHasPin(int id) {
        return (root, query, cb) -> cb.equal(root.get(Product_.PIN).get(Pin_.ID), id);
    }

    public static Specification<Product> productHasRam(int id) {
        return (root, query, cb) -> cb.equal(root.get(Product_.PIN).get(Pin_.ID), id);
    }
}
