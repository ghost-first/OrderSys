package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public interface UserService {


    public User selectById(String userId);

    public List<User> selectAll(User user);

    public User add(User user) throws Exception;

    public User updateInfo(User user);

    public int delete(String userId);

    public String uploadPic(String itemName, MultipartHttpServletRequest request) throws Exception;
}
