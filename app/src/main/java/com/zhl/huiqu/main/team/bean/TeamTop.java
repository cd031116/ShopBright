package com.zhl.huiqu.main.team.bean;

import com.zhl.huiqu.main.bean.HotInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamTop implements Serializable{
    private List<CityList> destination;

    private List<TeamHot> hot;
    private List<TeamImage> img;
    public List<CityList> getDestination() {
        return destination;
    }

    public void setDestination(List<CityList> destination) {
        this.destination = destination;
    }

    public List<TeamHot> getHot() {
        return hot;
    }

    public void setHot(List<TeamHot> hot) {
        this.hot = hot;
    }

    public List<TeamImage> getImg() {
        return img;
    }

    public void setImg(List<TeamImage> img) {
        this.img = img;
    }
}
