package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.base.ASResultbean;
import com.zhl.huiqu.main.bean.TicketsInfo;

/**
 * Created by Administrator on 2017/8/18.
 */

public class TickBean extends ASResultbean {
    private TickListInfo body;

    public TickListInfo getBody() {
        return body;
    }

    public void setBody(TickListInfo body) {
        this.body = body;
    }
}
