package com.example.demo.service;

import com.example.demo.entity.Dishes;
import com.example.demo.entity.DishesExample;

import java.util.List;

public interface DishesService {

    public List<Dishes> findAll();

    public List<Dishes> findSomeDishes(String dishName,Double minPrice,Double maxPrice,Integer isrec);

    public Dishes findByDid(int did);

    public List<Dishes> findByCondition(DishesExample ge);
    public boolean addDishes(Dishes dishes);

    public boolean removeDishes(int did);

    public boolean removeDishesByCondition(DishesExample ge);

    public boolean editDishes(Dishes dishes);
}
