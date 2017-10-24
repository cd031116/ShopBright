package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TeamPriceEntity extends BaseBean {
    private List<TeamPriceBean> body;

    public List<TeamPriceBean> getBody() {
        return body;
    }

    public void setBody(List<TeamPriceBean> body) {
        this.body = body;
    }
}
