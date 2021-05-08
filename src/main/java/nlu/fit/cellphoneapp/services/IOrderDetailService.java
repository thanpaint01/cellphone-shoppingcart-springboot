package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Order;
import nlu.fit.cellphoneapp.entities.OrderDetail;

public interface IOrderDetailService {
    OrderDetail insertIntoTable(OrderDetail orderDetail);
    void updateActive(Order o, int active);

}
