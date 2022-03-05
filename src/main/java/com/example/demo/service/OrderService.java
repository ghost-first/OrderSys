package com.example.demo.service;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.TestDish;

import java.util.List;

public interface OrderService {
    OrderInfo addOrder(OrderInfo orderInfo);

    OrderInfo addDishesIntoOrder(List<DishOrder> dishes, OrderInfo order);
    boolean checkout(int orderid);

    int deleteOrder(int orderId);

    List<TestDish> queryOrder(OrderInfo orderInfo);

}
