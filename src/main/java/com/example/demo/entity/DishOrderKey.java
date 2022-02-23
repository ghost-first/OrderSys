package com.example.demo.entity;

import java.io.Serializable;

public class DishOrderKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishorder.oid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    private String oid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dishorder.dishid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    private Integer dishid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dishorder
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishorder.oid
     *
     * @return the value of dishorder.oid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    public String getOid() {
        return oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishorder.oid
     *
     * @param oid the value for dishorder.oid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dishorder.dishid
     *
     * @return the value of dishorder.dishid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    public Integer getDishid() {
        return dishid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dishorder.dishid
     *
     * @param dishid the value for dishorder.dishid
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    public void setDishid(Integer dishid) {
        this.dishid = dishid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dishorder
     *
     * @mbggenerated Wed Feb 23 14:22:03 CST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", dishid=").append(dishid);
        sb.append("]");
        return sb.toString();
    }
}