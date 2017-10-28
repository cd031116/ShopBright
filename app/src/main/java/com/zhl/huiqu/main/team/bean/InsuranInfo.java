package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class InsuranInfo implements Serializable{
    private String productName;
    private String departDate;
    private String adultCount;
    private String childCount;
    private String orderCount;

    private List<InsuranData> insuranceInfo;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public List<InsuranData> getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(List<InsuranData> insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }
}
