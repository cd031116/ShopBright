package com.zhl.huiqu.sdk.eventbus;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TeamEvent{
    private String desCityId;
    private String themeId;

    public TeamEvent(String desCityId, String themeId) {
        this.desCityId = desCityId;
        this.themeId = themeId;
    }

    public String getDesCityId() {
        return desCityId;
    }

    public String getThemeId() {
        return themeId;
    }
}
