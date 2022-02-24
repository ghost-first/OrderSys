package com.example.demo.service;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;

import java.util.List;

public interface OrderService {
    OrderInfo addOrder(OrderInfo orderInfo);

    OrderInfo addDishes(List<DishOrder> dishes, OrderInfo order);
}
