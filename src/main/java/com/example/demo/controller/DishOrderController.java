package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.service.serviceImpl.DishOrderServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dishOrder")
@CrossOrigin
public class DishOrderController {
    @Autowired
    private DishOrderServiceImpl dishOrderServiceImpl;

    @RequestMapping("/querySome")
    @RequiresRoles("COOK")
    public List<Map<String,Object>> findSomeDishOrder(@Param("dishName")String dishName,
                                                      @Param("dishState")Integer dishState,
                                                      @Param("tableId")Integer tableId,
                                                      @Param("count")Integer count){
        List<Map<String,Object>> rs = dishOrderServiceImpl.findSomeDishOrder(dishName,dishState,tableId,count);
        return rs;
    }
    @RequestMapping("/update")
    @RequiresRoles(logical = Logical.OR, value = {"COOK", "WAITER"})
    public DishOrder updateDishOrder(DishOrder dishOrder){
        return dishOrderServiceImpl.updateDishOrder(dishOrder);
    }

    //发布传菜信息
    @RequestMapping("/sendDishInfo")
    @RequiresRoles("WAITER")
    public List<Map<String,Object>> sendDishInfo(){
        return dishOrderServiceImpl.sendDishInfo();
    }

}
