package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/16.
 */

public class MainSpotBean implements Serializable{
    private String shop_spot_id;
    private String title;
    private String thumb;
    private String shop_price;
    private String market_price;
    private String csr;
    private boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

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

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
