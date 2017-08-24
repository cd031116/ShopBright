package com.zhl.huiqu.main.ticket;

import com.zhl.huiqu.base.BaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CityInfo extends BaseInfo{

  private List<CityData> body;

    public List<CityData> getBody() {
        return body;
    }

    public void setBody(List<CityData> body) {
        this.body = body;
    }
}
