package com.zhl.huiqu.personal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class OrderTeam implements Serializable{
    private String orderSn;
    private String orderId;
    private String memberId;
    private String productId;
    private String productName;
    private String duration;
    private String startCityName;
    private String desCityName;
    private String departureTime;
    private String returnTime;
    private String userName;
    private String userMobile;
    private String userCard;
    private String adultCount;
    private String adultTicketPrice;
    private String adultTicketCount;

    private String childCount;
    private String childTicketPrice;
    private String childTicketCount;
    private String roomChargePrice;
    private String insurancePriceCount;

    private String insuranceIds;
    private String amoutPrice;
    private String payedPrice;
    private String contactIds;
    private String tradeCode;
    private String payTime;
    private String payWay;
    private String outTradeCode;
    private String status;

    private String isDelete;

    private String addTime;
    private String upTime;
    private List<OrderContect> contactInfo;
    private List<OrderInsuran> insuranceInfo;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartCityName() {
        return startCityName;
    }

    public void setStartCityName(String startCityName) {
        this.startCityName = startCityName;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
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

    public String getAdultTicketCount() {
        return adultTicketCount;
    }

    public void setAdultTicketCount(String adultTicketCount) {
        this.adultTicketCount = adultTicketCount;
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

    public String getChildTicketCount() {
        return childTicketCount;
    }

    public void setChildTicketCount(String childTicketCount) {
        this.childTicketCount = childTicketCount;
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

    public String getInsuranceIds() {
        return insuranceIds;
    }

    public void setInsuranceIds(String insuranceIds) {
        this.insuranceIds = insuranceIds;
    }

    public String getAmoutPrice() {
        return amoutPrice;
    }

    public void setAmoutPrice(String amoutPrice) {
        this.amoutPrice = amoutPrice;
    }

    public String getPayedPrice() {
        return payedPrice;
    }

    public void setPayedPrice(String payedPrice) {
        this.payedPrice = payedPrice;
    }

    public String getContactIds() {
        return contactIds;
    }

    public void setContactIds(String contactIds) {
        this.contactIds = contactIds;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOutTradeCode() {
        return outTradeCode;
    }

    public void setOutTradeCode(String outTradeCode) {
        this.outTradeCode = outTradeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public List<OrderContect> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<OrderContect> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<OrderInsuran> getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(List<OrderInsuran> insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }
}
