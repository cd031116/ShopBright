package com.zhl.huiqu.main;

import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.bean.DitalTickList;
import com.zhl.huiqu.widget.RushBuyCountDownTimerView;

import butterknife.Bind;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.count_down)
    RushBuyCountDownTimerView count_down;

    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.image_zfb)
    ImageView image_zfb;
    @Bind(R.id.iamge_wechat)
    ImageView iamge_wechat;
private int select=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        super.initView();
        DitalTickList mPerson = (DitalTickList) getIntent().getSerializableExtra("pay");
        if (mPerson != null) {
            price.setText(mPerson.getShop_price());
            long time=1000* 60*30;
            count_down.setTime(time);
            count_down.start();
        }
        top_title.setText("订单支付");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.top_left, R.id.zfb_rela, R.id.wechat_rela})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                PayActivity.this.finish();
                break;
            case R.id.zfb_rela:
                if(select==1){
                    return;
                }
                select=1;
                setview(select);
                break;
            case R.id.wechat_rela:
                if(select==2){
                    return;
                }
                select=2;
                setview(select);
                break;
        }
    }


    private void setview(int index){
        image_zfb.setImageResource(R.drawable.order_pay_gray_gou);
        iamge_wechat.setImageResource(R.drawable.order_pay_gray_gou);
        if(index==1){
            image_zfb.setImageResource(R.drawable.order_pay_red_gou);
        }else {
            iamge_wechat.setImageResource(R.drawable.order_pay_red_gou);
        }
    }

}
