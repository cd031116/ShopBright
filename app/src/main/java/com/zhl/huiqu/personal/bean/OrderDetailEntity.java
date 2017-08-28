package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/26.
 */

public class OrderDetailEntity implements Serializable{

    /**
     * order_id : 1952
     * ticket_id : 0
     * order_sn : 170833111433126980
     * name : 【石牛寨套票】石牛寨大门票+玻璃桥+博物馆+缆车上行票
     * price : 0.01
     * num : 1
     * use_date : 2017-8-27
     * use_name : 刘大神
     * pay_way : 微信支付
     * add_time : 1503717273
     * mobile : 18658858468
     * order_total : 0.01
     * status : 1
     * spot_name : 石燕湖生态旅游景区
     * take : 至景区售票处换取门票入园
     */

    private int order_id;
    private int ticket_id;
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
    private String spot_name;
    private String take;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
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

    public String getSpot_name() {
        return spot_name;
    }

    public void setSpot_name(String spot_name) {
        this.spot_name = spot_name;
    }

    public String getTake() {
        return take;
    }

    public void setTake(String take) {
        this.take = take;
    }
}
