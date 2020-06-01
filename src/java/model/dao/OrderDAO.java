package model.dao;
//metodi che ci servono dopo aver fatto la progettazione

import model.dao.exception.DuplicatedObjectException;
import model.mo.Order;

import java.util.List;

public interface OrderDAO {

    Order insert(
            String total,
            String date,
            String userId,
            String travelCode
    ) throws DuplicatedObjectException;

    boolean update(Order order, String field, String value);

    void delete(Order order);

    Order findByOrderNumber(Long number);
    List<Order> find(String field, String value);

    List<Order> allOrder();

}
