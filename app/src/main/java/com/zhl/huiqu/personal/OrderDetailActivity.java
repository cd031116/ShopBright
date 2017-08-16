package com.zhl.huiqu.personal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by dw on 2017/8/11.
 */

public class OrderDetailActivity extends BaseActivity implements RefundLisener {
    @Bind(R.id.order_detail_red2)
    TextView order_detail_red2;
    @Bind(R.id.view_line2)
    View view_line2;
    @Bind(R.id.take_ticket_text)
    TextView takeTicketText;
    @Bind(R.id.order_detail_red3)
    TextView order_detail_red3;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.order_detail_frame)
    FrameLayout fragment;

    OrderPayFragment orderPayFragment = new OrderPayFragment();
    OrderGoOutFragment orderGoOutFragment = new OrderGoOutFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText(getResources().getString(R.string.order_detail));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.order_detail_frame, orderPayFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({ R.id.top_image})
    void onClick(View view) {
        switch (view.getId()) {
//            case R.id.apply_refund_btn:
//                startActivity(new Intent(this, ApplyRefundActivity.class));
//                break;
            case R.id.top_image:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(String tag) {
        if ("orderPayFragment".equals(tag)) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            hindAllFragment(fragmentTransaction);
            fragmentTransaction.replace(R.id.order_detail_frame, orderGoOutFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Drawable drawable = getResources().getDrawable(R.drawable.order_detail_red3);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            order_detail_red3.setCompoundDrawables(null, drawable, null, null);
            view_line2.setBackgroundResource(R.color.red_text);
        }
    }
}
