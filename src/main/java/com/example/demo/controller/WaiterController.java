package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.TestDish;
import com.example.demo.service.OrderService;
import com.example.demo.service.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WaiterController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;


    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    @ResponseBody
    public OrderInfo newOrder(@RequestBody TestDish testDish){
        System.out.println("newOrder开始");
        System.out.println(testDish);

        //添加新订单，获取订单号
        OrderInfo orderInfo = orderServiceImpl.addOrder(testDish.getNewOrder());
        System.out.println("获取到订单号"+orderInfo.getOrderId());

        List<DishOrder> dishes = testDish.getDishes();
        System.out.println("列表长度："+dishes.size());
        //菜品添加进订单，获得价格数
        OrderInfo orderPrice = orderServiceImpl.addDishes(dishes, orderInfo);

        System.out.println(orderPrice);
        System.out.println("结束getOrder");
        return orderPrice;
    }

    @ResponseBody
    @RequestMapping("/checkout")
    public boolean checkout(int orderid){
        return orderServiceImpl.checkout(orderid);
    }
}
