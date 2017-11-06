package com.zhl.huiqu.main.team;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.interfaces.ItemTeamCallback;
import com.zhl.huiqu.main.team.bean.TeamListInfo;
import com.zhl.huiqu.main.team.bean.TeamSearchInfo;
import com.zhl.huiqu.main.team.itemview.TeamPartItemView;
import com.zhl.huiqu.scan.CaptureActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.utils.Utils;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.support.paging.IPaging;
import org.aisen.android.support.paging.PageIndexPaging;
import org.aisen.android.ui.activity.container.FragmentArgs;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamListSearchFragment extends ARecycleViewSwipeRefreshFragment<TeamListInfo, TeamSearchInfo, Serializable> implements ItemTeamCallback {
    private List<TeamListInfo> tickInfoList;

    public static void launch(Activity from, String content) {
        FragmentArgs args = new FragmentArgs();
        args.add("content", content);
        ContainerActivity.launch(from, TeamListSearchFragment.class, args);
    }

    @ViewInject(id = R.id.editSearch)
    EditText editSearch;
    @ViewInject(id = R.id.error_text)
    TextView error_text;
    @ViewInject(id = R.id.error_image)
    ImageView error_image;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;
    @ViewInject(id = R.id.list_layout)
    RelativeLayout list_layout;

    private String content;
    private String deviceId;

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView() {
        return R.layout.ui_search_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        String shop_spot_id = getAdapterItems().get(position).getProductId();
        if (position < getAdapterItems().size()) {
            Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
            intent.putExtra("spot_team_id", shop_spot_id);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments().getString("content");
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
    }

    @Override
    public IItemViewCreator<TeamListInfo> configItemViewCreator() {
        return new IItemViewCreator<TeamListInfo>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
                return layoutInflater.inflate(TeamPartItemView.LAYOUT_RES, viewGroup, false);
            }

            @Override
            public IITemView<TeamListInfo> newItemView(View view, int i) {
                return new TeamPartItemView(getActivity(), view, TeamListSearchFragment.this);
            }
        };
    }

    @Override
    protected IItemViewCreator<TeamListInfo> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<TeamListInfo, TeamSearchInfo> newPaging() {
        return new PageIndexPaging<>();
    }

    @Override
    protected void setupRefreshConfig(RefreshConfig config) {
        super.setupRefreshConfig(config);
        config.footerMoreEnable = true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        requestData(RefreshMode.reset);
    }

    @OnClick({R.id.btnBack, R.id.line_back, R.id.scan,R.id.go_search})
    void onBtnBackClicked(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.scan:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.go_search:
                Log.e("tttt", "onSearchClicked:" );
                String text = editSearch.getText().toString();
                Log.e("tttt", "onSearchClicked: " + text );
                if (!TextUtils.isEmpty(text)) {
                    content = text;
//                    new Task(RefreshMode.reset).execute();
                    list_layout.setVisibility(View.GONE);
                    requestData(RefreshMode.reset);
                } else {
                    ToastUtils.showShortToast(getActivity(), "请输入搜索关键字");
                }
                break;
        }
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
//        recycleview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), null, 1));
//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.length() == 0) {
//                    getActivity().finish();
//                }
//            }
//        });
    }

    @Override
    public void requestData(RefreshMode refreshMode) {
        if(!TextUtils.isEmpty(content)){
            new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
        }
    }

    @Override
    public void onClickItemBean(TeamListInfo bean, int position) {

    }

    class Task extends APagingTask<Void, Void, TeamSearchInfo> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TeamListInfo> parseResult(TeamSearchInfo bean) {
            list_layout.setVisibility(View.VISIBLE);
            return bean.getBody();
        }


        @Override
        protected TeamSearchInfo workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)) {
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TeamSearchInfo beans = queryList(start);
            if (beans != null && beans.getBody() != null) {
                beans.setEndPaging(beans.getBody().size() <= 5);
                tickInfoList = beans.getBody();
            }
            return beans;
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            error_text.setText(exception.getMessage());
        }
    }

    protected TeamSearchInfo queryList(int start) throws TaskException {

        return SDK.newInstance(getActivity()).getSearchTeamByCondition(content, deviceId, start + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
