package com.zhl.huiqu.interfaces;

import com.zhl.huiqu.main.ProductPartBean;

/**
 * Created by Administrator on 2017/9/14.
 */

public interface ItemCallback{

    void onClickItem(int position);

    void onClickItemBean(ProductPartBean bean,int position);
}
