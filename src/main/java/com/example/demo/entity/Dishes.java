package com.example.demo.entity;

import java.io.Serializable;

public class Dishes implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.dish_id
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private Integer dishId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.dish_name
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private String dishName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.price
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.intro
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private String intro;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.detail
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private String detail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.type
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishes.dish_pic
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private String dishPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dishes
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.dish_id
     *
     * @return the value of dishes.dish_id
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public Integer getDishId() {
        return dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.dish_id
     *
     * @param dishId the value for dishes.dish_id
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.dish_name
     *
     * @return the value of dishes.dish_name
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.dish_name
     *
     * @param dishName the value for dishes.dish_name
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setDishName(String dishName) {
        this.dishName = dishName == null ? null : dishName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.price
     *
     * @return the value of dishes.price
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.price
     *
     * @param price the value for dishes.price
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.intro
     *
     * @return the value of dishes.intro
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.intro
     *
     * @param intro the value for dishes.intro
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.detail
     *
     * @return the value of dishes.detail
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.detail
     *
     * @param detail the value for dishes.detail
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.type
     *
     * @return the value of dishes.type
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.type
     *
     * @param type the value for dishes.type
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishes.dish_pic
     *
     * @return the value of dishes.dish_pic
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public String getDishPic() {
        return dishPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishes.dish_pic
     *
     * @param dishPic the value for dishes.dish_pic
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    public void setDishPic(String dishPic) {
        this.dishPic = dishPic == null ? null : dishPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dishes
     *
     * @mbggenerated Thu Feb 24 15:34:35 CST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dishId=").append(dishId);
        sb.append(", dishName=").append(dishName);
        sb.append(", price=").append(price);
        sb.append(", intro=").append(intro);
        sb.append(", detail=").append(detail);
        sb.append(", type=").append(type);
        sb.append(", dishPic=").append(dishPic);
        sb.append("]");
        return sb.toString();
    }
}