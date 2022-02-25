package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int insert(User record);

    User selectById(String userId);

    List<User> selectAll(User user);

    int updateInfo(User record);

    int deleteById(String userId);

    int deleteByPrimaryKey(String userId);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}