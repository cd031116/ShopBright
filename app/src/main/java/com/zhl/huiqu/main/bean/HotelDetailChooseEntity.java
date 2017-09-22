package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HotelDetailChooseEntity implements Serializable{
    private String name;
    private String content;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
