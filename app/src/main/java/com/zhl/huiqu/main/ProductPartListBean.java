package com.zhl.huiqu.main;

import com.zhl.huiqu.bean.ASResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ProductPartListBean extends ASResultBean {

    private static final long serialVersionUID = 2267060725486603901L;

    private List<ProductPartBean> result;

    private int total;

    public List<ProductPartBean> getResult() {
        return result;
    }

    public void setResult(List<ProductPartBean> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}