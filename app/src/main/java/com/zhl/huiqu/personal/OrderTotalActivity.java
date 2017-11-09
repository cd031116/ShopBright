package com.zhl.huiqu.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.team.TeamOrderActivity;
import com.zhl.huiqu.main.team.TeamOrderDetailActivity;
import com.zhl.huiqu.personal.bean.AllOrderBean;
import com.zhl.huiqu.personal.bean.OrderTeam;
import com.zhl.huiqu.personal.bean.OrderTick;
import com.zhl.huiqu.personal.bean.UrLikeBean;
import com.zhl.huiqu.personal.bean.UrLikeEntity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
/*
*
* @author lyj
* @describe 我的订单
* @data 2017/11/9
* */

public class OrderTotalActivity extends BaseActivity {
    private String member_id;

    @Bind(R.id.mp_list)
    RecyclerView mp_list;
    @Bind(R.id.gt_list)
    RecyclerView gt_list;
    @Bind(R.id.fresh_main)
    PullToRefreshLayout fresh_main;

    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.view_empty)
    LinearLayout view_empty;
    @Bind(R.id.text)
    TextView text;

    private int stats = -1;
    int page=1;
    private List<OrderTick> tList=new ArrayList<>();
    private CommonAdapter<OrderTick> tAdapter;

    private List<OrderTeam> oList=new ArrayList<>();
    private CommonAdapter<OrderTeam> oAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_total;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            stats = bd.getInt("stats");
        }

        if(stats==-1){
            top_title.setText("全部订单");
        }
        if(stats==0){
            top_title.setText("待付款");
        }
        if(stats==1){
            top_title.setText("待出行");
        }
        if(stats==4){
            top_title.setText("已退款");
        }




        fresh_main.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page=1;
                new getOrderData().execute();
            }

            @Override
            public void loadMore() {
                page++;
                new getOrderDatamore().execute();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        RegisterEntity account = SaveObjectUtils.getInstance(OrderTotalActivity.this).getObject(Constants.USER_INFO, RegisterEntity.class);
        member_id = account.getBody().getMember_id();
        new getOrderData().execute();
        setTicklist();
        setTeamlist();
    }

    @OnClick({R.id.top_image,})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_image:
                finish();
                break;
        }
    }

    private void setTicklist() {
        tAdapter = new CommonAdapter<OrderTick>(OrderTotalActivity.this, R.layout.item_all_order_list, tList) {
            @Override
            protected void convert(ViewHolder holder,final OrderTick data, final  int position) {
                String strs="";
                if("0".equals(data.getStatus())){
                    strs="未付款";
                }
                if("1".equals(data.getStatus())){
                    strs="已支付";
                }
                if("2".equals(data.getStatus())){
                    strs="已取消";
                }
                if("3".equals(data.getStatus())){
                    strs="已核销";
                }
                if("4".equals(data.getStatus())){
                    strs="已完成";
                }
                holder.setText(R.id.all_order_ticket_state,strs);
                holder.setText(R.id.order_ticket_type,data.getName());
                holder.setText(R.id.order_ticket_number_text,"(" + data.getNum() + "张)");
                holder.setText(R.id.order_goout_time_text,data.getUse_date());
                holder.setText(R.id.order_price,"￥"+data.getPrice());
                holder.setText(R.id.order_ticket_num_text,data.getOrder_sn());
                holder.setOnClickListener(R.id.order_delete_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tList.remove(position);
                        tAdapter.notifyDataSetChanged();
                        new deleteOrder(data.getOrder_id()).execute();
                    }
                });
                holder.setOnClickListener(R.id.main_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("0".equals(data.getStatus())) {
                            Intent intent = new Intent(OrderTotalActivity.this, OrderDetailActivity.class);
                            intent.putExtra("order_state", getResources().getString(R.string.personal_pay_order));
                            intent.putExtra("order_sn",data.getOrder_sn()+"");
                            startActivity(intent);
                        } else if ("1".equals(data.getStatus())) {
                            Intent intent = new Intent(OrderTotalActivity.this, OrderDetailActivity.class);
                            intent.putExtra("order_state", getResources().getString(R.string.personal_out_order));
                            intent.putExtra("order_sn", data.getOrder_sn()+"");
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        mp_list.setLayoutManager(new LinearLayoutManager(OrderTotalActivity.this));
        mp_list.setAdapter(tAdapter);
        mp_list.setNestedScrollingEnabled(false);
    }


    private void setTeamlist() {
        oAdapter = new CommonAdapter<OrderTeam>(OrderTotalActivity.this, R.layout.item_order_team, oList) {
            @Override
            protected void convert(ViewHolder holder, final OrderTeam info, final int position) {
                String strs="";
                if("0".equals(info.getStatus())){
                    strs="未付款";
                }
                if("1".equals(info.getStatus())){
                    strs="已支付";
                }
                if("2".equals(info.getStatus())){
                    strs="已取消";
                }
                if("3".equals(info.getStatus())){
                    strs="已核销";
                }
                if("4".equals(info.getStatus())){
                    strs="已完成";
                }
                holder.setText(R.id.order_price,"￥"+info.getAmoutPrice());
                holder.setText(R.id.all_order_ticket_state,strs);
                holder.setText(R.id.order_ticket_type,info.getProductName());
                int nums=0;
                if(!TextUtils.isEmpty(info.getAdultCount())){
                    nums=nums+Integer.parseInt(info.getAdultCount());
                }
                if(!TextUtils.isEmpty(info.getChildCount())){
                    nums=nums+Integer.parseInt(info.getChildCount());
                }
                holder.setText(R.id.order_ticket_number_text,"(" + nums+"" + "人)");
                holder.setText(R.id.order_goout_time_text,info.getDepartureTime());

                holder.setText(R.id.order_ticket_num_text,info.getOrderSn());
                holder.setOnClickListener(R.id.order_delete_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        oList.remove(position);
                        oAdapter.notifyDataSetChanged();

                        new deleteOrder(info.getOrderId()).execute();
                    }
                });
                holder.setOnClickListener(R.id.main_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent = new Intent(OrderTotalActivity.this, TeamOrderDetailActivity.class);
                            intent.putExtra("order_state", getResources().getString(R.string.personal_pay_order));
                            intent.putExtra("order_sn",info.getOrderSn());
                            startActivity(intent);
                    }
                });
            }
        };
        gt_list.setLayoutManager(new LinearLayoutManager(OrderTotalActivity.this));
        gt_list.setAdapter(oAdapter);
        gt_list.setNestedScrollingEnabled(false);

    }


    //猜你喜欢
    class getOrderData extends WorkTask<String, Void, AllOrderBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public AllOrderBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(OrderTotalActivity.this).getAllOrder(member_id, 1, stats);
        }

        @Override
        protected void onSuccess(AllOrderBean info) {
            super.onSuccess(info);
            dismissAlert();
            if(tList.size()>0){
                tList.clear();
            }
            if(oList.size()>0){
                oList.clear();
            }
            if (info.getBody()!=null) {
                tList.addAll(info.getBody().getTicketOrder());
                oList.addAll(info.getBody().getTeamOrder());
            }
            if(tList.size()<=0&oList.size()<=0){
                view_empty.setVisibility(View.VISIBLE);
                text.setText("暂无数据");
            }else {
                view_empty.setVisibility(View.GONE);
            }
            tAdapter.notifyDataSetChanged();
            oAdapter.notifyDataSetChanged();
            fresh_main.finishRefresh();
        }

        @Override
        protected void onFailure(TaskException exception) {
            view_empty.setVisibility(View.VISIBLE);
            text.setText(exception.getMessage());
            dismissAlert();
            fresh_main.finishRefresh();
        }
    }


    class getOrderDatamore extends WorkTask<String, Void, AllOrderBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public AllOrderBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(OrderTotalActivity.this).getAllOrder(member_id, page, stats);
        }

        @Override
        protected void onSuccess(AllOrderBean info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody()!=null) {
                tList.addAll(info.getBody().getTicketOrder());
                oList.addAll(info.getBody().getTeamOrder());
            }
            tAdapter.notifyDataSetChanged();
            oAdapter.notifyDataSetChanged();
            fresh_main.finishRefresh();
        }

        @Override
        protected void onFailure(TaskException exception) {

            dismissAlert();
            fresh_main.finishRefresh();
        }
    }

    class deleteOrder extends WorkTask<String, Void, BaseInfo> {
        String positions;
        public  deleteOrder(String positions){
            this.positions=positions;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(OrderTotalActivity.this).deleteOrder(positions);

        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            if ("1".equals(info.getCode())) {
                ToastUtils.showShortToast(OrderTotalActivity.this, "该订单已成功删除");
            } else
                ToastUtils.showShortToast(OrderTotalActivity.this, info.getMsg());
        }

        @Override
        protected void onFailure(TaskException exception) {

        }
    }

}
