package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.TestDish;
import com.example.demo.service.serviceImpl.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoServiceImpl orderInfoServiceImpl;

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    @ResponseBody
    public OrderInfo newOrder(@RequestBody TestDish testDish){
        System.out.println("newOrder开始");
        System.out.println(testDish);

        //添加新订单，获取订单号
        OrderInfo orderInfo = orderInfoServiceImpl.addOrder(testDish.getNewOrder());
        System.out.println("获取到订单号"+orderInfo.getOrderId());

        List<DishOrder> dishes = testDish.getDishOrders();
        System.out.println("列表长度："+dishes.size());
        //菜品添加进订单，获得价格数
        OrderInfo orderPrice = orderInfoServiceImpl.addDishesIntoOrder(dishes, orderInfo);

        System.out.println(orderPrice);
        System.out.println("结束getOrder");
        return orderPrice;
    }

    //买单
    @ResponseBody
    @RequestMapping("/checkout")
    public boolean checkout(int orderid){
        return orderInfoServiceImpl.checkout(orderid);
    }

    //删除订单
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    @ResponseBody
    public Integer deleteOrder(Integer orderId){
        int res = orderInfoServiceImpl.deleteOrder(orderId);
        return res;
    }

    //查询订单
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    @ResponseBody
    public List<TestDish> queryOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        List<TestDish> testDishes = orderInfoServiceImpl.queryOrder(orderInfo);
        return testDishes;
    }

    //查询订单
    @RequestMapping(value = "/queryDetailOrder",method = RequestMethod.GET)
    public List<Map<String, Object>> queryDetailOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        return orderInfoServiceImpl.queryDetailOrder(orderInfo);
    }


    //加菜
    @RequestMapping("/addDishes")
    public OrderInfo addDishes(@RequestBody TestDish testDish){
        return orderInfoServiceImpl.addDishes(testDish);
    }

    //TODO 撤销菜品
    @RequestMapping(value = "/deleteDishOrder",method = RequestMethod.POST)
    public String deleteDishOrder(DishOrder dishOrder){
        return orderInfoServiceImpl.deleteDishOrder(dishOrder);
    }
    @RequestMapping("/querySome")
    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        return orderInfoServiceImpl.findSomeOrderInfo(tableId);
    }

    @RequestMapping("/get7DaysData")
    public List<Map<String, Object>> get7DaysData(){
        return orderInfoServiceImpl.get7DaysData();
    }

    @RequestMapping("/getSixMonthsData")
    public List<Map<String, Object>> getSixMonthsData(){
        return orderInfoServiceImpl.getSixMonthsData();
    }
}
