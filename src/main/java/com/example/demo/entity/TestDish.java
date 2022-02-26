package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

public class TestDish implements Serializable {
    private OrderInfo newOrder;
    private List<DishOrder> dishOrders;
    private List<Dishes> dishes;

    public OrderInfo getNewOrder() {
        return newOrder;
    }

    public TestDish setNewOrder(OrderInfo newOrder) {
        this.newOrder = newOrder;
        return this;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public TestDish setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
        return this;
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

    public TestDish setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
        return this;
    }

    @Override
    public String toString() {
        return "TestDish{" +
                "newOrder=" + newOrder +
                ", dishOrders=" + dishOrders +
                ", dishes=" + dishes +
                '}';
    }
}
