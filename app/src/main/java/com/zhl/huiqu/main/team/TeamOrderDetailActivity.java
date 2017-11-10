package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.PayActivity;
import com.zhl.huiqu.main.team.bean.OrderDetailBase;
import com.zhl.huiqu.main.team.bean.OrderPut;
import com.zhl.huiqu.personal.bean.OrderContect;
import com.zhl.huiqu.personal.bean.OrderDetailBean;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.personal.bean.OrderInsuran;
import com.zhl.huiqu.personal.bean.OrderTeam;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamOrderDetailActivity extends BaseActivity {
    @Bind(R.id.top_left_text)
    TextView top_left_text;
    @Bind(R.id.bottom)
    LinearLayout bottom;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.t_riqi)
    TextView t_riqi;
    @Bind(R.id.p_num)
    TextView p_num;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.total_m)
    TextView total_m;
    @Bind(R.id.order_num)
    TextView order_num;
    @Bind(R.id.pay_date)
    TextView pay_date;
    @Bind(R.id.t_phone)
    TextView t_phone;
    @Bind(R.id.t_name)
    TextView t_name;

    @Bind(R.id.chuyou_list)
    RecyclerView chuyou_list;
    @Bind(R.id.baoxian_list)
    RecyclerView baoxian_list;
    private CommonAdapter<OrderContect> cAdapter;
    private List<OrderContect> cList;

    private CommonAdapter<OrderInsuran> oAdapter;
    private List<OrderInsuran> oList;

    private String order_state;
    private String order_sn;
    private OrderTeam body;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_order_detail;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        order_state = getIntent().getStringExtra("order_state");
        order_sn = getIntent().getStringExtra("order_sn");
        top_left_text.setTextColor(Color.parseColor("#333333"));
        top_left_text.setText("订单详情");
    }


    @Override
    public void onResume() {
        super.onResume();
        RegisterEntity registerInfo = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        new getOrderinfoTask(registerInfo.getBody().getMember_id()).execute();
    }

    @OnClick({R.id.top_image,R.id.submit})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.top_image:
                TeamOrderDetailActivity.this.finish();
                break;
            case R.id.submit:
                OrderEntity mPerson=new OrderEntity();
                mPerson.setOrder_total(body.getAmoutPrice());
                mPerson.setOrder_sn(body.getOrderSn());
                mPerson.setName(body.getProductName());
                mPerson.setUse_date(body.getDepartureTime());
                if(!TextUtils.isEmpty(body.getOrderId())){
                    mPerson.setOrder_id(Integer.parseInt(body.getOrderId()));
                }
                Intent intent = new Intent(TeamOrderDetailActivity.this, PayActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("body", mPerson);
                intent.putExtra("type","team");
                intent.putExtras(mBundle);
                startActivity(intent);
                break;
            case R.id.cancel:

                break;
        }
    }


    class getOrderinfoTask extends WorkTask<String, Void, OrderDetailBase> {
        private String member_id;

        public getOrderinfoTask(String member_id) {
            this.member_id = member_id;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中..", false);
        }

        @Override
        public OrderDetailBase workInBackground(String... params) throws TaskException {
            return SDK.newInstance(TeamOrderDetailActivity.this).getOrderInfo(member_id, order_sn);
        }

        @Override
        protected void onSuccess(OrderDetailBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info != null) {
                showview(info.getBody());
                body=info.getBody();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    private void showview(OrderTeam data) {
        if ("0".equals(data.getStatus())){
            bottom.setVisibility(View.VISIBLE);
        }else {
            bottom.setVisibility(View.GONE);
        }
        title.setText(data.getProductName());
        t_riqi.setText(data.getDepartureTime());

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(data.getAdultCount())) {
            if (Integer.parseInt(data.getAdultCount()) > 0) {
                sb.append(data.getAdultCount() + "成人,");
            }
        }
        if (!TextUtils.isEmpty(data.getChildCount())) {
            if (Integer.parseInt(data.getChildCount()) > 0) {
                sb.append(data.getAdultCount() + "儿童,");
            }
        }
        p_num.setText(sb.toString().substring(0, sb.toString().length() - 1));
        if ("0".equals(data.getStatus())) {
            state.setText("未支付");
        } else {
            state.setText("已支付");
        }
        total_m.setText("￥" + data.getAmoutPrice());
        order_num.setText(data.getOrderSn());
        pay_date.setText(data.getAddTime());
        t_name.setText(data.getUserName());
        t_phone.setText(data.getUserMobile());
        cList=data.getContactInfo();
        oList=data.getInsuranceInfo();
        setchuyou();
        setbaox();
    }

    private void setchuyou() {
        cAdapter = new CommonAdapter<OrderContect>(TeamOrderDetailActivity.this, R.layout.order_detal_team, cList) {
            @Override
            protected void convert(ViewHolder holder, OrderContect datas, int position) {
                int a=position+1;
            holder.setText(R.id.num,"出游人信息"+"("+String.valueOf(a)+")");
                holder.setText(R.id.name,datas.getName());
                holder.setText(R.id.id_card,datas.getCertificate());

            }
        };
        chuyou_list.setLayoutManager(new LinearLayoutManager(TeamOrderDetailActivity.this));
        chuyou_list.setAdapter(cAdapter);
        chuyou_list.setNestedScrollingEnabled(false);
    }

    private void setbaox() {
        oAdapter = new CommonAdapter<OrderInsuran>(TeamOrderDetailActivity.this, R.layout.order_insuran_item, oList) {
            @Override
            protected void convert(ViewHolder holder, OrderInsuran info, int position) {
                holder.setText(R.id.nmae, info.getType());
                holder.setText(R.id.price, "￥"+info.getPrice());
            }
        };
        baoxian_list.setLayoutManager(new LinearLayoutManager(TeamOrderDetailActivity.this));
        baoxian_list.setAdapter(oAdapter);
        baoxian_list.setNestedScrollingEnabled(false);
    }
}
