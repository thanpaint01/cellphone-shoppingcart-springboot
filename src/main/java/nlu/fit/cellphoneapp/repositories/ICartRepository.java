package nlu.fit.cellphoneapp.repositories;

import nlu.fit.cellphoneapp.entities.Cart;
import nlu.fit.cellphoneapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> getAllByUser(User user);
}
