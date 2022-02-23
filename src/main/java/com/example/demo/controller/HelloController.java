package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public User login(String username, String password){
        System.out.println("login"+username+"---"+password);
        User user = new User();
        user.setUid(3);
        user.setUname("汪成飞");
        return user;
    }
}
