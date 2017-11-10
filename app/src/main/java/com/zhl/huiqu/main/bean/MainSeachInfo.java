package com.zhl.huiqu.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MainSeachInfo implements Serializable{

private List<MainTeam> team;

private List<MainTick> ticket;

    public List<MainTeam> getTeam() {
        return team;
    }

    public void setTeam(List<MainTeam> team) {
        this.team = team;
    }

    public List<MainTick> getTicket() {
        return ticket;
    }

    public void setTicket(List<MainTick> ticket) {
        this.ticket = ticket;
    }
}
