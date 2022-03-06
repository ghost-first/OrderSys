package com.example.demo.controller;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.TestDish;
import com.example.demo.service.serviceImpl.OrderInfoServiceImpl;
import com.example.demo.service.serviceImpl.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoServiceImpl orderInfoServiceImpl;

    private List<Map<String, Object>> past7DaysData;
    private List<Map<String, Object>> past6MonthsData;

    public void setPast7DaysData(List<Map<String, Object>> past7DaysData) {
        this.past7DaysData = past7DaysData;
    }

    public void setPast6MonthsData(List<Map<String, Object>> past6MonthsData) {
        this.past6MonthsData = past6MonthsData;
    }

    //初始化的构造函数
    public OrderInfoController(OrderInfoServiceImpl orderInfoService) {
        this.orderInfoServiceImpl = orderInfoService;
        this.past7DaysData = orderInfoServiceImpl.get7DaysData();
        this.past6MonthsData = orderInfoServiceImpl.get6MonthsData();
    }

//    生成token
    @RequestMapping("/form")
    @ResponseBody()
    public String fromToket(HttpServletRequest req, HttpServletResponse resp, Model model){
        //生成toket
        String toket = UUID.randomUUID().toString();
        //保存到session
        req.getSession().setAttribute("token",toket);
        System.out.println("开始form");
        System.out.println(req.getSession());
        //丢给前端
        model.addAttribute("token",toket);
        System.out.println(req.getSession().getAttribute("token"));
        return  toket;
    }

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    @ResponseBody
    public OrderInfo newOrder(@RequestBody TestDish testDish, @RequestHeader("token") String token, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("newOrder开始");
        System.out.println(testDish);
        System.out.println(token);
        //表单幂等性
        // 防止浏览器显示乱码
        resp.setContentType("text/html;charset=utf-8");

        //验证toket 表单是否已经提交过
//        if(!isBumit(req,token)){
//            System.out.println("您已提交了数据..或者token错误!");
//            return null;
//        }
//        resp.getWriter().write("表单数据保存成功..");
        System.out.println("表单数据提交成功");

        //删除sessionToken
        req.getSession().removeAttribute("token");

        //添加新订单，获取订单号
        OrderInfo orderInfo = orderInfoServiceImpl.addOrder(testDish.getNewOrder());
        System.out.println("获取到订单号"+orderInfo.getOrderId());

        List<DishOrder> dishes = testDish.getDishOrders();
        System.out.println("列表长度："+dishes.size());
        //菜品添加进订单，获得价格数
        OrderInfo orderPrice = orderInfoServiceImpl.addDishesIntoOrder(dishes, orderInfo);

        System.out.println(orderPrice);
        System.out.println("结束getOrder");

        WebSocketService.sendMessageToCook("有新的菜品");
        return orderPrice;
    }

    private boolean isBumit(HttpServletRequest request,String token) {
//        String token = request.getParameter("token");
        String sessionToken = (String) request.getSession().getAttribute("token");
        System.out.println("开始isBumit");
        System.out.println(request.getSession());
        System.out.println(request.getSession().toString());
        System.out.println("token:"+token);
        System.out.println("sessionToken:"+sessionToken);
        //判断是否提交过
        if (sessionToken == null) {
            return false;
        }
        // 判断是否是伪造token
        if(!(token.equals(sessionToken))){
            return false;
        }
        return true;
    }

    //买单
    @ResponseBody
    @RequestMapping("/checkout")
    public boolean checkout(int orderid){
        return orderInfoServiceImpl.checkout(orderid);
    }

    //删除订单
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    @ResponseBody
    public Integer deleteOrder(Integer orderId){
        int res = orderInfoServiceImpl.deleteOrder(orderId);
        return res;
    }

    //查询订单
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    @ResponseBody
    public List<TestDish> queryOrder(OrderInfo orderInfo){
        System.out.println("开始queryOrder");
        System.out.println(orderInfo);
        List<TestDish> testDishes = orderInfoServiceImpl.queryOrder(orderInfo);
        return testDishes;
    }

    //查询订单
    @RequestMapping(value = "/queryDetailOrder",method = RequestMethod.GET)
    public List<Map<String, Object>> queryDetailOrder(OrderInfo orderInfo){
        return orderInfoServiceImpl.queryDetailOrder(orderInfo);
    }


    //加菜
    @RequestMapping("/addDishes")
    public OrderInfo addDishes(@RequestBody TestDish testDish){
        return orderInfoServiceImpl.addDishes(testDish);
    }

    //TODO 撤销菜品
    @RequestMapping(value = "/deleteDishOrder",method = RequestMethod.POST)
    public String deleteDishOrder(DishOrder dishOrder){
        return orderInfoServiceImpl.deleteDishOrder(dishOrder);
    }


    @RequestMapping("/querySome")
    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        return orderInfoServiceImpl.findSomeOrderInfo(tableId);
    }

    @RequestMapping("/querySales")
    public List<Map<String,Object>> selectBySales() throws ParseException {
        return orderInfoServiceImpl.selectBySales();
    }

    @RequestMapping("/get7DaysData")
    public List<Map<String, Object>> get7DaysData(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        return past7DaysData;
    }

    @RequestMapping("/get6MonthsData")
    public List<Map<String,Object>> get6MonthsData(){
        setPast6MonthsData(orderInfoServiceImpl.get6MonthsData());
        return past6MonthsData;
    }

    @RequestMapping("/getToday")
    public Map<String,Object> getToday(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        return past7DaysData.get(0);
    }

    @RequestMapping("/getYesterday")
    public Map<String,Object> getYesterday(){
        return past7DaysData.get(1);
    }

    @RequestMapping("/getThisWeek")
    public double getThisWeek(){
        setPast7DaysData(orderInfoServiceImpl.get7DaysData());
        List<Map<String,Object>> thisweek = past7DaysData;
        double result =0;
        for(Map<String,Object> map:thisweek){
            result+=Double.valueOf(map.get("totalprice").toString());
            if(map.get("weekday").equals("Mon"))
                break;
        }
        return result;
    }

    @RequestMapping("/getThisMonth")
    public Map<String,Object> getThisMonth(){
        setPast6MonthsData(orderInfoServiceImpl.get6MonthsData());
        return past6MonthsData.get(0);
    }

}
