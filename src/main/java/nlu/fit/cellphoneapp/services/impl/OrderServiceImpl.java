package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.Product;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IOrderRepository orderRepo;

    @Override
    public Order insertIntoTable(Order order) {
        return orderRepo.save(order);
    }
    @Override
    public Page<Order> findPaginated(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return orderRepo.findAll(pageable);
    }

    @Override
    public Order updatePayment(int orderID, String payment) {
        Order order;
        if(null != (order = orderRepo.getOne(orderID))){
            order.setPayment(payment);
            order.setActive(1);
            return orderRepo.save(order);
        }else{
            return null;
        }
    }

    @Override
    public Order getOne(int orderID) {
        return orderRepo.getOne(orderID);
    }

    @Override
    public Order updateOrderStatus(Order order) {
        order.setOrderStatus("Đã hủy");
        return orderRepo.save(order);
    }

}
