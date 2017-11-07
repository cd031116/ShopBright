package com.zhl.huiqu.personal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class AllOrderInfo implements Serializable{
    private List<OrderTick> ticketOrder;
    private List<OrderTeam> teamOrder;

    public List<OrderTick> getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(List<OrderTick> ticketOrder) {
        this.ticketOrder = ticketOrder;
    }

    public List<OrderTeam> getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(List<OrderTeam> teamOrder) {
        this.teamOrder = teamOrder;
    }
}
