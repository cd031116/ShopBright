package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TeamPriceBean implements Serializable{

    /**
     * year : 2017
     * month : 11
     * day : 08
     * ticketCount : 20
     * ticketPrice : 3306
     * childTicketPrice : 935
     * roomChargeprice : 540
     */

    private String year;
    private String month;
    private String day;
    private int ticketCount;
    private int ticketPrice;
    private int childTicketPrice;
    private int roomChargeprice;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getChildTicketPrice() {
        return childTicketPrice;
    }

    public void setChildTicketPrice(int childTicketPrice) {
        this.childTicketPrice = childTicketPrice;
    }

    public int getRoomChargeprice() {
        return roomChargeprice;
    }

    public void setRoomChargeprice(int roomChargeprice) {
        this.roomChargeprice = roomChargeprice;
    }
}
