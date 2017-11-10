package com.zhl.huiqu.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.bean.MainSearchBean;
import com.zhl.huiqu.main.bean.MainTeam;
import com.zhl.huiqu.main.bean.MainTick;
import com.zhl.huiqu.main.team.TeamDetailActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.GlideRoundTransform;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainSearchActivity extends BaseActivity {

    @Bind(R.id.mrecycle)
    RecyclerView mrecycle;
    @Bind(R.id.jrecycle)
    RecyclerView jrecycle;
    @Bind(R.id.fresh_main)
    PullToRefreshLayout fresh_main;
    @Bind(R.id.editSearch)
    EditText editSearch;

    List<MainTick> jlist = new ArrayList<>();
    private CommonAdapter<MainTick> jAdapter;

    private int page = 1;
    private CommonAdapter<MainTeam> mAdapter;
    private List<MainTeam> mlist = new ArrayList<>();
    private String deviceId = "";
    private String keyd="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_search;
    }

    @Override
    public void initView() {
        super.initView();
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
    }

    @Override
    public void initData() {
        super.initData();
        fresh_main.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                new querySearch(keyd, deviceId).execute();
            }

            @Override
            public void loadMore() {
                page++;
                new querySearchmore(keyd, deviceId).execute();
            }
        });
    }


    @OnClick({R.id.btnBack, R.id.go_search})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                MainSearchActivity.this.finish();
                break;
            case R.id.go_search:
                 keyd = editSearch.getText().toString();
                if (TextUtils.isEmpty(keyd)) {
                    ToastUtils.showShortToast(MainSearchActivity.this, "请输入关键字!");
                    return;
                }
                new querySearch(keyd, deviceId).execute();
                break;


        }
    }

    class querySearch extends WorkTask<String, Void, MainSearchBean> {
        private String keys;
        private String nujm;

        public querySearch(String keys, String num) {
            this.keys = keys;
            this.nujm = num;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public MainSearchBean workInBackground(String... params) throws TaskException {

            return SDK.newInstance(MainSearchActivity.this).getSearchInfo(keys, nujm, page + "");
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
        private String keys;
        private String nujm;

        public querySearchmore(String keys, String num) {
            this.keys = keys;
            this.nujm = num;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public MainSearchBean workInBackground(String... params) throws TaskException {

            return SDK.newInstance(MainSearchActivity.this).getSearchInfo(keys, nujm, page + "");
        }

        @Override
        protected void onSuccess(MainSearchBean info) {
            super.onSuccess(info);
            dismissAlert();
            fresh_main.finishRefresh();
            TLog.log("tttt", "info=" + info);
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

    //门票
    private void setReListView() {
        jAdapter = new CommonAdapter<MainTick>(MainSearchActivity.this, R.layout.item_product_part, jlist) {
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
                        Intent intent=new Intent(MainSearchActivity.this,ProductDetailActivity.class);
                        intent.putExtra("shop_spot_id", bean.getShop_spot_id());
                        startActivity(intent);
                    }
                });
            }
        };
        jrecycle.setLayoutManager(new LinearLayoutManager(MainSearchActivity.this));
        jrecycle.setAdapter(jAdapter);
        jrecycle.setNestedScrollingEnabled(false);
    }

    //跟团游
    private void setrelist() {
        mAdapter = new CommonAdapter<MainTeam>(MainSearchActivity.this, R.layout.team_list_item, mlist) {
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
                    Intent intent=new Intent(MainSearchActivity.this,TeamDetailActivity.class);
                        intent.putExtra("spot_team_id", bean.getProductId());
                        startActivity(intent);

                    }
                });
            }
        };
        mrecycle.setLayoutManager(new LinearLayoutManager(MainSearchActivity.this));
        mrecycle.setAdapter(mAdapter);
        mrecycle.setNestedScrollingEnabled(false);
    }

}
