package com.zhl.huiqu.main.bean;

import com.zhl.huiqu.base.ASResultbean;
import com.zhl.huiqu.main.ticket.TickInfo;
import com.zhl.huiqu.main.ticket.TickListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class SearchBean extends ASResultbean {
    private List<TickInfo>  data;

    public List<TickInfo> getData() {
        return data;
    }

    public void setData(List<TickInfo> data) {
        this.data = data;
    }
}
