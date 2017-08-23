package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.base.BaseInfo;

/**
 * Created by dw on 2017/8/23.
 */

public class OrderBean extends BaseInfo {

    private OrderEntity body;

    public OrderEntity getBody() {
        return body;
    }

    public void setBody(OrderEntity body) {
        this.body = body;
    }
}
