package nlu.fit.cellphoneapp.repositories.interfaces;

import nlu.fit.cellphoneapp.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
