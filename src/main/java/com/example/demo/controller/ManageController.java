package com.example.demo.controller;

import com.example.demo.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManageController {
    @Autowired
    private UserServiceImpl userServiceImpl;


}
