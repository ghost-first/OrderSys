package com.example.demo.dao;

import com.example.demo.entity.DishOrder;
import com.example.demo.entity.DishOrderExample;
import com.example.demo.entity.DishOrderKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishOrderMapper {
    int countByExample(DishOrderExample example);

    int deleteByExample(DishOrderExample example);

    int deleteByPrimaryKey(DishOrderKey key);

    int insert(DishOrder record);

    int insertSelective(DishOrder record);

    DishOrder selectByExample(DishOrderExample example);

    DishOrder selectByPrimaryKey(DishOrderKey key);

    int updateByExampleSelective(@Param("record") DishOrder record, @Param("example") DishOrderExample example);

    int updateByExample(@Param("record") DishOrder record, @Param("example") DishOrderExample example);

    int updateByPrimaryKeySelective(DishOrder record);

    int updateByPrimaryKey(DishOrder record);

    List<Map<String, Object>> selectByTest(Map<String, Object> map);

    int deleteOrder(int orderId);

    List<DishOrder> queryDishes(Integer orderId);

    List<Map<String, Object>> sendDishInfo();

    int cancel(DishOrderKey key);
}