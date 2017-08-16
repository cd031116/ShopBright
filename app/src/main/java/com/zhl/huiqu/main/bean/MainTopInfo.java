package com.zhl.huiqu.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyj on 2017/8/15.
 */

public class MainTopInfo implements Serializable{

   private List<HotelInfo>  hotel;

    private List<TicketsInfo>  ticket;

    private List<HotInfo>  hot;

    public List<HotelInfo> getHotel() {
        return hotel;
    }

    public void setHotel(List<HotelInfo> hotel) {
        this.hotel = hotel;
    }

    public List<TicketsInfo> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketsInfo> ticket) {
        this.ticket = ticket;
    }

    public List<HotInfo> getHot() {
        return hot;
    }

    public void setHot(List<HotInfo> hot) {
        this.hot = hot;
    }
}
