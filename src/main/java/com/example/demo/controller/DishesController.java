package com.example.demo.controller;

import com.example.demo.entity.Dishes;
import com.example.demo.service.serviceImpl.DishesServiceImpl;
import org.apache.ibatis.annotations.Param;
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
    @ResponseBody
    public List<Dishes> showDishes() {
        List<Dishes> list = dishesServiceImpl.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Dishes queryDish(int dishId){
        Dishes dish = dishesServiceImpl.findByDid(dishId);
        System.out.println(dish);
        return dish;
    }
    @ResponseBody
    @RequestMapping("/querySome")
    public List<Dishes> querySomeDishes(@Param("dishName") String dishName,
                                        @Param("minPrice")Double minPrice,
                                        @Param("maxPrice")Double maxPrice,
                                        @Param("isrec") Integer isrec){
        return dishesService.findSomeDishes(dishName,minPrice,maxPrice,isrec);
    }

    @ResponseBody
    @RequestMapping("/remove")
    public boolean removeDish(int dishid){
        return dishesServiceImpl.removeDishes(dishid);
    }

    @ResponseBody
    @RequestMapping("/add")
    public boolean addDish(Dishes dish){
        System.out.println(dish);
//        dish.setDishPic(uploadPic("dish_pic",request));  //设置图片
        return dishesServiceImpl.addDishes(dish);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public boolean editDish(Dishes dish){
//        dish.setDishPic(uploadPic("dish_pic",request));  //设置图片
        System.out.println(dish);
        return dishesServiceImpl.editDishes(dish);
    }

}
