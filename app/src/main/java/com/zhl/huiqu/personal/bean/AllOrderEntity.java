package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/23.
 */

public class AllOrderEntity implements Serializable {


    /**
     * order_id : 1913
     * order_sn : 170850032250627054
     * name : 鼋头渚家庭休闲卡三人卡
     * price : 325.00
     * num : 1
     * use_date : 2017-08-24
     * use_name : 吴云成
     * pay_way :
     * add_time : 1503386570
     * mobile : 15111287366
     * order_total : 325.00
     * status : 0
     */

    private int order_id;
    private String order_sn;
    private String name;
    private String price;
    private int num;
    private String use_date;
    private String use_name;
    private String pay_way;
    private int add_time;
    private String mobile;
    private String order_total;
    private int status;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
