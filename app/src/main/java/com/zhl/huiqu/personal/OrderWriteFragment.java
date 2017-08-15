package com.zhl.huiqu.personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.utils.CodeUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

/**
 * Created by dw on 2017/8/15.
 */

public class OrderWriteFragment extends BaseFragment {

    @ViewInject(id = R.id.check_code_img)
    ImageView checkCodeImg;
    private String realCode;

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode().toLowerCase();
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_write_order;
    }

    public static void launch(Activity from) {
        ContainerActivity.launch(from, OrderWriteFragment.class, null);
    }

    @OnClick({R.id.down_btn, R.id.add_btn, R.id.commit_order_btn, R.id.check_code_img})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_btn:
                break;
            case R.id.add_btn:
                break;
            case R.id.commit_order_btn:

                break;
            case R.id.check_code_img:
                checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
                realCode = CodeUtils.getInstance().getCode().toLowerCase();
                break;
        }
    }
}
