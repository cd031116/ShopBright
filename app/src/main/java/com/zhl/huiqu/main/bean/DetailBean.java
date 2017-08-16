package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class DetailBean implements Serializable{

    private DetailMainInfo spot_info;

    private List<String> thumb_list;

    private List<DitalTickList>  ticket_list;

    public DetailMainInfo getSpot_info() {
        return spot_info;
    }

    public void setSpot_info(DetailMainInfo spot_info) {
        this.spot_info = spot_info;
    }

    public List<String> getThumb_list() {
        return thumb_list;
    }

    public void setThumb_list(List<String> thumb_list) {
        this.thumb_list = thumb_list;
    }

    public List<DitalTickList> getTicket_list() {
        return ticket_list;
    }

    public void setTicket_list(List<DitalTickList> ticket_list) {
        this.ticket_list = ticket_list;
    }
}
