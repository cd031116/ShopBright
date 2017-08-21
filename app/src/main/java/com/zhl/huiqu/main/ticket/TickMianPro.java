package com.zhl.huiqu.main.ticket;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/19.
 */

public class TickMianPro implements Serializable{
    private String role_module_id;
    private String type;
    private String icon;

    public String getRole_module_id() {
        return role_module_id;
    }

    public void setRole_module_id(String role_module_id) {
        this.role_module_id = role_module_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
