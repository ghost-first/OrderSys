package com.example.demo.entity;

//import com.sun.source.doctree.SerialDataTree;

import java.io.Serializable;
import java.util.List;

public class TestDish implements Serializable {
    private OrderInfo newOrder;
    private List<DishOrder> dishes;

    public OrderInfo getNewOrder() {
        return newOrder;
    }

    public TestDish setNewOrder(OrderInfo newOrder) {
        this.newOrder = newOrder;
        return this;
    }

    public List<DishOrder> getDishes() {
        return dishes;
    }

    public TestDish setDishes(List<DishOrder> dishes) {
        this.dishes = dishes;
        return this;
    }

    @Override
    public String toString() {
        return "TestDish{" +
                "newOrder=" + newOrder +
                ", dishes=" + dishes +
                '}';
    }
}
