package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(String userId){
        return userMapper.selectById(userId);
    }
    public List<User> selectAll(User user){
        return userMapper.selectAll(user);
    }
    public User add(User user){
        User oldUser = userMapper.selectById(user.getUserId());
        if(oldUser != null){
            return oldUser;
        }
        userMapper.insert(user);
        return user;
    }
    public int updateInfo(User user){
        return userMapper.updateInfo(user);
    }
    public int delete(String userId){
        return userMapper.deleteById(userId);
    }
}
