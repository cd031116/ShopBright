package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/22.
 */

public class OrderEntity implements Serializable{

    /**
     * order_sn : AS1708040259045108
     * product_num : 50
     * product_name : 鼋头渚家庭休闲卡三人卡
     * use_date : 2017-08-27
     * use_name : wyc11
     * pay_way :
     * add_time : 1503385144
     * mobile : 18569502645
     * total : 16250.00
     * status : 0
     * spot_name : 鼋头渚
     * take : 至景区售票处（鼋头渚景区充山大门）携带预订时的身份证换取门票入园，首次去需要将3人的照片印在卡上，后面只能这3人使用），先去网络窗口拿拍照凭证，然后去游客中心拍照然后去休闲卡中心做卡
     */

    private String order_sn;
    private int product_num;
    private String product_name;
    private String use_date;
    private String use_name;
    private String pay_way;
    private int add_time;
    private String mobile;
    private String total;
    private int status;
    private String spot_name;
    private String take;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
