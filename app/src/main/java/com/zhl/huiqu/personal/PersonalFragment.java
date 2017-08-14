package com.zhl.huiqu.personal;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.main.MainTabFragment;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

import butterknife.Bind;

/**
 * 个人中心
 * Created by dw on 2017/8/11.
 */

public class PersonalFragment extends BaseFragment {
//    @ViewInject(id=R.id.row_collect_layout)
//    RelativeLayout myCollect;
//    @ViewInject(id=R.id.row_look_his_layout)
//    RelativeLayout lookHistory;
//    @ViewInject(id=R.id.row_normal_msg_layout)
//    RelativeLayout normalMsg;
//    @ViewInject(id=R.id.row_kefu_center_layout)
//    RelativeLayout kefuCenter;
//
//    @ViewInject(id=R.id.all_order_btn)
//    TextView allOrderBtn;
//    @ViewInject(id=R.id.pay_order_btn)
//    TextView payBtn;
//    @ViewInject(id=R.id.goout_order_btn)
//    TextView gooutBtn;
//    @ViewInject(id=R.id.refund_order_btn)
//    TextView refundBtn;


    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }


    @Override
    public int inflateContentView() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);
    }

    @OnClick({R.id.row_collect_layout, R.id.row_look_his_layout, R.id.row_normal_msg_layout, R.id.row_kefu_center_layout,
            R.id.refund_order_btn, R.id.goout_order_btn, R.id.pay_order_btn, R.id.all_order_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.row_collect_layout:
                Log.e("dddd", "onClick: row_collect_layout");
                startActivity(new Intent(getActivity(), MyCollectAcitivity.class));
                break;
            case R.id.row_look_his_layout:
                Log.e("dddd", "onClick: row_look_his_layout");
                startActivity(new Intent(getActivity(), LookHistoryActivity.class));
                break;
            case R.id.row_normal_msg_layout:
                break;
            case R.id.row_kefu_center_layout:
                startActivity(new Intent(getActivity(), KefuCenterActivity.class));
                break;
            case R.id.all_order_btn:
                OrderListFragment.launch(getActivity());
                break;
            case R.id.pay_order_btn:
                startActivity(new Intent(getActivity(), OrderDetailActivity.class));
                break;
            case R.id.goout_order_btn:
                break;
            case R.id.refund_order_btn:
                break;
        }
    }
}
