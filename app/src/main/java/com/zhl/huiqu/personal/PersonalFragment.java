package com.zhl.huiqu.personal;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.main.MainTabFragment;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心
 * Created by dw on 2017/8/11.
 */

public class PersonalFragment extends BaseFragment {
    @Bind(R.id.row_collect_layout)
    RelativeLayout myCollect;
    @Bind(R.id.row_look_his_layout)
    RelativeLayout lookHistory;
    @Bind(R.id.row_normal_msg_layout)
    RelativeLayout normalMsg;
    @Bind(R.id.row_kefu_center_layout)
    RelativeLayout kefuCenter;


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

    @OnClick({R.id.row_collect_layout, R.id.row_look_his_layout, R.id.row_normal_msg_layout, R.id.row_kefu_center_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.row_collect_layout:
                startActivity(new Intent(getActivity(), MyCollectAcitivity.class));
                break;
            case R.id.row_look_his_layout:
                break;
            case R.id.row_normal_msg_layout:
                break;
            case R.id.row_kefu_center_layout:
                break;
        }
    }
}
