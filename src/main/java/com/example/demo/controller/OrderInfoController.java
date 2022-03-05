package com.example.demo.controller;

import com.example.demo.entity.OrderInfo;
import com.example.demo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/orderInfo")
@CrossOrigin
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    private List<Map<String, Object>> past7DaysData;
    private List<Map<String, Object>> past6MonthsData;

    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
        this.past7DaysData = orderInfoService.get7DaysData();
        this.past6MonthsData = orderInfoService.get6MonthsData();
    }

    @RequestMapping("/querySome")
    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        return orderInfoService.findSomeOrderInfo(tableId);
    }

    @RequestMapping("/get7DaysData")
    public List<Map<String, Object>> get7DaysData(){
        return past7DaysData;
    }

    @RequestMapping("/get6MonthsData")
    public List<Map<String,Object>> get6MonthsData(){
        return past6MonthsData;
    }

    @RequestMapping("/getToday")
    public Map<String,Object> getToday(){
        return past7DaysData.get(0);
    }

    @RequestMapping("/getYesterday")
    public Map<String,Object> getYesterday(){
        return past7DaysData.get(1);
    }

    @RequestMapping("/getThisWeek")
    public List<Map<String,Object>> getThisWeek(){
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);
//        int len=past7DaysData.size();
        return past7DaysData.subList(0,day-1);
    }

    @RequestMapping("/getThisMonth")
    public Map<String,Object> getThisMonth(){
        return past6MonthsData.get(past6MonthsData.size()-1);
    }

    @RequestMapping("/getSixMonthsData")
    public List<Map<String, Object>> getSixMonthsData(){
        return orderInfoService.getSixMonthsData();
    }
}
