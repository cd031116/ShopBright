package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/23.
 */

public class AllOrderEntity implements Serializable {


    /**
     * order_id : 1894
     * status : 0
     * order_sn : AS1708480322488659
     * product_name : 鼋头渚家庭休闲卡三人卡
     * product_num : 1
     * total : 325.00
     * use_date : 2017-08-24
     */

    private int order_id;
    private int status;
    private String order_sn;
    private String product_name;
    private int product_num;
    private String total;
    private String use_date;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }
}
