package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.bean.ASResultBean;

import org.aisen.android.network.biz.IResult;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class TeamSearchInfo extends ASResultBean {

    private List<TeamListInfo> body;

    public List<TeamListInfo> getBody() {
        return body;
    }

    public void setBody(List<TeamListInfo> body) {
        this.body = body;
    }
}
