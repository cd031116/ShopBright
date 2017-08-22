package com.zhl.huiqu.sdk.eventbus;

/**
 * Created by Administrator on 2017/8/22.
 */

public class TickSearchEvent{
    private String select;
    private String theme_id;
    private String grade;

    public TickSearchEvent(String select, String theme_id, String grade) {
        this.select = select;
        this.theme_id = theme_id;
        this.grade = grade;
    }

    public String getSelect() {
        return select;
    }


    public String getTheme_id() {
        return theme_id;
    }

    public String getGrade() {
        return grade;
    }

}
