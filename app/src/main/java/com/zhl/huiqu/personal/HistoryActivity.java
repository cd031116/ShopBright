package com.zhl.huiqu.personal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.MainSearchActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.bean.MainSearchBean;
import com.zhl.huiqu.main.bean.MainTeam;
import com.zhl.huiqu.main.bean.MainTick;
import com.zhl.huiqu.main.team.TeamDetailActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {
    @Bind(R.id.mrecycle)
    RecyclerView mrecycle;
    @Bind(R.id.jrecycle)
    RecyclerView jrecycle;
    @Bind(R.id.fresh_main)
    PullToRefreshLayout fresh_main;

    @Bind(R.id.top_title)
    TextView top_title;
    List<MainTick> jlist = new ArrayList<>();
    private CommonAdapter<MainTick> jAdapter;
    private  String deviceId;
    private int page = 1;
    private CommonAdapter<MainTeam> mAdapter;
    private List<MainTeam> mlist = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @OnClick({R.id.top_image})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.top_image:
                HistoryActivity.this.finish();
                break;



        }
    }
    @Override
    public void initView() {
        super.initView();
        top_title.setText("浏览历史");
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        new querySearch(deviceId).execute();
    }

    @Override
    public void initData() {
        super.initData();
        fresh_main.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                new querySearch(deviceId).execute();
            }

            @Override
            public void loadMore() {
                page++;
                new querySearchmore(deviceId).execute();
            }
        });
    }

    class querySearch extends WorkTask<String, Void, MainSearchBean> {
        private String nujm;

        public querySearch(String num) {
            this.nujm = num;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载..", false);
        }

        @Override
        public MainSearchBean workInBackground(String... params) throws TaskException {

            return SDK.newInstance(HistoryActivity.this).getbrowserhistoryd(nujm, page + "");
        }

        @Override
        protected void onSuccess(MainSearchBean info) {
            super.onSuccess(info);
            dismissAlert();
            fresh_main.finishRefresh();
            TLog.log("tttt", "info=" + info);
            if (jlist != null) {
                jlist.clear();
            }
            if (mlist != null) {
                mlist.clear();
            }
            jlist.addAll(info.getBody().getTicket());
            mlist.addAll(info.getBody().getTeam());
            setReListView();
            setrelist();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
            fresh_main.finishRefresh();

        }
    }
    class querySearchmore extends WorkTask<String, Void, MainSearchBean> {
        private String nujm;
        public querySearchmore( String num) {
            this.nujm = num;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
        }

        @Override
        public MainSearchBean workInBackground(String... params) throws TaskException {

            return SDK.newInstance(HistoryActivity.this).getbrowserhistoryd(nujm, page + "");
        }

        @Override
        protected void onSuccess(MainSearchBean info) {
            super.onSuccess(info);
            fresh_main.finishLoadMore();
            TLog.log("tttt", "info=" + info);
            jlist.addAll(info.getBody().getTicket());
            mlist.addAll(info.getBody().getTeam());
            setReListView();
            setrelist();
        }

        @Override
        protected void onFailure(TaskException exception) {
            fresh_main.finishLoadMore();
        }
    }

    //门票
    private void setReListView() {
        jAdapter = new CommonAdapter<MainTick>(HistoryActivity.this, R.layout.item_product_part, jlist) {
            @Override
            protected void convert(ViewHolder holder, final  MainTick bean, int position) {

                holder.setRunderWithUrl(R.id.photo, bean.getThumb());

                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.manyidu, bean.getCsr());
                holder.setText(R.id.price, "￥" + bean.getShop_price());
                holder.setText(R.id.desc, bean.getDesc());

                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(HistoryActivity.this,ProductDetailActivity.class);
                        intent.putExtra("shop_spot_id", bean.getShop_spot_id());
                        startActivity(intent);
                    }
                });
            }
        };
        jrecycle.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        jrecycle.setAdapter(jAdapter);
        jrecycle.setNestedScrollingEnabled(false);
    }

    //跟团游
    private void setrelist() {
        mAdapter = new CommonAdapter<MainTeam>(HistoryActivity.this, R.layout.team_list_item, mlist) {
            @Override
            protected void convert(ViewHolder holder, final MainTeam bean, int position) {
                holder.setRunderWithUrl(R.id.photo, bean.getThumb());
                holder.setText(R.id.price, "￥" + bean.getPriceAdultMin());
                holder.setText(R.id.manyidu, bean.getCsr());
                holder.setText(R.id.address, bean.getDepartCitysName());
                holder.setText(R.id.day_time, bean.getDuration() + "日游");
                String stitle = bean.getProductName();
                if (!TextUtils.isEmpty(stitle) && stitle.contains("<")) {
                    holder.setText(R.id.title, stitle.substring(stitle.indexOf("<") + 1, stitle.indexOf(">")));
                } else {
                    holder.setText(R.id.title, stitle);
                }
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(HistoryActivity.this,TeamDetailActivity.class);
                        intent.putExtra("spot_team_id", bean.getProductId());
                        startActivity(intent);

                    }
                });
            }
        };
        mrecycle.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        mrecycle.setAdapter(mAdapter);
        mrecycle.setNestedScrollingEnabled(false);
    }

}
