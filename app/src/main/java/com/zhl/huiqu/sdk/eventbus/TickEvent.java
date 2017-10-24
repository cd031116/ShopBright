package com.zhl.huiqu.sdk.eventbus;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TickEvent {
    private String gradeId;
    private String themeId;

    public TickEvent(String gradeId, String themeId) {
        this.gradeId = gradeId;
        this.themeId = themeId;
    }

    public String getGradeId() {
        return gradeId;
    }


    public String getThemeId() {
        return themeId;
    }

}
