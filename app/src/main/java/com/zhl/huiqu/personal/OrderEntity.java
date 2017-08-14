package com.zhl.huiqu.personal;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/14.
 */

public class OrderEntity implements Serializable {

    String ticketState;
    String ticketWhere;
    String ticketType;
    int ticketNum;
    int ticketPrice;
    String ticketTime;
    String orderNum;

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public String getTicketWhere() {
        return ticketWhere;
    }

    public void setTicketWhere(String ticketWhere) {
        this.ticketWhere = ticketWhere;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
