package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SearBase extends BaseInfo{

 private List<SearchInfo> body;

    public List<SearchInfo> getBody() {
        return body;
    }

    public void setBody(List<SearchInfo> body) {
        this.body = body;
    }
}
