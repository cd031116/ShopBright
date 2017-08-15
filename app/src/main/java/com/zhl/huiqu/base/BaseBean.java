package com.zhl.huiqu.base;

import java.io.Serializable;

/**
 * Created by wangdan on 17/2/12.
 */

public class BaseBean implements Serializable {

    private static final long serialVersionUID = -7956466350732983639L;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
