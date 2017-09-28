package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.main.bean.HotInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamTop implements Serializable{
    private List<CityList> city_list;

    private List<HotInfo> hot;

    public List<CityList> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<CityList> city_list) {
        this.city_list = city_list;
    }

    public List<HotInfo> getHot() {
        return hot;
    }

    public void setHot(List<HotInfo> hot) {
        this.hot = hot;
    }
}
