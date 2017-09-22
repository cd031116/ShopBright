package com.zhl.huiqu.main.hotelTour;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 自由组合
 * Created by Administrator on 2017/9/22.
 */

public class CombActivity extends BaseActivity {

    @Bind(R.id.change_hotel)
    TextView change_hotel;
    @Bind(R.id.choose_room_type)
    RelativeLayout choose_room_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comb_layout;
    }

    @Override
    public void initView() {
        super.initView();
    }


    @OnClick({R.id.btnBack, R.id.change_hotel, R.id.choose_room_type})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.choose_room_type:
                Intent intent = new Intent(this, ChooseDetailActivity.class);
                intent.putExtra("which","hotel");
                startActivity(intent);
                break;
            case R.id.change_hotel:
                startActivity(new Intent(this, ChooseHotelTourActivity.class));
                break;
        }
    }

}
