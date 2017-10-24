package com.zhl.huiqu.main.team;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.search.SearchHistoryManager;
import com.zhl.huiqu.main.team.bean.SearBase;
import com.zhl.huiqu.main.team.bean.SearchInfo;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.TagCloudView;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

import java.util.ArrayList;
import java.util.List;

/*
*
* @author lyj
* @describe 跟团游搜索
* @data 2017/10/24
* */


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
    @ViewInject(id = R.id.line_back)
    LinearLayout line_back;

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
        go_search.setText("取消");
        line_back.setVisibility(View.GONE);
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        new getHotSearch().execute();
        new getSearchHistory().execute(deviceId);

    }

    @OnClick(R.id.editSearch)
    void onSearch(View view) {
        TeamListSearchFragment.launch(getActivity(), "");
    }


    /**
     * 获取热门搜索
     */
    class getHotSearch extends WorkTask<String, Void, SearBase> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public SearBase workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getTeamgetHotSearch();
        }

        @Override
        protected void onSuccess(SearBase info) {
            super.onSuccess(info);
            dismissAlert();
            final List<String> tags = new ArrayList<>();
            for (SearchInfo searchInfo : info.getBody()) {
                tags.add(searchInfo.getSearchContent());
            }
            tagsSchool.setTags(tags);
            tagsSchool.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                @Override
                public void onTagClick(int position) {
                    TeamListSearchFragment.launch(getActivity(), tags.get(position).toString());
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
    class getSearchHistory extends WorkTask<String, Void, SearBase> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public SearBase workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getTeamSearchHistory(params[0]);
        }

        @Override
        protected void onSuccess(SearBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null && info.getBody().size() > 0) {
                final List<String> tags = new ArrayList<>();
                for (SearchInfo searchInfo : info.getBody()) {
                    tags.add(searchInfo.getSearchContent());
                }
                tagsHisgory.setTags(tags);
                tagsHisgory.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position) {
                        TeamListSearchFragment.launch(getActivity(), tags.get(position).toString());
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
     * 清除
     */
    class clearSearchHistory extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).clearTeamSearchHistory(params[0]);
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

    @OnClick(R.id.txtClearHistory)
    void onClearHistory(View view) {
        SearchHistoryManager.clear();
        new clearSearchHistory().execute(deviceId);
    }


    @OnClick(R.id.go_search)
    void onSearchClicked(View view) {
       getActivity().finish();
    }

}
