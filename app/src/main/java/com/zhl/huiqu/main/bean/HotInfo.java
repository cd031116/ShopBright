package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class HotInfo implements Serializable {

    private String shop_spot_id;
    private String title;
    private String thumb;

    public String getShop_spot_id() {
        return shop_spot_id;
    }

    public void setShop_spot_id(String shop_spot_id) {
        this.shop_spot_id = shop_spot_id;
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
