package com.zhl.huiqu.main.ticket;

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

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.main.ProductPartListBean;
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
 * Created by Administrator on 2017/8/15.
 */

public class TickListFragment  extends ARecycleViewSwipeRefreshFragment<ProductPartBean, ProductPartListBean, Serializable> {

    public static TickListFragment newInstance() {
        Bundle args = new Bundle();
//        args.putString("categoryId", categoryId);
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

    private String categoryId;
    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }
    @Override
    public int inflateContentView() {
        return R.layout.ui_recycle;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        int a = getAdapterItems().get(position).getId();
        if (position < getAdapterItems().size()) {
            startActivity(new Intent(getActivity(),ProductDetailActivity.class));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getString("categoryId");
    }

    @Override
    public IItemViewCreator<ProductPartBean> configItemViewCreator() {
        return new IItemViewCreator<ProductPartBean>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i){
                return layoutInflater.inflate(TickItemView.LAYOUT_RES, viewGroup, false);
            }

            @Override
            public IITemView<ProductPartBean> newItemView(View view, int i) {
                return new TickItemView(getActivity(), view);
            }
        };
    }

    @Override
    protected IItemViewCreator<ProductPartBean> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<ProductPartBean, ProductPartListBean> newPaging() {
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
    public void requestData(RefreshMode refreshMode) {
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    class Task extends APagingTask<Void, Void, ProductPartListBean> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<ProductPartBean> parseResult(ProductPartListBean productPartListBean) {
            return productPartListBean.getResult();
        }

        @Override
        protected ProductPartListBean workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)){
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ProductPartListBean beans = queryList(start);

            if (beans != null && beans.getResult() != null) {
                beans.setEndPaging(beans.getResult().size() <= 5);
            }

            return beans;
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
//            error_text.setText(exception.getMessage());
//            if ("noneNetwork".equals(exception.getCode())) {
//                error_image.setImageResource(R.mipmap.no_net);
//            }
        }
    }
    int a=0;
    protected ProductPartListBean queryList(int start) throws TaskException {
//        return SDK.newInstance(getActivity()).queryBProductList(AppContext.getEmployeeId(), categoryId, start);
        //

        ProductPartListBean bean=new ProductPartListBean();
        bean.setTotal(5);
        List<ProductPartBean> result=new ArrayList<>();
        for(int i=0;i<10;i++){
            ProductPartBean bn=new ProductPartBean();
            bn.setId(i+5);
            result.add(bn);
        }
        bean.setResult(result);
        return  bean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
















}
