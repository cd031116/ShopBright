package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class UrLikeBean extends BaseInfo {

    private List<UrLikeEntity> body;

    public List<UrLikeEntity> getBody() {
        return body;
    }

    public void setBody(List<UrLikeEntity> body) {
        this.body = body;
    }
}

