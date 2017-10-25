package com.zhl.huiqu.main.team;

import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.team.bean.TeamOrderPriceBean;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamOrderActivity extends BaseActivity {

    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.outing_time)
    TextView outing_time;
    @Bind(R.id.outing_num)
    TextView outing_num;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_order;
    }

    @Override
    public void initView() {
        super.initView();
        TeamOrderPriceBean teamOrderPriceBean = (TeamOrderPriceBean) getIntent().getSerializableExtra("t_order_price");
        desc.setText(teamOrderPriceBean.getProductTitle());
        outing_time.setText(teamOrderPriceBean.getProductTime());
        if (teamOrderPriceBean.getProductChildNum() != 0)
            outing_num.setText("成人：" + teamOrderPriceBean.getProductAdultNum() + "人,儿童:" + teamOrderPriceBean.getProductChildNum() + "人");
        else
            outing_num.setText("成人：" + teamOrderPriceBean.getProductAdultNum() + "人");
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.top_main})
    void onclicj(View v) {
        switch (v.getId()) {
            case R.id.top_main:
                TeamOrderActivity.this.finish();
                break;
        }
    }
}
