package com.example.demo.service;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.DishOrderKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DishOrderService {
    List<Map<String,Object>> findSomeDishOrder(String dishName,Integer dishState,Integer tableId,Integer count);

    DishOrder updateDishOrder(DishOrder dishOrder);

    //发布传菜信息
    List<Map<String,Object>> sendDishInfo();

    int cancelDishOrder(Integer orderId,Integer dishId);
}
