package com.zhl.huiqu.main.team;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import com.zhl.huiqu.main.team.bean.TeamListInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class GroupListInfo implements Serializable {

    @JSONField(name="package")
    private List<TeamListInfo> data;

    public List<TeamListInfo> getData() {
        return data;
    }

    public void setData(List<TeamListInfo> data) {
        this.data = data;
    }
}
