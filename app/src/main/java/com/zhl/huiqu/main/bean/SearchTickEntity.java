package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.ASResultbean;
import com.zhl.huiqu.main.ticket.TickInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class SearchTickEntity extends ASResultbean {
    private List<TickInfo> body;

    public List<TickInfo> getBody() {
        return body;
    }

    public void setBody(List<TickInfo> body) {
        this.body = body;
    }
}