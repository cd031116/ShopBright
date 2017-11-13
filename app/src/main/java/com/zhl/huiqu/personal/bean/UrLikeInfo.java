package com.zhl.huiqu.personal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */

public class UrLikeInfo implements Serializable{

    List<CollectTick> ticket;

    List<UrLikeTeam> team;

    public List<CollectTick> getTicket() {
        return ticket;
    }

    public void setTicket(List<CollectTick> ticket) {
        this.ticket = ticket;
    }

    public List<UrLikeTeam> getTeam() {
        return team;
    }

    public void setTeam(List<UrLikeTeam> team) {
        this.team = team;
    }
}
