package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.bean.ASResultBean;

import java.util.List;

/**
 * Created by dw on 2017/8/23.
 */

public class AllOrderBean extends ASResultBean {

    List<AllOrderEntity> body;

        public List<AllOrderEntity> getBody() {
        return body;
    }

    public void setBody(List<AllOrderEntity> body) {
        this.body = body;
    }
}
