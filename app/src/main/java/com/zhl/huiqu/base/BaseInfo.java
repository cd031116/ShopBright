package com.zhl.huiqu.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16.
 */

public class BaseInfo implements Serializable {

    private static final long serialVersionUID = -7956466350732983639L;

    private String code;

    private String msg;

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
}
