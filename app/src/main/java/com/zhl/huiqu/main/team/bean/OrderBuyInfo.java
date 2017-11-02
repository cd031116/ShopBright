package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class OrderBuyInfo implements Serializable{

    private String orderSn;
    private String productName;
    private String adultTicketPrice;
    private String adultTicketCount;
    private String childTicketPrice;
    private String childTicketCount;
    private String amoutPrice;
    private String departureTime;
    private String returnTime;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private List<BuyInsuran> insurance;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdultTicketPrice() {
        return adultTicketPrice;
    }

    public void setAdultTicketPrice(String adultTicketPrice) {
        this.adultTicketPrice = adultTicketPrice;
    }

    public String getAdultTicketCount() {
        return adultTicketCount;
    }

    public void setAdultTicketCount(String adultTicketCount) {
        this.adultTicketCount = adultTicketCount;
    }

    public String getChildTicketPrice() {
        return childTicketPrice;
    }

    public void setChildTicketPrice(String childTicketPrice) {
        this.childTicketPrice = childTicketPrice;
    }

    public String getChildTicketCount() {
        return childTicketCount;
    }

    public void setChildTicketCount(String childTicketCount) {
        this.childTicketCount = childTicketCount;
    }

    public String getAmoutPrice() {
        return amoutPrice;
    }

    public void setAmoutPrice(String amoutPrice) {
        this.amoutPrice = amoutPrice;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public List<BuyInsuran> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<BuyInsuran> insurance) {
        this.insurance = insurance;
    }
}
