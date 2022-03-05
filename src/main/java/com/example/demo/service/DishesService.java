package com.example.demo.service;

import com.example.demo.entity.Dishes;
import com.example.demo.entity.DishesExample;

import java.util.List;

public interface DishesService {

    List<Dishes> findAll();

    List<Dishes> findSomeDishes(String dishName,Double minPrice,Double maxPrice,Integer isrec);

    Dishes findByDid(int did);

    List<Dishes> findByCondition(DishesExample ge);
    boolean addDishes(Dishes dishes);

    boolean removeDishes(int did);
    boolean removeDishesByCondition(DishesExample ge);

    boolean editDishes(Dishes dishes);
}
