package com.zhl.huiqu.main;

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
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.bean.SearchBean;
import com.zhl.huiqu.main.ticket.TickInfo;
import com.zhl.huiqu.main.ticket.TickItemView;
import com.zhl.huiqu.main.ticket.TicksListFragment;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.Utils;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.support.paging.IPaging;
import org.aisen.android.support.paging.PageIndexPaging;
import org.aisen.android.ui.activity.container.FragmentArgs;
import org.aisen.android.ui.fragment.ARecycleViewFragment;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.List;


/*
*
* @author lyj
* @describe  周边
* @data 2017/11/9
* */

public class NearlyFragment extends ARecycleViewFragment<TickInfo, SearchBean, Serializable> {

    public static NearlyFragment newInstance(String theme_id) {
        Bundle args = new Bundle();
        args.putString("theme_id", theme_id);
        NearlyFragment fragment = new NearlyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static void launch(Activity from, String theme_id) {
        FragmentArgs args = new FragmentArgs();
        args.add("theme_id", theme_id);
        ContainerActivity.launch(from, TicksListFragment.class, args);
    }

    @ViewInject(id = R.id.error_text)
    TextView error_text;
    @ViewInject(id = R.id.error_image)
    ImageView error_image;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;
    @ViewInject(id = R.id.top_image)
    ImageView top_image;

    @ViewInject(id = R.id.top_title)
    TextView top_title;
    @ViewInject(id = R.id.fresh_main)
    PullToRefreshLayout fresh_main;


    private String theme_id;
    @Override
    public void setContentView(ViewGroup view){
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView(){
        return R.layout.nearly_recycle;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        int a = getAdapterItems().get(position).getShop_spot_id();
        if (position < getAdapterItems().size()) {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("shop_spot_id",a+"");
            startActivity(intent);
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
    protected IPaging<TickInfo, SearchBean> newPaging() {
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
        config.footerMoreEnable = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(RefreshMode.reset);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        top_title.setText("周边");
        top_image.setVisibility(View.GONE);
        fresh_main.setCanLoadMore(false);
        fresh_main.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
               new Task(RefreshMode.reset).execute() ;
            }

            @Override
            public void loadMore() {

            }
        });
//        getSwipeRefreshLayout().setEnabled(false);
//        recycleview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), null, 1));
//        scrollView.setFillViewport(true);
    }

    @Override
    public void requestData(RefreshMode refreshMode){
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    class Task extends APagingTask<Void, Void, SearchBean>{
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TickInfo> parseResult(SearchBean bean) {
            fresh_main.finishRefresh();
            return bean.getBody();
        }


        @Override
        protected SearchBean workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids)throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)){
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SearchBean beans = queryList(start);
            if (beans != null && beans.getBody()!= null){
                beans.setEndPaging(beans.getBody().size() <= 5);
            }
            return beans;
        }

        @Override
        protected void onFailure(TaskException exception){
            super.onFailure(exception);
            error_text.setText(exception.getMessage());
            fresh_main.finishRefresh();
//            if ("noneNetwork".equals(exception.getCode())) {
//                error_image.setImageResource(R.mipmap.no_net);
//            }
        }
    }
    protected SearchBean queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getTicketData(theme_id,start+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
