package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SpotTBean extends BaseInfo{

    private List<SpotThemeInfo> body;

    public List<SpotThemeInfo> getBody() {
        return body;
    }

    public void setBody(List<SpotThemeInfo> body) {
        this.body = body;
    }
}
