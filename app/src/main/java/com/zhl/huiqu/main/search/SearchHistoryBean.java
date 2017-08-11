package com.zhl.huiqu.main.search;

import org.aisen.android.component.orm.annotation.PrimaryKey;

import java.io.Serializable;

/**
 * Created by wangdan on 17/2/19.
 */

public class SearchHistoryBean implements Serializable {

    private static final long serialVersionUID = -7285299875729249248L;

    @PrimaryKey(column = "text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
