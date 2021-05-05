package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.OrderDetail;

public interface IOrderDetailService {
    OrderDetail insertIntoTable(OrderDetail orderDetail);

}
