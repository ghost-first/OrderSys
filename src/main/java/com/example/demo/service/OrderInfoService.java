package com.example.demo.service;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderInfoExample;
import com.example.demo.entity.TestDish;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface OrderInfoService {
    OrderInfo addOrder(OrderInfo orderInfo);

    OrderInfo addDishesIntoOrder(List<DishOrder> dishes, OrderInfo order);
    boolean checkout(int orderid);

    int deleteOrder(int orderId);

    List<TestDish> queryOrder(OrderInfo orderInfo);
    List<OrderInfo> findSomeOrderInfo(Integer tableId);

    //查看历史数据
    List<Map<String,Object>> get7DaysData();

    List<Map<String, Object>> getSixMonthsData();

    List<Map<String,Object>> selectBySales() throws ParseException;

    int cancelDishesFromOrder(Integer orderId,Double totalPrice);
}
