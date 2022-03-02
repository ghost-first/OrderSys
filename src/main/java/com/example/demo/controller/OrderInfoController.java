package com.example.demo.controller;

import com.example.demo.entity.OrderInfo;
import com.example.demo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/orderInfo")
@CrossOrigin
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping("/querySome")
    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        return orderInfoService.findSomeOrderInfo(tableId);
    }

    @RequestMapping("/get7DaysData")
    public List<Map<String, Object>> get7DaysData(){
        return orderInfoService.get7DaysData();
    }
}
