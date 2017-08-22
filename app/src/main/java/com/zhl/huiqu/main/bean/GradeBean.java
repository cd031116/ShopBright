package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GradeBean extends BaseInfo{
    private List<GradeInfo> data;

    public List<GradeInfo> getData() {
        return data;
    }

    public void setData(List<GradeInfo> data) {
        this.data = data;
    }
}
