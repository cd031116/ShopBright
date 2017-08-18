package com.zhl.huiqu.main.ticket;


import com.zhl.huiqu.base.ASResultbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class TickListInfo implements Serializable{
    private List<TickInfo> ticketOnly;

//    private int total;

    public List<TickInfo> getTicketOnly() {
        return ticketOnly;
    }

    public void setTicketOnly(List<TickInfo> ticketOnly){
        this.ticketOnly = ticketOnly;
    }

//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
}
