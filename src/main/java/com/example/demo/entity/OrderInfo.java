package com.example.demo.entity;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    private Integer orderId;

    private String orderTime;

    private Integer tableId;

    private Integer orderState;

    private Integer waiter;

    private Double totalPrice;

    private String remarks;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getWaiter() {
        return waiter;
    }

    public void setWaiter(Integer waiter) {
        this.waiter = waiter;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", tableId=").append(tableId);
        sb.append(", orderState=").append(orderState);
        sb.append(", waiter=").append(waiter);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", remarks=").append(remarks);
        sb.append("]");
        return sb.toString();
    }
}