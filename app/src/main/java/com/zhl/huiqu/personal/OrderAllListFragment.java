package com.zhl.huiqu.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.personal.bean.AllOrderBean;
import com.zhl.huiqu.personal.bean.AllOrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.Utils;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2017/8/14.
 */

public class OrderAllListFragment extends ARecycleViewSwipeRefreshFragment<AllOrderEntity, AllOrderBean, Serializable> {
    private String productId;
    @ViewInject(id = R.id.order_sx)
    TextView sxTextView;
    @ViewInject(id = R.id.top_title)
    TextView titleText;
    private String member_id;

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_all_order;
    }

    public static void launch(Activity from, FragmentArgs args) {
        ContainerActivity.launch(from, OrderAllListFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productId = getArguments().getString("productId");
        RegisterEntity  account = SaveObjectUtils.getInstance(getActivity()).getObject(Constants.USER_INFO, RegisterEntity.class);
        member_id=account.getBody().getMember_id();
        Log.e("tttt", "onCreate: " + productId);
    }

    @OnClick(R.id.top_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("order_state", productId);
        startActivity(intent);

    }

    @Override
    public IItemViewCreator<AllOrderEntity> configItemViewCreator() {
        return new IItemViewCreator<AllOrderEntity>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
                return layoutInflater.inflate(OrderItemView.LAYOUT_RES, viewGroup, false);

            }

            @Override
            public IITemView<AllOrderEntity> newItemView(View view, int i) {
                return new OrderItemView(getActivity(), view);
            }
        };
    }

    @Override
    protected IItemViewCreator<AllOrderEntity> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<AllOrderEntity, AllOrderBean> newPaging() {
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

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getSwipeRefreshLayout().setEnabled(false);
        titleText.setText(productId);
//        scrollView.setFillViewport(true);
    }

    @Override
    public void requestData(RefreshMode refreshMode) {
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    class Task extends APagingTask<Void, Void, AllOrderBean> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<AllOrderEntity> parseResult(AllOrderBean allOrderBean) {
            return allOrderBean.getData();
        }

        @Override
        protected AllOrderBean workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)) {
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AllOrderBean beans = queryList(start);

            if (beans != null && beans.getData() != null) {
                beans.setEndPaging(beans.getData().size() <= 5);
            }

            return beans;
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
        }
    }

    protected AllOrderBean queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getAllOrder(member_id);
        //
//        OrderListEntity bean = new OrderListEntity();
//
//        return bean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
