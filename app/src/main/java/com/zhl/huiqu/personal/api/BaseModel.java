package com.zhl.huiqu.personal.api;

import com.zhl.huiqu.main.ticket.TickInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class BaseModel{
    private String code;

    private String msg;

    private ArrayList<TickInfo> body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<TickInfo> getBody() {
        return body;
    }

    public void setBody(ArrayList<TickInfo> body) {
        this.body = body;
    }
}
