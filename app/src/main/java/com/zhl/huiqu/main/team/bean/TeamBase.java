package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamBase extends BaseInfo{
    private List<TeamMainList> body;

    public List<TeamMainList> getBody() {
        return body;
    }

    public void setBody(List<TeamMainList> body) {
        this.body = body;
    }
}
