package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.Utils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.support.paging.IPaging;
import org.aisen.android.support.paging.PageIndexPaging;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 2017/8/15.
 */

public class TickListFragment  extends ARecycleViewSwipeRefreshFragment<TickInfo, TickListInfo, Serializable> {

    public static TickListFragment newInstance(String theme_id) {
        Bundle args = new Bundle();
        args.putString("theme_id", theme_id);
        TickListFragment fragment = new TickListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewInject(id = R.id.error_text)
    TextView error_text;
    @ViewInject(id = R.id.error_image)
    ImageView error_image;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;


    private String theme_id;
    @Override
    public void setContentView(ViewGroup view){
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView(){
        return R.layout.ui_recycle;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        int a = getAdapterItems().get(position).getShop_spot_id();
        if (position < getAdapterItems().size()) {
            startActivity(new Intent(getActivity(),ProductDetailActivity.class));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme_id = getArguments().getString("theme_id");
    }

    @Override
    public IItemViewCreator<TickInfo> configItemViewCreator() {
        return new IItemViewCreator<TickInfo>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i){
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
    protected IPaging<TickInfo, TickListInfo> newPaging() {
        return new PageIndexPaging<>();
    }

//    @Override
//    protected RecyclerView.LayoutManager configLayoutManager() {
//
//        return new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
//    }

    @Override
    protected void setupRefreshConfig(RefreshConfig config) {
        super.setupRefreshConfig(config);
        config.footerMoreEnable = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(RefreshMode.reset);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getSwipeRefreshLayout().setEnabled(false);
//        scrollView.setFillViewport(true);
    }

    @Override
    public void requestData(RefreshMode refreshMode){
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    class Task extends APagingTask<Void, Void, TickBean>{
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TickInfo> parseResult(TickBean bean) {
            return bean.getData().getTicketOnly();
        }



        @Override
        protected TickBean workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids)throws TaskException{
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)){
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TickBean beans = queryList(start);
            if (beans != null && beans.getData() != null){
                beans.setEndPaging(beans.getData().getTicketOnly().size() <= 5);
            }
            return beans;
        }

        @Override
        protected void onFailure(TaskException exception){
            super.onFailure(exception);
//            error_text.setText(exception.getMessage());
//            if ("noneNetwork".equals(exception.getCode())) {
//                error_image.setImageResource(R.mipmap.no_net);
//            }
        }
    }
    protected TickBean queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getTicketData("5",start+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
