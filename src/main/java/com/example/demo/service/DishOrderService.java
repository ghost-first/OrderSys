package com.example.demo.service;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.entity.DishOrder;
import com.example.demo.entity.DishOrderExample;
import com.example.demo.entity.DishOrderKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishOrderService {
    @Autowired
    private DishOrderMapper dishOrderMapper;

    public List<DishOrder> findSomeDishOrder(DishOrder dishOrder){
        DishOrderExample doe = new DishOrderExample();
        DishOrderExample.Criteria criteria = doe.createCriteria();
        if(dishOrder.getCount() != null){
            criteria.andCountEqualTo(dishOrder.getCount());
        }
        if(dishOrder.getDishState() != null){
            criteria.andDishStateEqualTo(dishOrder.getDishState());
        }
        return dishOrderMapper.selectByExample(doe);
    }
    public DishOrder updateDishOrder(DishOrder dishOrder){
        int result = dishOrderMapper.updateByPrimaryKey(dishOrder);
        if(result>0){
            DishOrderKey dishOrderKey = new DishOrderKey();
            dishOrderKey.setDishId(dishOrder.getDishId());
            dishOrderKey.setOrderId(dishOrder.getOrderId());
            DishOrder dishOrder1 = dishOrderMapper.selectByPrimaryKey(dishOrderKey);
            if(dishOrder1.getDishState() == 3){
                dishOrderMapper.deleteByPrimaryKey(dishOrderKey);
                dishOrder1 = null;
            }
            return dishOrder1;
        }else{
            return null;
        }
    }

}
