package com.zhl.huiqu.personal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class CollectInfo implements Serializable{

    List<CollectTick> ticket;
    List<ColletTeam> team;

    public List<CollectTick> getTicket() {
        return ticket;
    }

    public void setTicket(List<CollectTick> ticket) {
        this.ticket = ticket;
    }

    public List<ColletTeam> getTeam() {
        return team;
    }

    public void setTeam(List<ColletTeam> team) {
        this.team = team;
    }
}
