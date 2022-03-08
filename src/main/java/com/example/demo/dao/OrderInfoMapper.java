package com.example.demo.dao;

import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderInfoMapper {
    int countByExample(OrderInfoExample example);

    int deleteByExample(OrderInfoExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    List<OrderInfo> selectByExample(OrderInfoExample example);

    OrderInfo selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> queryOrder(OrderInfo orderInfo);

    List<Map<String, Object>> queryOrderDetail(@Param("orderId") int orderId);

    //查看历史数据
    List<Map<String,Object>> get7DaysData();

    List<Map<String, Object>> getSixMonthsData();

    List<Map<String,Object>> selectBySales(String start,String end);

    int updatePrice(@Param("orderId") Integer orderId,@Param("totalPrice") Double totalPrice);
}