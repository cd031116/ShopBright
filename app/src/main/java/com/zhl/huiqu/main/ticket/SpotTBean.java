package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SpotTBean extends BaseInfo{

    private List<SpotThemeInfo> data;

    public List<SpotThemeInfo> getData() {
        return data;
    }

    public void setData(List<SpotThemeInfo> data) {
        this.data = data;
    }
}
