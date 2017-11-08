package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.personal.bean.OrderTeam;

/**
 * Created by Administrator on 2017/11/8.
 */

public class OrderDetailBase extends BaseInfo{

    private OrderTeam body;

    public OrderTeam getBody() {
        return body;
    }

    public void setBody(OrderTeam body) {
        this.body = body;
    }
}
