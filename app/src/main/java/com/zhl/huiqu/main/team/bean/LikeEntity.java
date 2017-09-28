package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LikeEntity extends BaseBean {

    private List<LikeBean> body;

    public List<LikeBean> getBody() {
        return body;
    }

    public void setBody(List<LikeBean> body) {
        this.body = body;
    }
}
