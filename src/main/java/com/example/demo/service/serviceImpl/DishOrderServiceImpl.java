package com.example.demo.service.serviceImpl;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.entity.DishOrder;
import com.example.demo.entity.DishOrderExample;
import com.example.demo.entity.DishOrderKey;
import com.example.demo.service.DishOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DishOrderServiceImpl implements DishOrderService {

    @Autowired
    private DishOrderMapper dishOrderMapper;

    public List<Map<String,Object>> findSomeDishOrder(String dishName,Integer dishState,Integer tableId,Integer count){
        Map<String,Object> map = new HashMap<>();
        map.put("dishName",dishName);
        map.put("dishState",dishState);
        map.put("tableId",tableId);
        map.put("count",count);
        return dishOrderMapper.selectByTest(map);
    }
    public DishOrder updateDishOrder(DishOrder dishOrder){
        int result = dishOrderMapper.updateByPrimaryKey(dishOrder);
        if(result>0){
            DishOrderKey dishOrderKey = new DishOrderKey();
            dishOrderKey.setDishId(dishOrder.getDishId());
            dishOrderKey.setOrderId(dishOrder.getOrderId());
            DishOrder dishOrder1 = dishOrderMapper.selectByPrimaryKey(dishOrderKey);
            return dishOrder1;
        }else{
            return null;
        }
    }

    //发布传菜信息
    public List<Map<String,Object>> sendDishInfo(){
        return dishOrderMapper.sendDishInfo();
    }


}
