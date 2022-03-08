package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.TestDish;
import com.example.demo.service.serviceImpl.OrderInfoServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.example.demo.service.serviceImpl.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoServiceImpl orderInfoServiceImpl;

    private List<Map<String, Object>> past7DaysData;
    private List<Map<String, Object>> past6MonthsData;

    public void setPast7DaysData(List<Map<String, Object>> past7DaysData) {
        this.past7DaysData = past7DaysData;
    }

    public void setPast6MonthsData(List<Map<String, Object>> past6MonthsData) {
        this.past6MonthsData = past6MonthsData;
    }

    //初始化的构造函数
    public OrderInfoController(OrderInfoServiceImpl orderInfoService) {
        this.orderInfoServiceImpl = orderInfoService;
        this.past7DaysData = orderInfoServiceImpl.get7DaysData();
        this.past6MonthsData = orderInfoServiceImpl.get6MonthsData();
    }

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    @RequiresRoles("WAITER")
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
        WebSocketService.sendMessageToCook("有新的菜品");
        return orderPrice;
    }

    //买单
    @RequestMapping("/checkout")
    @RequiresRoles("WAITER")
    public boolean checkout(int orderid){
        return orderInfoServiceImpl.checkout(orderid);
    }

    //删除订单
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    @RequiresRoles("ADMIN")
    public Integer deleteOrder(Integer orderId){
        int res = orderInfoServiceImpl.deleteOrder(orderId);
        return res;
    }

    //查询订单
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    @RequiresRoles("ADMIN")
    public List<TestDish> queryOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        List<TestDish> testDishes = orderInfoServiceImpl.queryOrder(orderInfo);
        return testDishes;
    }

    //查询包含加菜的订单
    @RequestMapping(value = "/queryDetailOrder",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR,value = {"ADMIN","WAITER"})
    public List<Map<String, Object>> queryDetailOrder(OrderInfo orderInfo){
        return orderInfoServiceImpl.queryDetailOrder(orderInfo);
    }


    //加菜
    @RequestMapping("/addDishes")
    @RequiresRoles("WAITER")
    public OrderInfo addDishes(@RequestBody TestDish testDish){
        WebSocketService.sendMessageToCook("有新的菜品");
        return orderInfoServiceImpl.addDishes(testDish);
    }

    //撤销菜品
    @RequestMapping(value = "/deleteDishOrder",method = RequestMethod.POST)
    @RequiresRoles("WAITER")
    public String deleteDishOrder(DishOrder dishOrder){
        return orderInfoServiceImpl.deleteDishOrder(dishOrder);
    }



    @RequestMapping("/querySome")
    @RequiresRoles(logical = Logical.OR,value = {"ADMIN","WAITER"})
    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        return orderInfoServiceImpl.findSomeOrderInfo(tableId);
    }

    @RequestMapping("/querySales")
    public List<Map<String,Object>> selectBySales() throws ParseException {
        return orderInfoServiceImpl.selectBySales();
    }

    @RequestMapping("/get7DaysData")
    @RequiresRoles("ADMIN")
    public List<Map<String, Object>> get7DaysData(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        return past7DaysData;
    }

    @RequestMapping("/get6MonthsData")
    @RequiresRoles("ADMIN")
    public List<Map<String,Object>> get6MonthsData(){
        setPast6MonthsData(orderInfoServiceImpl.get6MonthsData());
        return past6MonthsData;
    }

    @RequestMapping("/getToday")
    @RequiresRoles("ADMIN")
    public Map<String,Object> getToday(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        return past7DaysData.get(0);
    }

    @RequestMapping("/getYesterday")
    @RequiresRoles("ADMIN")
    public Map<String,Object> getYesterday(){
        return past7DaysData.get(1);
    }

    @RequestMapping("/getThisWeek")
    @RequiresRoles("ADMIN")
    public double getThisWeek(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        List<Map<String,Object>> thisweek = past7DaysData;
        double result =0;
        for(Map<String,Object> map:thisweek){
            result+=Double.parseDouble(map.get("totalprice").toString());
            if(map.get("weekday").equals("Mon"))
                break;
        }
        return result;
    }

    @RequestMapping("/getThisMonth")
    @RequiresRoles("ADMIN")
    public Map<String,Object> getThisMonth(){
        setPast6MonthsData(orderInfoServiceImpl.get6MonthsData());
        return past6MonthsData.get(0);
    }

}
