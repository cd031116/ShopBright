package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.ASResultbean;
import com.zhl.huiqu.main.ticket.TickInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class SearchEntity extends ASResultbean {
    private List<SearchInfo> body;

    public List<SearchInfo> getBody() {
        return body;
    }

    public void setBody(List<SearchInfo> body) {
        this.body = body;
    }
}
