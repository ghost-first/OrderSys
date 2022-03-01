package com.example.demo.service;

import com.example.demo.dao.OrderInfoMapper;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    //查看历史数据
    public List<Map<String,Object>> get7DaysData(){
        return orderInfoMapper.get7DaysData();
    }

//    public List<Map<String,Object>> selectBySales(){
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        System.out.println("年"+year);
//        System.out.println("月"+month);
//    }
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
}
