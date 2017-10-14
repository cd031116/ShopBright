package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class FilterInfo implements Serializable{

    private List<FilterTheme> theme;
    private List<FilterDest> destination;

    public List<FilterTheme> getTheme() {
        return theme;
    }

    public void setTheme(List<FilterTheme> theme) {
        this.theme = theme;
    }

    public List<FilterDest> getDestination() {
        return destination;
    }

    public void setDestination(List<FilterDest> destination) {
        this.destination = destination;
    }
}
