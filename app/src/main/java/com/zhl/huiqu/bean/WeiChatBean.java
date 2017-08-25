package com.zhl.huiqu.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */

public class WeiChatBean extends BaseInfo {
    private  BRewardBean body;

    public BRewardBean getBody() {
        return body;
    }

    public void setBody(BRewardBean body) {
        this.body = body;
    }
}
