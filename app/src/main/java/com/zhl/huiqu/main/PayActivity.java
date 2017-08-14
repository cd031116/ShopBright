package com.zhl.huiqu.main;

import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.widget.RushBuyCountDownTimerView;

import butterknife.Bind;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;

    @Bind(R.id.count_down)
    RushBuyCountDownTimerView count_down;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText("订单支付");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.top_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                PayActivity.this.finish();
                break;
        }
    }




}
