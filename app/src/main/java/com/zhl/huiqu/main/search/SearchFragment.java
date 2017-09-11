package com.zhl.huiqu.main.search;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.MainTabFragment;
import com.zhl.huiqu.main.bean.SearchEntity;
import com.zhl.huiqu.main.bean.SearchInfo;
import com.zhl.huiqu.main.ticket.TickListFragment;
import com.zhl.huiqu.personal.KefuCenterFragment;
import com.zhl.huiqu.personal.PersonalFragment;
import com.zhl.huiqu.scan.CaptureActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.ISearchSubscriber;
import com.zhl.huiqu.sdk.eventbus.SearchEvent;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.TagCloudView;

import org.aisen.android.common.utils.Logger;
import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 17/2/19.
 */

public class SearchFragment extends BaseFragment {

    public static void launch(Activity from) {
        ContainerActivity.launch(from, SearchFragment.class, null);
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

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int inflateContentView() {
        return R.layout.ui_search;
    }

    @OnClick({R.id.btnBack, R.id.line_back, R.id.scan})
    void onBtnBackClicked(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.scan:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.searh_line:
                break;
        }
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        new getHotSearch().execute();
        new getSearchHistory().execute(deviceId);
    }

    @OnClick(R.id.txtClearHistory)
    void onClearHistory(View view) {
        SearchHistoryManager.clear();
        new clearSearchHistory().execute(deviceId);
    }


    @OnClick(R.id.go_search)
    void onSearchClicked(View view) {
        String text = editSearch.getText().toString();

        if (!TextUtils.isEmpty(text)) {
            SearchListFragment.launch(getActivity(), text);
        }
    }

    private void onSearchItemClicked(String text) {
        SearchHistoryManager.addHistory(text);
    }

    /**
     * 获取热门搜索
     */
    class getHotSearch extends WorkTask<String, Void, SearchEntity> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public SearchEntity workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getHotSearch();
        }

        @Override
        protected void onSuccess(SearchEntity info) {
            super.onSuccess(info);
            dismissAlert();
            final List<String> tags = new ArrayList<>();
            for (SearchInfo searchInfo : info.getBody()) {
                tags.add(searchInfo.getSearch_content());
            }
            tagsSchool.setTags(tags);
            tagsSchool.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                @Override
                public void onTagClick(int position) {
                    SearchListFragment.launch(getActivity(), tags.get(position).toString());
                }
            });
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 获取历史搜索
     */
    class getSearchHistory extends WorkTask<String, Void, SearchEntity> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public SearchEntity workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getSearchHistory(params[0]);
        }

        @Override
        protected void onSuccess(SearchEntity info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null && info.getBody().size() > 0) {
                final List<String> tags = new ArrayList<>();
                for (SearchInfo searchInfo : info.getBody()) {
                    tags.add(searchInfo.getSearch_content());
                }
                tagsHisgory.setTags(tags);
                tagsHisgory.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position) {
                        SearchListFragment.launch(getActivity(), tags.get(position).toString());
                    }
                });
                layHistory.setVisibility(View.VISIBLE);
            } else
                layHistory.setVisibility(View.GONE);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    /**
     * 获取热门搜索
     */
    class clearSearchHistory extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).clearSearchHistory(params[0]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getCode().equals("1"))
                layHistory.setVisibility(View.GONE);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

}
