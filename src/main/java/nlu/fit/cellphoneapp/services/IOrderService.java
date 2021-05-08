package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Order;
import org.springframework.data.domain.Page;

public interface IOrderService {
    Order insertIntoTable(Order order);
    Page<Order> findPaginated(int page, int limit);
    Order updatePayment(int orderID, String payment);
    Order getOne(int orderID);
}
