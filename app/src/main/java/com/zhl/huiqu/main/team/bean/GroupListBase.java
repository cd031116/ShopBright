package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.bean.ASResultBean;
import com.zhl.huiqu.main.team.GroupListInfo;

/**
 * Created by Administrator on 2017/9/28.
 */

public class GroupListBase extends ASResultBean {

    private GroupListInfo body;

    public GroupListInfo getBody() {
        return body;
    }

    public void setBody(GroupListInfo body) {
        this.body = body;
    }
}
