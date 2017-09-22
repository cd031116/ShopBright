package com.zhl.huiqu.main.hotelTour;

import android.support.v7.widget.RecyclerView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.bean.HotelTourTcChooseEntity;
import com.zhl.huiqu.main.hotelTour.adapter.HotelTourTcChooseAdapter;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 酒店，景点选择列表
 * Created by Administrator on 2017/9/22.
 */

public class HotelTourTcChooseActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.hoteltour_order_layout;
    }

    @Override
    public void initView() {
        super.initView();
    }



    @OnClick({R.id.head_layout})
    void onClick() {
        finish();
    }
}
