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

    /**
     * 后厨查看订单菜品，包含条件查询
     * @param dishName
     * @param dishState
     * @param tableId
     * @param count
     * @return
     */
    public List<Map<String,Object>> findSomeDishOrder(String dishName,Integer dishState,Integer tableId,Integer count){
        Map<String,Object> map = new HashMap<>();
        map.put("dishName",dishName);
        map.put("dishState",dishState);
        map.put("tableId",tableId);
        map.put("count",count);
        return dishOrderMapper.selectByTest(map);
    }

    /**
     * 后厨更新菜品状态，烹饪结束通知服务员传菜
     * @param dishOrder
     * @return
     */
    public DishOrder updateDishOrder(DishOrder dishOrder){
        int result = dishOrderMapper.updateByPrimaryKey(dishOrder);
        if(result>0){
            DishOrderKey dishOrderKey = new DishOrderKey();
            dishOrderKey.setDishId(dishOrder.getDishId());
            dishOrderKey.setOrderId(dishOrder.getOrderId());
            DishOrder dishOrder1 = dishOrderMapper.selectByPrimaryKey(dishOrderKey);
            if (dishOrder1.getDishState() == 2){
                WebSocketService.sendMessageToWaiter("有新菜品待传送");
            }
            return dishOrder1;
        }else{
            return null;
        }
    }

    /**
     * 服务员查看待传送的菜品
     * @return
     */
    public List<Map<String,Object>> sendDishInfo(){
        return dishOrderMapper.sendDishInfo();
    }

    /**
     * 服务员撤销菜品
     * @param orderId
     * @param dishId
     * @return
     */
    public int cancelDishOrder(Integer orderId,Integer dishId){
        DishOrderKey dishOrderKey = new DishOrderKey();
        dishOrderKey.setOrderId(orderId);
        dishOrderKey.setDishId(dishId);
        WebSocketService.sendMessageToCook("撤菜");
        return dishOrderMapper.cancel(dishOrderKey);
    }

}
