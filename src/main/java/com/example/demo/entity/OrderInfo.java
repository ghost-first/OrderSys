package com.example.demo.entity;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.order_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private Integer orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.order_time
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private String orderTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.table_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private Integer tableId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.order_state
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private Integer orderState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.waiter
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private String waiter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.total_price
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private Double totalPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderinfo.remarks
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table orderinfo
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.order_id
     *
     * @return the value of orderinfo.order_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.order_id
     *
     * @param orderId the value for orderinfo.order_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.order_time
     *
     * @return the value of orderinfo.order_time
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.order_time
     *
     * @param orderTime the value for orderinfo.order_time
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.table_id
     *
     * @return the value of orderinfo.table_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public Integer getTableId() {
        return tableId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.table_id
     *
     * @param tableId the value for orderinfo.table_id
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.order_state
     *
     * @return the value of orderinfo.order_state
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public Integer getOrderState() {
        return orderState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.order_state
     *
     * @param orderState the value for orderinfo.order_state
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.waiter
     *
     * @return the value of orderinfo.waiter
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public String getWaiter() {
        return waiter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.waiter
     *
     * @param waiter the value for orderinfo.waiter
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.total_price
     *
     * @return the value of orderinfo.total_price
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.total_price
     *
     * @param totalPrice the value for orderinfo.total_price
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderinfo.remarks
     *
     * @return the value of orderinfo.remarks
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderinfo.remarks
     *
     * @param remarks the value for orderinfo.remarks
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbggenerated Thu Feb 24 09:38:47 CST 2022
     */
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