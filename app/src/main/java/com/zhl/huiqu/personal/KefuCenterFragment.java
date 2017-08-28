package com.zhl.huiqu.personal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.sdk.permission.CallPhonePermissionAction;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.support.action.IAction;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.activity.container.FragmentArgs;

/**
 * 客服中心
 * Created by dw on 2017/8/11.
 */

public class KefuCenterFragment extends BaseFragment {


    @ViewInject(id = R.id.top_title)
    TextView titleText;
    @ViewInject(id = R.id.top_image)
    ImageView backText;
    private int tag = 0;

    public static KefuCenterFragment newInstance(int tag) {
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        KefuCenterFragment fragment = new KefuCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void launch(Activity from, FragmentArgs args) {

        ContainerActivity.launch(from, KefuCenterFragment.class, args);
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_kefu_center;
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);

        tag = getArguments().getInt("tag");
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        titleText.setText(getResources().getString(R.string.personal_kefu_center));
        if (tag == 1)
            backText.setVisibility(View.GONE);
        else
            backText.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.call_kefu, R.id.top_image, R.id.kefu_phone_text})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.kefu_phone_text:
            case R.id.call_kefu:
                callKefu();
                break;
            case R.id.top_image:
                getActivity().finish();
                break;
        }
    }

    private void callKefu() {
        if (getActivity() instanceof org.aisen.android.ui.activity.basic.BaseActivity) {
            org.aisen.android.ui.activity.basic.BaseActivity aisenBaseActivity =
                    (org.aisen.android.ui.activity.basic.BaseActivity) getActivity();

            new IAction(aisenBaseActivity, new CallPhonePermissionAction(aisenBaseActivity,
                    null)) {
                @Override
                public void doAction() {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getResources().getString(R.string.order_account_phone_text)));//跳转到拨号界面
                    startActivity(dialIntent);
                }
            }.run();
        }
    }

}
