package nlu.fit.cellphoneapp.repositories.interfaces;

import nlu.fit.cellphoneapp.entities.CartItem;
import nlu.fit.cellphoneapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> getAllByUser(User user);
    void deleteById(int id);
    @Transactional
    void deleteAllByUser_Id(int userID);

}
