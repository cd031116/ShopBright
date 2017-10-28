package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class OrderCountInfo implements Serializable{
    private String orderCount;
    private String roomChargePrice;
    private String adultOrderCount;
    private String childOrderCount;
    private   OrderInsuran insuranceInfo;

    public OrderInsuran getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(OrderInsuran insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getRoomChargePrice() {
        return roomChargePrice;
    }

    public void setRoomChargePrice(String roomChargePrice) {
        this.roomChargePrice = roomChargePrice;
    }

    public String getAdultOrderCount() {
        return adultOrderCount;
    }

    public void setAdultOrderCount(String adultOrderCount) {
        this.adultOrderCount = adultOrderCount;
    }

    public String getChildOrderCount() {
        return childOrderCount;
    }

    public void setChildOrderCount(String childOrderCount) {
        this.childOrderCount = childOrderCount;
    }
}
