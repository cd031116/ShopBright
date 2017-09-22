package com.zhl.huiqu.main.hotelTour;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
 * 酒店，景点选择页
 * Created by Administrator on 2017/9/22.
 */

public class ChooseHotelTourActivity extends BaseActivity {


    @Bind(R.id.choose_recy)
    RecyclerView chooseRecy;

    @Override
    protected int getLayoutId() {
        return R.layout.hoteldetail_choose_layout;
    }

    @Override
    public void initView() {
        super.initView();
        setRecy();
    }

    /**
     *
     */
    private void setRecy() {
        List<HotelTourTcChooseEntity> list = new ArrayList<>();
        HotelTourTcChooseEntity hotelTourTcChooseEntity = new HotelTourTcChooseEntity();
        hotelTourTcChooseEntity.setPl_num("54");
        hotelTourTcChooseEntity.setAddress("地址：长沙天心区政府");
        hotelTourTcChooseEntity.setChooseName("zhl");
        hotelTourTcChooseEntity.setImg("http://wechats.zhonghuilv.net/uploads/news/20170816/4182a75d53f3840d4603f5182f4b1336.jpg");
        list.add(hotelTourTcChooseEntity);
        list.add(hotelTourTcChooseEntity);
        HotelTourTcChooseAdapter adapter = new HotelTourTcChooseAdapter(this, list);
        chooseRecy.setLayoutManager(new MyLinearLayoutManager(this));
        chooseRecy.setAdapter(adapter);
        adapter.setOnItemClickListener(new HotelTourTcChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @OnClick({R.id.head_layout})
    void onClick() {
        finish();
    }

}
