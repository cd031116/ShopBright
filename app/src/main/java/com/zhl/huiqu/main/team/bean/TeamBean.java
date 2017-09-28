package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.base.BaseBean;
import com.zhl.huiqu.base.BaseInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamBean implements Serializable {

    private List<TeamListInfo> spot;

    public List<TeamListInfo> getSpot() {
        return spot;
    }

    public void setSpot(List<TeamListInfo> spot) {
        this.spot = spot;
    }
}
