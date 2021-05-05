package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.OrderDetail;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderDetailRepository;
import nlu.fit.cellphoneapp.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    IOrderDetailRepository orderDetailRepo;

    @Override
    public OrderDetail insertIntoTable(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }
}
