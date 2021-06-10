package nlu.fit.cellphoneapp.specification;

import nlu.fit.cellphoneapp.entities.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;

@Component
public class ReviewSpecification {
    public Specification<Review> getByActiveUser(int userId) {
        return (root, query, cb) -> {
            Predicate ofUser = cb.equal(root.get(Review_.USER).get(User_.ID), userId);
            Predicate active = cb.equal(root.get(Review_.ACTIVE), 1);
            return cb.and(ofUser, active);
        };
    }

    public Specification<Review> getByActiveProduct(int productid) {
        return (root, query, cb) -> {
            Predicate ofUser = cb.equal(root.get(Review_.PRODUCT).get(Product_.ID), productid);
            Predicate active = cb.equal(root.get(Review_.ACTIVE), 1);
            return cb.and(ofUser, active);
        };
    }

}
