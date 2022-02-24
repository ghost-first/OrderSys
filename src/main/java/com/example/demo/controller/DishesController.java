package com.example.demo.controller;

import com.example.demo.entity.Dishes;
import com.example.demo.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DishesController {

    @Autowired
    private DishesService dishesService;

//    public DishesService getDishesService() {
//        return dishesService;
//    }
//
//    @Autowired
//    public void setDishesService(DishesService dishesService) {
//        this.dishesService = dishesService;
//    }

    @RequestMapping("dishlist")
    @ResponseBody
    public List<Dishes> showDishes() {
        List<Dishes> list = dishesService.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping("querydish")
    public Dishes queryDish(int dishid){
        System.out.println("开始queryDish");
        Dishes dish = dishesService.findByDid(dishid);
        System.out.println(dish);
//        Dishes test = new Dishes();
//        test.setDishId(1);
//        test.setPrice(2.0);
//        test.setDishName("test");
//        test.setIntro("dish");
//        test.setType("type");
        return dish;
    }

    @ResponseBody
    @RequestMapping("removedish")
    public boolean removeDish(int dishid){
        return dishesService.removeDishes(dishid);
    }

    @ResponseBody
    @RequestMapping("adddish")
    public boolean addDish(Dishes dish){
        return dishesService.addDishes(dish);
    }

}
