package com.zhl.huiqu.main.search;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.bean.SearchTickEntity;
import com.zhl.huiqu.main.ticket.TickInfo;
import com.zhl.huiqu.main.ticket.TickItemView;
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
 * Created by Administrator on 2017/9/8.
 */

public class SearchListFragment extends ARecycleViewSwipeRefreshFragment<TickInfo, SearchTickEntity, Serializable> {

    private String deviceId;
    private List<TickInfo> tickInfoList;

    public static void launch(Activity from, String content) {
        FragmentArgs args = new FragmentArgs();
        args.add("content", content);
        ContainerActivity.launch(from, SearchListFragment.class, args);
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

    @ViewInject(id = R.id.progress)
    ProgressBar progress;

    private String content;

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
        int shop_spot_id = getAdapterItems().get(position).getShop_spot_id();
        if (position < getAdapterItems().size()) {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("shop_spot_id", shop_spot_id + "");
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
    public IItemViewCreator<TickInfo> configItemViewCreator() {
        return new IItemViewCreator<TickInfo>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
                return layoutInflater.inflate(TickItemView.LAYOUT_RES, viewGroup, false);
            }

            @Override
            public IITemView<TickInfo> newItemView(View view, int i) {
                return new TickItemView(getActivity(), view);
            }
        };
    }

    @Override
    protected IItemViewCreator<TickInfo> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<TickInfo, SearchTickEntity> newPaging() {
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
        requestData(RefreshMode.reset);
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
        }
    }


    @OnClick(R.id.go_search)
    void onSearchClicked(View view) {
        String text = editSearch.getText().toString();
        Log.e("ttt", "onSearchClicked: " + text + "refresh" + tickInfoList.size());
        if (!TextUtils.isEmpty(text)) {
            list_layout.setVisibility(View.GONE);
            content = text;
            new SearchListFragment.Task(RefreshMode.reset).execute();
        } else {
            ToastUtils.showShortToast(getActivity(), "请输入搜索关键字");
        }
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        recycleview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), null, 1));
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("ttt", "afterTextChanged: " + editable.length());
                if (editable.length() == 0) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void requestData(RefreshMode refreshMode) {
        progress.setVisibility(View.VISIBLE);
        new SearchListFragment.Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    class Task extends APagingTask<Void, Void, SearchTickEntity> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TickInfo> parseResult(SearchTickEntity bean) {
            list_layout.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            return bean.getBody();
        }


        @Override
        protected SearchTickEntity workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)) {
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SearchTickEntity beans = queryList(start);
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
            progress.setVisibility(View.GONE);
        }
    }

    protected SearchTickEntity queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getSearchInfoByCondition(content, deviceId, start + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

