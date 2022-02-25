package com.example.demo.service.serviceImpl;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    /*
    * 通过userId查找用户
    * 用于登录
    * */
    public User findById(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    public int insertUser(User user) {
        int res = userMapper.insert(user);
        return res;
    }

    public int deleteUser(String userId) {
        int res = userMapper.deleteByPrimaryKey(userId);
        return res;
    }


}
