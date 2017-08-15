package com.zhl.huiqu.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyj on 2017/8/15.
 */

public class MainTopInfo implements Serializable{

   private List<HotelInfo>  hotel4;

    private List<TicketsInfo>  ticket4;

    private List<HotInfo>  hot3;


    public List<HotelInfo> getHotel4() {
        return hotel4;
    }

    public void setHotel4(List<HotelInfo> hotel4) {
        this.hotel4 = hotel4;
    }

    public List<TicketsInfo> getTicket4() {
        return ticket4;
    }

    public void setTicket4(List<TicketsInfo> ticket4) {
        this.ticket4 = ticket4;
    }

    public List<HotInfo> getHot3() {
        return hot3;
    }

    public void setHot3(List<HotInfo> hot3) {
        this.hot3 = hot3;
    }
}
