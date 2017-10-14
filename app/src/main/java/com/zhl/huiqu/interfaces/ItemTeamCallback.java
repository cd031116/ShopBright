package com.zhl.huiqu.interfaces;

import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.main.team.bean.TeamListInfo;

/**
 * Created by Administrator on 2017/9/29.
 */

public interface ItemTeamCallback {

    void onClickItemBean(TeamListInfo bean, int position);
}
