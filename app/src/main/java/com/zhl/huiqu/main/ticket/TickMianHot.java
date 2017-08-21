package com.zhl.huiqu.main.ticket;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/19.
 */

public class TickMianHot implements Serializable{
    private String shop_spot_id;
    private String title;
    private String shop_price;
    private String thumb;
    private String comment_num;
    private String csr;

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

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
