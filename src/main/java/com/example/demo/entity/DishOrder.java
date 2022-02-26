package com.example.demo.entity;

import java.io.Serializable;

public class DishOrder extends DishOrderKey implements Serializable {
    private Integer dishState;

    private Integer count;

    private static final long serialVersionUID = 1L;

    public Integer getDishState() {
        return dishState;
    }

    public void setDishState(Integer dishState) {
        this.dishState = dishState;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dishState=").append(dishState);
        sb.append(", count=").append(count);
        sb.append("]");
        return sb.toString();
    }
}