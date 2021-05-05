package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderRepository;
import nlu.fit.cellphoneapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IOrderRepository orderRepo;

    @Override
    public Order insertIntoTable(Order order) {
        return orderRepo.save(order);
    }
}
