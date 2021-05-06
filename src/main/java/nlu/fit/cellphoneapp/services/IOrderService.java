package nlu.fit.cellphoneapp.services;

import nlu.fit.cellphoneapp.entities.Order;

public interface IOrderService {
    Order insertIntoTable(Order order);
}
