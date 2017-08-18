package com.zhl.huiqu.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.utils.GlideCircleTransform;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.activity.container.FragmentArgs;

/**
 * 个人中心
 * Created by dw on 2017/8/11.
 */

public class PersonalFragment extends BaseFragment {
    //    @ViewInject(id=R.id.row_collect_layout)
//    RelativeLayout myCollect;
//    @ViewInject(id=R.id.row_look_his_layout)
//    RelativeLayout lookHistory;
    @ViewInject(id = R.id.personal_head_img)
    ImageView headImg;
    @ViewInject(id = R.id.personal_tel_text)
    TextView nameText;
    RegisterEntity account;


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

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        account = SaveObjectUtils.getInstance(getActivity()).getObject("account", RegisterEntity.class);
        if (account != null)
            nameText.setText(account.getData().getNickname());
    }

    @OnClick({R.id.row_collect_layout, R.id.row_look_his_layout, R.id.row_normal_msg_layout, R.id.row_kefu_center_layout,
            R.id.refund_order_btn, R.id.goout_order_btn, R.id.pay_order_btn, R.id.all_order_btn, R.id.personal_msg_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.row_kefu_center_layout:
                startActivity(new Intent(getActivity(), KefuCenterActivity.class));
                break;
            case R.id.personal_msg_layout:
                if (account != null)
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            default:
                otherClickEvent(view);
                break;
        }
    }

    private void otherClickEvent(View view) {

        FragmentArgs args = new FragmentArgs();
        if (account == null) {
            ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.should_account_login));
        } else {
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
                //所有订单
                case R.id.all_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_all_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //待付款
                case R.id.pay_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_pay_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //待出行
                case R.id.goout_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_out_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //退款
                case R.id.refund_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_tuikuan_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
            }
        }
    }
}
