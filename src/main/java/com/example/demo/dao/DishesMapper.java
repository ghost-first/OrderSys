package com.example.demo.dao;

import com.example.demo.entity.Dishes;
import com.example.demo.entity.DishesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DishesMapper {
    int countByExample(DishesExample example);

    int deleteByExample(DishesExample example);

    int deleteByPrimaryKey(Integer dishId);

    int insert(Dishes record);

    int insertSelective(Dishes record);

    List<Dishes> selectByExample(DishesExample example);

    Dishes selectByPrimaryKey(Integer dishId);

    int updateByExampleSelective(@Param("record") Dishes record, @Param("example") DishesExample example);

    int updateByExample(@Param("record") Dishes record, @Param("example") DishesExample example);

    int updateByPrimaryKeySelective(Dishes record);

    int updateByPrimaryKey(Dishes record);
}