package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.service.DishOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Map<String,Object>> findSomeDishOrder(Integer tableId){
        return dishOrderService.findSomeDishOrder(tableId);
    }
    @RequestMapping("/update")
    public DishOrder updateDishOrder(DishOrder dishOrder){
        return dishOrderService.updateDishOrder(dishOrder);
    }
}
