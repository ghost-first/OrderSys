package com.example.demo.controller;

import com.example.demo.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class ManageController {
    @Autowired
    private UserServiceImpl userServiceImpl;


}
