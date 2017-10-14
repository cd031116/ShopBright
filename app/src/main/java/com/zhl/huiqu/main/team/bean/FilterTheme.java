package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */

public class FilterTheme implements Serializable{

    private String team_attr_id;
    private String name;
    private  boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTeam_attr_id() {
        return team_attr_id;
    }

    public void setTeam_attr_id(String team_attr_id) {
        this.team_attr_id = team_attr_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
