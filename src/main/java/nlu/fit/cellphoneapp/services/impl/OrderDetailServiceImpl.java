package nlu.fit.cellphoneapp.services.impl;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.OrderDetail;
import nlu.fit.cellphoneapp.repositories.interfaces.IOrderDetailRepository;
import nlu.fit.cellphoneapp.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    IOrderDetailRepository orderDetailRepo;

    @Override
    public OrderDetail insertIntoTable(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public void updateActive(Order o, int active) {
        System.out.println("Inside update active order detail");
        List<OrderDetail> listOrderDetail = orderDetailRepo.getAllByOrder(o);
        for (OrderDetail od: listOrderDetail) {
            System.out.println("o_detail updated: "+od.getId());
            od.setActive(1);
            orderDetailRepo.save(od);
        }
    }
}
