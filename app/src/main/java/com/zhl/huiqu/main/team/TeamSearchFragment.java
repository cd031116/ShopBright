package com.zhl.huiqu.main.team;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.search.SearchFragment;
import com.zhl.huiqu.main.search.SearchHistoryManager;
import com.zhl.huiqu.main.search.SearchListFragment;
import com.zhl.huiqu.scan.CaptureActivity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.TagCloudView;

import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamSearchFragment extends BaseFragment {
    public static void launch(Activity from) {
        ContainerActivity.launch(from, TeamSearchFragment.class, null);
    }

    @ViewInject(id = R.id.editSearch)
    EditText editSearch;
    @ViewInject(id = R.id.layHistory)
    View layHistory;
    @ViewInject(id = R.id.tagsHistory)
    TagCloudView tagsHisgory;
    @ViewInject(id = R.id.tagsSchool)
    TagCloudView tagsSchool;
    @ViewInject(id = R.id.text_school)
    TextView text_school;
    private String deviceId;

    @ViewInject(id = R.id.scan)
    ImageView scan;
    @ViewInject(id = R.id.go_search)
    TextView go_search;

    @Override
    public int inflateContentView() {
        return R.layout.ui_search;
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }


    @OnClick({R.id.btnBack, R.id.line_back})
    void onBtnBackClicked(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.searh_line:
                break;
        }
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        scan.setVisibility(View.GONE);
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        go_search.setText("取消");
    }

    @OnClick(R.id.editSearch)
    void onSearch(View view) {
        TeamListSearchFragment.launch(getActivity(), "");
    }


    @OnClick(R.id.go_search)
    void onSearchClicked(View view) {
        getActivity().finish();
    }


}
