package com.example.demo.service.serviceImpl;

import com.example.demo.dao.DishOrderMapper;
import com.example.demo.dao.DishesMapper;
import com.example.demo.dao.OrderInfoMapper;
import com.example.demo.entity.*;
import com.example.demo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DishOrderMapper dishOrderMapper;
    @Autowired
    private DishesMapper dishesMapper;

    /**
     * 添加菜品
     * @param newOrder 获取订单ID，包含桌号，waiter，remarks
     * @return 返回订单信息，包括订单号
     */
    @Override
    public OrderInfo addOrder(OrderInfo newOrder) {
        //时间
        newOrder.setOrderTime(new Date());
        //0开始准备，1
        newOrder.setOrderState(0);
        //设置加菜情况
        newOrder.setAddOrder(0);

        orderInfoMapper.insert(newOrder);
        //获取订单号
        int orderId = newOrder.getOrderId();

        return newOrder;
    }

    /**
     *设置每个dish的订单号，将每一个菜品添加到订单中，计算总价格并set订单的总价
     * @param dishes 包括dish_id,count
     * @param order 包括订单号
     * @return 返回订单，包括总价
     */
    @Override
    public OrderInfo addDishesIntoOrder(List<DishOrder> dishes, OrderInfo order) {
        //总价
        double totalPrice = 0;
        //设置每个dish的订单号，将每一个菜品添加到订单中
        for (DishOrder dish : dishes) {
            dish.setOrderId(order.getOrderId());
            //菜品状态
            dish.setDishState(0);

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

    /**
     * 查询订单信息
     * @param orderInfo
     * @return 包括订单本身，以及订单对应的所点菜品
     */
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
            testDish.setDishes(dishes);
            testDish.setDishOrders(dishOrders);
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
            //设置总金额
            Double totalPrice = info.getTotalPrice();
            Map<String,Object> map = new HashMap<>();


            // 获取订单中菜品
            System.out.println(orderInfoMapper.queryOrderDetail(info.getOrderId()));
            map.put("dishes", orderInfoMapper.queryOrderDetail(info.getOrderId()));
            System.out.println("获取订单中菜品");

            //加菜信息
            System.out.println(info);
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
                    totalPrice += order.getTotalPrice();
                    HashMap<String, Object> addOrderMap = new HashMap<>();
                    addOrderMap.put("addOrder",order);
                    addOrderMap.put("addOrderDetail",orderInfoMapper.queryOrderDetail(order.getOrderId()));
                    addOrderList.add(addOrderMap);
                }
                map.put("addOrders",addOrderList);
            }
            // 添加订单信息
            info.setTotalPrice(totalPrice);
            map.put("newOrder",info);
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
        //旧订单的修改，获取新订单的ID
        OrderInfo newOrder = getAddOrderInfo(testDish.getNewOrder());

        //菜品加入订单DishOrder
        newOrder = addDishesIntoOrder(testDish.getDishOrders(), newOrder);
        System.out.println(newOrder);

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

    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        OrderInfoExample oie = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = oie.createCriteria();
        criteria.andTableIdEqualTo(tableId);
        return orderInfoMapper.selectByExample(oie);
    }

    //查看历史数据
    public List<Map<String,Object>> get7DaysData(){
        List<Map<String,Object>> result = orderInfoMapper.get7DaysData();
        for(Map<String,Object> map:result){
            if(map.get("totalprice")==null)
                map.put("totalprice",0);
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> get6MonthsData() {
        return orderInfoMapper.get6MonthsData();
    }

    public List<Map<String,Object>> selectBySales() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        System.out.println(today);
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
//        //月份+1，天设置为0。下个月第0天，就是这个月最后一天
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 0);
        String end = sdf.format(cld.getTime());
        cld.set(Calendar.DAY_OF_MONTH,1);
        String start = sdf.format(cld.getTime());
        return orderInfoMapper.selectBySales(start,end);
    }
    /**
     * 服务员撤单，订单总金额减少
     */
    public int cancelDishesFromOrder(Integer orderId,Double totalPrice){
        return orderInfoMapper.updatePrice(orderId,totalPrice);
    }
}
