package com.zhl.huiqu.personal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by dw on 2017/8/11.
 */

public class OrderDetailActivity extends BaseActivity {
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
    }

    @Override
    public void initData() {
        super.initData();
        String orderState = getIntent().getStringExtra("order_state");
        Log.e("tttt", "initData: " + orderState);
        showDetailView(orderState);
    }

    private void showDetailView(String orderState) {
        if (getResources().getString(R.string.personal_pay_order).equals(orderState)) {
            changeFragment(orderPayFragment, R.drawable.order_detail_gray3, R.color.gray_text);
        } else if (getResources().getString(R.string.personal_out_order).equals(orderState)) {
            changeFragment(orderGoOutFragment, R.drawable.order_detail_red3, R.color.red_text);
        }
    }

    private void changeFragment(Fragment fragment, int drawableId, int colorId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.order_detail_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        SupportMultipleScreensUtil.scaleDrawableBounds(drawable);
        Log.e("ttt", "changeFragment: ");
        order_detail_red3.setCompoundDrawables(null, drawable, null, null);
        view_line2.setBackgroundResource(colorId);
    }

    @OnClick({R.id.top_image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_image:
                finish();
                break;
        }
    }

}
