package com.example.demo.service.serviceImpl;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.dao.DishesMapper;
import com.example.demo.dao.OrderInfoMapper;
import com.example.demo.entity.*;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DishOrderMapper dishOrderMapper;
    @Autowired
    private DishesMapper dishesMapper;

    /*
    * 添加菜单
    * 参数获取订单ID，包含桌号，waiter，remarks
    * 设置时间，状态
    *
    * 返回订单号
    * */
    @Override
    public OrderInfo addOrder(OrderInfo newOrder) {
        //时间
        newOrder.setOrderTime(new Date());
        //0开始准备，1
        newOrder.setOrderState(0);

        orderInfoMapper.insert(newOrder);
        //获取订单号
        int orderId = newOrder.getOrderId();

        return newOrder;
    }

    /*
     * dish包括dish_id，count
     * 设置每个dish的订单号，将每一个菜品添加到订单中，
     * 总价格计算并set订单的总价
     * 返回总价
     * */
    @Override
    public OrderInfo addDishesIntoOrder(List<DishOrder> dishes, OrderInfo order) {
        //总价
        double totalPrice = 0;
        //设置每个dish的订单号，将每一个菜品添加到订单中
        for (DishOrder dish : dishes) {
            dish.setOrderId(order.getOrderId());
            //菜品状态
            dish.setDishState(3);

            //获取菜品ID
            Integer dishId = dish.getDishId();
            //获取菜品价格
            Double price = dishesMapper.selectByPrimaryKey(dishId).getPrice();
            //计算总价
            totalPrice += price * dish.getCount();

            dishOrderMapper.insert(dish);
        }
        order.setTotalPrice(totalPrice);
        orderInfoMapper.updateByPrimaryKeySelective(order);

        return order;
    }

    @Override
    public boolean checkout(int orderid){
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderid);
        if(orderInfo.getOrderState()==1)
            return false;
        orderInfo.setOrderState(1);
        return orderInfoMapper.updateByPrimaryKeySelective(orderInfo)>0;
    }
    /*
    * 删除DishOrder里的菜品，再删除订单
    * */
    @Override
    public int deleteOrder(int orderId) {
        int res1 = dishOrderMapper.deleteOrder(orderId);
        int res2 = orderInfoMapper.deleteByPrimaryKey(orderId);
        return res1 & res2;
    }

    @Override
    public List<TestDish> queryOrder(OrderInfo orderInfo) {
        //where add_order = 0,选出不加菜的列表
        List<OrderInfo> orderInfos = orderInfoMapper.queryOrder(orderInfo);
        return getDishes(orderInfos);
    }

    //根据订单查询
    public List<TestDish> getDishes(List<OrderInfo> orderInfos) {
        List<TestDish> list = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            // 获取订单中菜品
            List<DishOrder> dishOrders = dishOrderMapper.queryDishes(orderInfo.getOrderId());
            //菜品对应详情
            List<Dishes> dishes = new ArrayList<>();
            for (DishOrder dishOrder : dishOrders) {
                Dishes dish = dishesMapper.selectByPrimaryKey(dishOrder.getDishId());
                dishes.add(dish);
            }
            // 加菜信息

            TestDish testDish = new TestDish();
            testDish.setNewOrder(orderInfo);
            testDish.setDishes(dishOrders);
            list.add(testDish);
        }
        return list;
    }

    //获取订单信息，修改版-加菜
    public List<Map<String, Object>> queryDetailOrder(OrderInfo orderInfo){
        List<OrderInfo> orderInfos = orderInfoMapper.queryOrder(orderInfo);
        List<Map<String, Object>> maps = getDetailOrder(orderInfos);
        return maps;
    }
    public List<Map<String, Object>> getDetailOrder(List<OrderInfo> orderInfos){
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderInfo info : orderInfos) {
            Map<String,Object> map = new HashMap<>();
            // 添加订单信息
            map.put("newOrder",info);

            // 获取订单中菜品
            map.put("dishes", orderInfoMapper.queryOrderDetail(info.getOrderId()));

            //加菜信息
            int addOrder = info.getAddOrder();
            if(addOrder != 0){
                List<OrderInfo> orders = new ArrayList<>();
                OrderInfo orderInfo;
                //获取所有加菜订单
                while(addOrder!=0){
                    orderInfo = orderInfoMapper.selectByPrimaryKey(addOrder);
                    orders.add(orderInfo);
                    addOrder = orderInfo.getAddOrder();
                }
                //加菜订单中的菜品
                List<HashMap<String, Object>> addOrderList = new ArrayList<>();
                for (OrderInfo order : orders) {
                    HashMap<String, Object> addOrderMap = new HashMap<>();
                    addOrderMap.put("addOrder",order);
                    addOrderMap.put("addOrderDetail",orderInfoMapper.queryOrderDetail(order.getOrderId()));
                    addOrderList.add(addOrderMap);
                }
                map.put("addOrders",addOrderList);
            }
            list.add(map);
        }
        return list;
    }
/*
* 加菜
*
* */
    public OrderInfo addDishes(TestDish testDish){
        //加的钱
        System.out.println(testDish);
        //获取旧ID
        int firstOldId = testDish.getNewOrder().getOrderId();
        //旧订单的修改，获取新订单的ID
        OrderInfo newOrder = getAddOrderInfo(testDish.getNewOrder());

        //菜品加入订单DishOrder
        newOrder = addDishesIntoOrder(testDish.getDishOrders(), newOrder);
        System.out.println(newOrder);
//        orderInfoMapper.updateByPrimaryKeySelective(newOrder);

        //价格加到第一个菜单上
        OrderInfo firstOldOrder = orderInfoMapper.selectByPrimaryKey(firstOldId);
        firstOldOrder.setTotalPrice(firstOldOrder.getTotalPrice()+newOrder.getTotalPrice());
        orderInfoMapper.updateByPrimaryKeySelective(firstOldOrder);

        return newOrder;
    }

    public OrderInfo getAddOrderInfo(OrderInfo newOrder){
        //获取旧的订单
        OrderInfo oldOrder;
        int oldId = newOrder.getOrderId();
        do{
            oldOrder = orderInfoMapper.selectByPrimaryKey(oldId);
            oldId = oldOrder.getAddOrder();
        }while(oldOrder.getAddOrder() != 0);
        System.out.println(oldOrder);

        //订单ID设置为空
        newOrder.setOrderId(null);
        //订单时间
        newOrder.setOrderTime(new Date());
        //桌号
        newOrder.setTableId(oldOrder.getTableId());
        //订单状态
        newOrder.setOrderState(0);
        //waiter已有,remark已有，加菜订单设为0
        newOrder.setAddOrder(0);
        //插入数据
        orderInfoMapper.insert(newOrder);
        //获取新订单ID，设置旧订单加菜号
        oldOrder.setAddOrder(newOrder.getOrderId());
        System.out.println(newOrder.getOrderId());
        orderInfoMapper.updateByPrimaryKeySelective(oldOrder);

        return newOrder;
    }



    public String deleteDishOrder(DishOrder dishOrder) {
        DishOrderExample dishOrderExample = new DishOrderExample();
        DishOrderExample.Criteria criteria = dishOrderExample.createCriteria();
        criteria.andDishIdEqualTo(dishOrder.getDishId());
        criteria.andOrderIdEqualTo(dishOrder.getDishId());

        int i = dishOrderMapper.deleteByExample(dishOrderExample);

        return i==1?"success":"false";
    }
}
