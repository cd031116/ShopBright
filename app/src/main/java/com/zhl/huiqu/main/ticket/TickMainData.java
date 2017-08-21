package com.zhl.huiqu.main.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class TickMainData implements Serializable{


    private TickMianTabItem ticket_before;

    private TickMianPro promoteInfo;

    private List<TickMianHot> hot;

    private List<TickCircum> around;

    public TickMianTabItem getTicket_before() {
        return ticket_before;
    }

    public void setTicket_before(TickMianTabItem ticket_before) {
        this.ticket_before = ticket_before;
    }

    public TickMianPro getPromoteInfo() {
        return promoteInfo;
    }

    public void setPromoteInfo(TickMianPro promoteInfo) {
        this.promoteInfo = promoteInfo;
    }

    public List<TickMianHot> getHot() {
        return hot;
    }

    public void setHot(List<TickMianHot> hot) {
        this.hot = hot;
    }

    public List<TickCircum> getAround() {
        return around;
    }

    public void setAround(List<TickCircum> around) {
        this.around = around;
    }
}
