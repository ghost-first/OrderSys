package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {

    int insert(User record);

    User selectById(String userId);

    List<User> selectAll(User user);

    List<User> selectByExample(UserExample example);

    int updateInfo(User record);

    int deleteById(String userId);

    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insertSelective(User record);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);
}