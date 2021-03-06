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
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    @ResponseBody
    public OrderInfo getOrder(OrderInfo newOrder,@RequestBody List<DishOrder> dishes){
        System.out.println("getOrder开始");
        System.out.println(newOrder);
        //添加新订单，获取订单号
        OrderInfo orderInfo = orderServiceImpl.addOrder(newOrder);
        System.out.println("获取到订单号"+orderInfo.getOrderId());

        System.out.println("列表长度："+dishes.size());
        //菜品添加进订单，获得价格数
        OrderInfo orderPrice = orderServiceImpl.addDishes(dishes, orderInfo);

        System.out.println(orderPrice);
        System.out.println("结束getOrder");
        return orderPrice;
    }

    //测试
    @RequestMapping(value = "/getOrder1",method = RequestMethod.POST)
    @ResponseBody
    public String getOrder1(@RequestBody List<DishOrder> dishes){
        System.out.println(dishes.size());
        for (DishOrder dish : dishes) {
            System.out.println(dish);
        }
        return "success";
    }

    @RequestMapping(value = "/getOrder2",method = RequestMethod.POST)
    @ResponseBody
    public String getOrder2(@RequestBody Dishes dish){
        System.out.println(dish);
//        for (TestDish dish : dishes) {
//            System.out.println(dish);
//        }
        return "success";
    }

    @RequestMapping(value = "/getOrder3",method = RequestMethod.POST)
    @ResponseBody
    public String getOrder3(@RequestBody TestDish testDish){
        System.out.println(testDish);
//        for (TestDish dish : dishes) {
//            System.out.println(dish);
//        }
        return "success";
    }

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    @ResponseBody
    public OrderInfo newOrder(@RequestBody TestDish testDish){
        System.out.println("newOrder开始");
        System.out.println(testDish);

        //添加新订单，获取订单号
        OrderInfo orderInfo = orderServiceImpl.addOrder(testDish.getNewOrder());
        System.out.println("获取到订单号"+orderInfo.getOrderId());

        List<DishOrder> dishes = testDish.getDishOrders();
        System.out.println("列表长度："+dishes.size());
        //菜品添加进订单，获得价格数
        OrderInfo orderPrice = orderServiceImpl.addDishesIntoOrder(dishes, orderInfo);

        System.out.println(orderPrice);
        System.out.println("结束getOrder");
        return orderPrice;
    }

    //买单
    @RequestMapping("/checkout")
    public boolean checkout(int orderid){
        return orderServiceImpl.checkout(orderid);
    }

    //删除订单
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    @ResponseBody
    public Integer deleteOrder(Integer orderId){
        int res = orderServiceImpl.deleteOrder(orderId);
        return res;
    }

    //查询订单
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    public List<TestDish> queryOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        List<TestDish> testDishes = orderServiceImpl.queryOrder(orderInfo);
        return testDishes;
    }

    //查询订单
    @RequestMapping(value = "/queryDetailOrder",method = RequestMethod.GET)
    public List<Map<String, Object>> queryDetailOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        return orderServiceImpl.queryDetailOrder(orderInfo);
    }


}
