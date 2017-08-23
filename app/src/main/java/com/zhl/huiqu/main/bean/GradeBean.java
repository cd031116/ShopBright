package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GradeBean extends BaseInfo{
    private List<GradeInfo> body;

    public List<GradeInfo> getBody() {
        return body;
    }

    public void setBody(List<GradeInfo> body) {
        this.body = body;
    }
}
