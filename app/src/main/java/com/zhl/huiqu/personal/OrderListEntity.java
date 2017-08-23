package com.zhl.huiqu.personal;

import com.zhl.huiqu.bean.ASResultBean;
import com.zhl.huiqu.personal.bean.OrderEntity;

import java.util.List;

/**
 * Created by dw on 2017/8/14.
 */

public class OrderListEntity extends ASResultBean{

    private List<OrderEntity> result;
    private int total;

    public List<OrderEntity> getResult() {
        return result;
    }

    public void setResult(List<OrderEntity> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
