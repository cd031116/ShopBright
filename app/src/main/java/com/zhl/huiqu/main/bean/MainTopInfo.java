package com.zhl.huiqu.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyj on 2017/8/15.
 */

public class MainTopInfo implements Serializable{
    private List<BannerInfo> nav;
   private List<HotelInfo>  hotel;

    private List<TicketsInfo>  ticket;

    private List<HotInfo>  hot;
    private List<MainSpotBean>  spot;
    private List<MainTeamBean>  team;

    public List<MainSpotBean> getSpot() {
        return spot;
    }

    public void setSpot(List<MainSpotBean> spot) {
        this.spot = spot;
    }

    public List<MainTeamBean> getTeam() {
        return team;
    }

    public void setTeam(List<MainTeamBean> team) {
        this.team = team;
    }

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

    public List<BannerInfo> getNav() {
        return nav;
    }

    public void setNav(List<BannerInfo> nav) {
        this.nav = nav;
    }
}
