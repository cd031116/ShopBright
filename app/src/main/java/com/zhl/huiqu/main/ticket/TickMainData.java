package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.main.bean.TickActiveInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class TickMainData implements Serializable{
    private List<TickActiveInfo> nav;

    private List<Model> theme;

    private List<TickMianPro> promoteInfo;

    private List<TickMianHot> hot;

    private List<TickCircum> around;

    public List<TickActiveInfo> getNav() {
        return nav;
    }

    public void setNav(List<TickActiveInfo> nav) {
        this.nav = nav;
    }

    public List<Model> getTheme() {
        return theme;
    }

    public void setTheme(List<Model> theme) {
        this.theme = theme;
    }

    public List<TickMianPro> getPromoteInfo() {
        return promoteInfo;
    }

    public void setPromoteInfo(List<TickMianPro> promoteInfo) {
        this.promoteInfo = promoteInfo;
    }

    public List<TickMianHot> getHot() {
        return hot;
    }

    public void setHot(List<TickMianHot> hot) {
        this.hot = hot;
    }

    public List<TickCircum> getAround() {
        return around;
    }

    public void setAround(List<TickCircum> around) {
        this.around = around;
    }
}
