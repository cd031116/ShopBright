package com.zhl.huiqu.personal.bean;

import com.zhl.huiqu.bean.ASResultBean;

import java.util.List;

/**
 * Created by dw on 2017/8/23.
 */

public class CollectBean extends ASResultBean {

    List<CollectEntity> body;

        public List<CollectEntity> getBody() {
        return body;
    }

    public void setBody(List<CollectEntity> body) {
        this.body = body;
    }
}
