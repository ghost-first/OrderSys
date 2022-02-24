package com.example.demo.service;

import com.example.demo.dao.OrderInfoMapper;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public List<OrderInfo> findSomeOrderInfo(Integer tableId){
        OrderInfoExample oie = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = oie.createCriteria();
        criteria.andTableIdEqualTo(tableId);
        return orderInfoMapper.selectByExample(oie);
    }
}
