package com.example.demo.service.serviceImpl;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.dao.DishesMapper;
import com.example.demo.dao.OrderInfoMapper;
import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderInfoExample;
import com.example.demo.entity.TestDish;
import com.example.demo.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public OrderInfo addDishes(List<DishOrder> dishes, OrderInfo order) {
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
        List<OrderInfo> orderInfos = orderInfoMapper.queryOrder(orderInfo);
        return getDishes(orderInfos);
    }

    //根据订单查询
    public List<TestDish> getDishes(List<OrderInfo> orderInfos) {
        List<TestDish> list = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {

            List<DishOrder> dishOrders = dishOrderMapper.queryDishes(orderInfo.getOrderId());

            TestDish testDish = new TestDish();
            testDish.setNewOrder(orderInfo);
            testDish.setDishes(dishOrders);
            list.add(testDish);
        }
        return list;
    }

    public void testA(){
        System.out.println("为了commit");
    }

}
