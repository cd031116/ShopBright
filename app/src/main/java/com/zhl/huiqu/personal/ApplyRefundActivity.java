package com.zhl.huiqu.personal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import org.aisen.android.support.inject.ViewInject;

/**
 * 申请退款
 * Created by dw on 2017/8/11.
 */

public class ApplyRefundActivity extends BaseActivity implements RefundLisener {
    @ViewInject(id = R.id.refund_fragment)
    FrameLayout frameLayout;
    @ViewInject(id = R.id.order_detail_red1)
    TextView order_detail_red1;
    @ViewInject(id = R.id.order_detail_red2)
    TextView order_detail_red2;
    @ViewInject(id = R.id.order_detail_success)
    TextView order_detail_red3;

    RefundApplyFragment refundApplyFragment = new RefundApplyFragment();
    RefundingFragment refundingFragment = new RefundingFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_refund;
    }

    @Override
    public void initView() {
        super.initView();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.refund_fragment, refundApplyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onFragmentInteraction(String tag) {
        if ("refundApplyFragment".equals(tag)) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            hindAllFragment(fragmentTransaction);
            fragmentTransaction.replace(R.id.refund_fragment, refundingFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Drawable drawable = getResources().getDrawable(R.drawable.order_detail_red2);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            order_detail_red2.setCompoundDrawables(null, drawable, null, null);
        }
        if ("refundingFragment".equals(tag)){
//            Drawable drawable = getResources().getDrawable(R.drawable.order_detail_red_gou);
//            // 这一步必须要做,否则不会显示.
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
//                    drawable.getMinimumHeight());
//            order_detail_red3.setCompoundDrawables(null, drawable, null, null);
        }
    }
}
