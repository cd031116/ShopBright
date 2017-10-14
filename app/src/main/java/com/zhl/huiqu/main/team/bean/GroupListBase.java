package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.bean.ASResultBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class GroupListBase extends ASResultBean {

    private List<TeamListInfo> body;

    public List<TeamListInfo> getBody() {
        return body;
    }

    public void setBody(List<TeamListInfo> body) {
        this.body = body;
    }
}
