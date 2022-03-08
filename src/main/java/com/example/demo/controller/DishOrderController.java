package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.service.serviceImpl.DishOrderServiceImpl;
import com.example.demo.service.serviceImpl.OrderInfoServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dishOrder")
@CrossOrigin
public class DishOrderController {
    @Autowired
    private DishOrderServiceImpl dishOrderServiceImpl;
    @Autowired
    private OrderInfoServiceImpl orderInfoServiceImpl;

    @RequestMapping("/querySome")
    public List<Map<String,Object>> findSomeDishOrder(@Param("dishName")String dishName,
                                                      @Param("dishState")Integer dishState,
                                                      @Param("tableId")Integer tableId,
                                                      @Param("count")Integer count){
        List<Map<String,Object>> rs = dishOrderServiceImpl.findSomeDishOrder(dishName,dishState,tableId,count);
        return rs;
    }
    @RequestMapping("/update")
    public DishOrder updateDishOrder(DishOrder dishOrder){
        return dishOrderServiceImpl.updateDishOrder(dishOrder);
    }

    //发布传菜信息
    @RequestMapping("/sendDishInfo")
    public List<Map<String,Object>> sendDishInfo(){
        return dishOrderServiceImpl.sendDishInfo();
    }

    @RequestMapping("/cancel")
    public int cancelDishOrder(Integer orderId,Integer dishId,Integer count,Double price){
        orderInfoServiceImpl.cancelDishesFromOrder(orderId,count*price);
        return dishOrderServiceImpl.cancelDishOrder(orderId,dishId);
    }
}
