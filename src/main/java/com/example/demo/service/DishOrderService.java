package com.example.demo.service;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.entity.DishOrder;
import com.example.demo.entity.DishOrderExample;
import com.example.demo.entity.DishOrderKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DishOrderService {
    @Autowired
    private DishOrderMapper dishOrderMapper;

//    public List<DishOrder> findSomeDishOrder(Map<String,Object> map){
//        DishOrderExample doe = new DishOrderExample();
//        DishOrderExample.Criteria criteria = doe.createCriteria();
//        if(dishOrder.getCount() != null){
//            criteria.andCountEqualTo(dishOrder.getCount());
//        }
//        if(dishOrder.getDishState() != null){
//            criteria.andDishStateEqualTo(dishOrder.getDishState());
//        }
//        return dishOrderMapper.selectByExample(doe);
//    }
    public List<Map<String,Object>> findSomeDishOrder(Map<String,Object> map){
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

}
