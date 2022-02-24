package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    User findById(String userId);

    int insertUser(User user);

    int deleteUser(String userId);


}
