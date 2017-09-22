package com.zhl.huiqu.main.hotelTour;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.OnClick;

/**
 * 酒店，景点详情页
 * Created by Administrator on 2017/9/22.
 */

public class ChooseDetailActivity extends BaseActivity {

    private ChooseHotelFragment hotelFragment;
    private ChooseTourFragment tourFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    public void initView() {
        super.initView();
        String which = getIntent().getStringExtra("which");
        setFragment(which);
    }

    private void setFragment(String which) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if ("hotel".equals(which)) {
            hotelFragment = ChooseHotelFragment.newInstance(1);
            transaction.replace(R.id.fragment,hotelFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if ("tour".equals(which)) {

        }
    }


    @OnClick({R.id.btnBack})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
