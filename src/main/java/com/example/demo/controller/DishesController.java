package com.example.demo.controller;

import com.example.demo.entity.Dishes;
import com.example.demo.service.serviceImpl.DishesServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/dishes")
@CrossOrigin
public class DishesController {

    @Autowired
    private DishesServiceImpl dishesServiceImpl;

    @RequestMapping("/all")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN"})
    public List<Dishes> showDishes() {
        List<Dishes> list = dishesServiceImpl.findAll();
        return list;
    }


    @RequestMapping("/query")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN"})
    public Dishes queryDish(int dishId){
        Dishes dish = dishesServiceImpl.findByDid(dishId);
        return dish;
    }

    @RequestMapping("/querySome")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN"})
    public List<Dishes> querySomeDishes(@Param("dishName") String dishName,
                                        @Param("minPrice")Double minPrice,
                                        @Param("maxPrice")Double maxPrice,
                                        @Param("isrec") Integer isrec){
        return dishesServiceImpl.findSomeDishes(dishName,minPrice,maxPrice,isrec);
    }

    @RequestMapping("/remove")
    @RequiresRoles("ADMIN")
    public boolean removeDish(int dishid){
        return dishesServiceImpl.removeDishes(dishid);
    }


    @RequestMapping("/add")
    @RequiresRoles("ADMIN")
    public boolean addDish(Dishes dish){
        return dishesServiceImpl.addDishes(dish);
    }

    @RequestMapping("/edit")
    @RequiresRoles("ADMIN")
    public boolean editDish(Dishes dish){
        return dishesServiceImpl.editDishes(dish);
    }

}
