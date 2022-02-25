package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.service.DishOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dishOrder")
public class DishOrderController {
    @Autowired
    private DishOrderService dishOrderService;

//    @RequestMapping("/querySome")
//    public List<DishOrder> findSomeDishOrder(DishOrder dishOrder){
//        return dishOrderService.findSomeDishOrder(dishOrder);
//    }
    @RequestMapping("/querySome")
    public List<Map<String,Object>> findSomeDishOrder(@Param("dishName")String dishName,
                                                      @Param("dishState")Integer dishState,
                                                      @Param("tableId")Integer tableId,
                                                      @Param("count")Integer count){
        Map<String,Object> map = new HashMap<>();
        map.put("dishName",dishName);
        map.put("dishState",dishState);
        map.put("tableId",tableId);
        map.put("count",count);
        List<Map<String,Object>> rs = dishOrderService.findSomeDishOrder(map);
        System.out.println(rs.size());
        return dishOrderService.findSomeDishOrder(map);
    }
    @RequestMapping("/update")
    public DishOrder updateDishOrder(DishOrder dishOrder){
        return dishOrderService.updateDishOrder(dishOrder);
    }
}
