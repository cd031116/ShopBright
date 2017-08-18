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
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.Utils;

import org.aisen.android.network.task.TaskException;
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

public class OrderAllListFragment extends ARecycleViewSwipeRefreshFragment<OrderEntity, OrderListEntity, Serializable> {
    private String productId;
    @ViewInject(id = R.id.order_sx)
    TextView sxTextView;
    @ViewInject(id = R.id.top_title)
    TextView titleText;

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
        Log.e("tttt", "onCreate: "+productId );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        super.onItemClick(parent, view, position, id);
        Intent intent=new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("order_state",productId);
        startActivity(intent);

    }

    @Override
    public IItemViewCreator<OrderEntity> configItemViewCreator() {
        return new IItemViewCreator<OrderEntity>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
                return layoutInflater.inflate(OrderItemView.LAYOUT_RES, viewGroup, false);

            }

            @Override
            public IITemView<OrderEntity> newItemView(View view, int i) {
                return new OrderItemView(getActivity(), view);
            }
        };
    }

    @Override
    protected IItemViewCreator<OrderEntity> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<OrderEntity, OrderListEntity> newPaging() {
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

    class Task extends APagingTask<Void, Void, OrderListEntity> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<OrderEntity> parseResult(OrderListEntity orderListEntity) {
            return orderListEntity.getResult();
        }

        @Override
        protected OrderListEntity workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)) {
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            OrderListEntity beans = queryList(start);

            if (beans != null && beans.getResult() != null) {
                beans.setEndPaging(beans.getResult().size() <= 5);
            }

            return beans;
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
        }
    }

    protected OrderListEntity queryList(int start) throws TaskException {
//        return SDK.newInstance(getActivity()).queryBProductList(AppContext.getEmployeeId(), categoryId, start);
        //
        OrderListEntity bean = new OrderListEntity();
        bean.setTotal(5);

        List<OrderEntity> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity bn = new OrderEntity();
            bn.setOrderNum(i + "xx");
            bn.setTicketNum(9999);
            bn.setTicketPrice(99);
            if (i % 4 == 0) {
                bn.setTicketWhere("石燕湖大门票");
                bn.setTicketType("成人票");
                bn.setTicketState("已完成");
            }
            if (i % 4 == 1) {
                bn.setTicketWhere("大帽山大门票");
                bn.setTicketType("儿童票");
                bn.setTicketState("带出行");
            }
            if (i % 4 == 2) {
                bn.setTicketWhere("梅江大门票");
                bn.setTicketType("老人票");
                bn.setTicketState("已取消");
            }
            if (i % 4 == 3) {
                bn.setTicketWhere("石牛仔大门票");
                bn.setTicketType("残疾人票");
                bn.setTicketState("代付款");
            }
            bn.setTicketTime("2017-8-14");
            result.add(bn);
        }
        bean.setResult(result);
        return bean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
