package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class HotInfo implements Serializable {

    private String id;
    private String title;
    private String thumb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
