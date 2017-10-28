package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class OrderPut implements Serializable{

    private String productId;
    private String memberId;
    private String departDate;
    private  String adultCount;
    private String adultTicketPrice;
    private String childCount;
    private String childTicketPrice;
    private String roomChargePrice;
    private String insurancePriceCount;
    private String insuranceIdList;
    private  String orderCount;
    private String getTicketName;
    private String getTicketMobile;
    private  String getTicketCard;
    private String contactIds;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(String adultCount) {
        this.adultCount = adultCount;
    }

    public String getAdultTicketPrice() {
        return adultTicketPrice;
    }

    public void setAdultTicketPrice(String adultTicketPrice) {
        this.adultTicketPrice = adultTicketPrice;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public String getChildTicketPrice() {
        return childTicketPrice;
    }

    public void setChildTicketPrice(String childTicketPrice) {
        this.childTicketPrice = childTicketPrice;
    }

    public String getRoomChargePrice() {
        return roomChargePrice;
    }

    public void setRoomChargePrice(String roomChargePrice) {
        this.roomChargePrice = roomChargePrice;
    }

    public String getInsurancePriceCount() {
        return insurancePriceCount;
    }

    public void setInsurancePriceCount(String insurancePriceCount) {
        this.insurancePriceCount = insurancePriceCount;
    }

    public String getInsuranceIdList() {
        return insuranceIdList;
    }

    public void setInsuranceIdList(String insuranceIdList) {
        this.insuranceIdList = insuranceIdList;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getGetTicketName() {
        return getTicketName;
    }

    public void setGetTicketName(String getTicketName) {
        this.getTicketName = getTicketName;
    }

    public String getGetTicketMobile() {
        return getTicketMobile;
    }

    public void setGetTicketMobile(String getTicketMobile) {
        this.getTicketMobile = getTicketMobile;
    }

    public String getGetTicketCard() {
        return getTicketCard;
    }

    public void setGetTicketCard(String getTicketCard) {
        this.getTicketCard = getTicketCard;
    }

    public String getContactIds() {
        return contactIds;
    }

    public void setContactIds(String contactIds) {
        this.contactIds = contactIds;
    }
}
