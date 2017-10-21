package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */

public class GoalInfo implements Serializable{

    private List<GoalProInfo> province;

    private GoalHot thumb;

    private List<GoalHot> city;

    public List<GoalProInfo> getProvince() {
        return province;
    }

    public void setProvince(List<GoalProInfo> province) {
        this.province = province;
    }

    public GoalHot getThumb() {
        return thumb;
    }

    public void setThumb(GoalHot thumb) {
        this.thumb = thumb;
    }

    public List<GoalHot> getCity() {
        return city;
    }

    public void setCity(List<GoalHot> city) {
        this.city = city;
    }
}
