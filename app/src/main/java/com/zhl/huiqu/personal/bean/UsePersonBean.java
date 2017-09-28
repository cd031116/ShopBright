package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class UsePersonBean extends BaseInfo{
    private List<UsePerList> body;

    public List<UsePerList> getBody() {
        return body;
    }

    public void setBody(List<UsePerList> body) {
        this.body = body;
    }
}
