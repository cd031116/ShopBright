package com.zhl.huiqu.personal;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by dw on 2017/8/11.
 */

public class OrderDetailActivity extends BaseActivity {
    //取票时间
    @Bind(R.id.take_ticket_text)
    TextView takeTicketText;
    //成人票
    @Bind(R.id.ticket_type_text)
    TextView ticketTypeText;
    //出游时间
    @Bind(R.id.ticket_outing_time_text)
    TextView ticketOutingTimeText;
    //门票数量
    @Bind(R.id.ticket_num_text)
    TextView ticketNumText;
    //入园方式
    @Bind(R.id.ticket_in_type_text)
    TextView ticketInTypeText;
    //价格
    @Bind(R.id.ticket_price)
    TextView ticketPriceText;
    //优惠券
    @Bind(R.id.yhq_using_text)
    TextView yhq_using_text;
    //总价
    @Bind(R.id.price_all_text)
    TextView price_all_text;
    //退订规则
    @Bind(R.id.refund_layout_text)
    TextView refund_layout_text;
    //订单号
    @Bind(R.id.refund_order_num_text)
    TextView refund_order_num_text;
    //下单方式
    @Bind(R.id.refund_time_text)
    TextView refund_time_text;
    //下单方式
    @Bind(R.id.refund_pay_type_text)
    TextView refund_pay_type_text;
    //下单方式
    @Bind(R.id.order_account_name_text)
    TextView order_account_name_text;
    //手机号
    @Bind(R.id.order_account_phone_text)
    TextView order_account_phone_text;
    @Bind(R.id.top_title)
    TextView top_title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText(getResources().getString(R.string.order_detail));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.apply_refund_btn, R.id.top_image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_refund_btn:
                startActivity(new Intent(this, ApplyRefundActivity.class));
                break;
            case R.id.top_image:
                finish();
                break;
        }
    }
}
