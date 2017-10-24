package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TickBase extends BaseInfo{

    private   TickListInfo body;

    public TickListInfo getBody() {
        return body;
    }

    public void setBody(TickListInfo body) {
        this.body = body;
    }
}
