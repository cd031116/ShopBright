package com.zhl.huiqu.sdk.eventbus;

/**
 * Created by Administrator on 2017/8/22.
 */

public class TickSearchEvent{
    private String order;
    private String theme_id;
    private String grade;

    public TickSearchEvent(String order, String theme_id, String grade) {
        this.order = order;
        this.theme_id = theme_id;
        this.grade = grade;
    }


    public String getOrder() {
        return order;
    }

    public String getTheme_id() {
        return theme_id;
    }

    public String getGrade() {
        return grade;
    }

}
