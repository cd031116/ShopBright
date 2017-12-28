package com.zhl.huiqu.personal;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.team.TeamDetailActivity;
import com.zhl.huiqu.main.team.TeamOrderDetailActivity;
import com.zhl.huiqu.personal.bean.CollectBean;
import com.zhl.huiqu.personal.bean.CollectEntity;
import com.zhl.huiqu.personal.bean.CollectTick;
import com.zhl.huiqu.personal.bean.ColletTeam;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.CommomDialog;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的收藏
 * Created by dw on 2017/8/11.
 */

public class MyCollectAcitivity extends BaseActivity {
    @Bind(R.id.my_collect_list)
    RecyclerView recyclerView;
    @Bind(R.id.his_is_null)
    TextView his_is_null;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.mp_list)
    RecyclerView mp_list;

    @Bind(R.id.fresh_main)
    PullToRefreshLayout fresh_main;

    List<CollectTick> list = new ArrayList<>();
    private CollectRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isCancel;
    private String memberId="";
    private CommonAdapter<ColletTeam> mAdapter;
    private List<ColletTeam> mlist = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setVisibility(View.VISIBLE);
        top_title.setText(getResources().getString(R.string.personal_my_collect));
        fresh_main.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new queryCollect().execute(memberId, "1");
            }

            @Override
            public void loadMore() {

            }
        });
        fresh_main.setCanLoadMore(false);
    }

    @Override
    public void initData() {
        super.initData();
         memberId = getIntent().getStringExtra("memberId");
        new queryCollect().execute(memberId, "1");
        if (list != null) {
            his_is_null.setVisibility(View.GONE);
            setReListView(list);
        } else {
            his_is_null.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.top_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
        }
    }

    class queryCollect extends WorkTask<String, Void, CollectBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public CollectBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(MyCollectAcitivity.this).getCollectList(params[0]);
        }

        @Override
        protected void onSuccess(CollectBean info) {
            super.onSuccess(info);
            dismissAlert();
            fresh_main.finishRefresh();
            TLog.log("tttt", "info=" + info);
            if(list!=null){
                list.clear();
            }
            if(mlist!=null){
                mlist.clear();
            }
            list.addAll(info.getBody().getTicket());
            mlist.addAll(info.getBody().getTeam());
            setReListView(list);
            setrelist();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
            fresh_main.finishRefresh();
        }
    }

    private void setReListView(final List<CollectTick> collectEntityList) {
        recyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewAdapter = new CollectRecyclerViewAdapter(MyCollectAcitivity.this, collectEntityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCollectAcitivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter.setOnItemClickListener(new CollectRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyCollectAcitivity.this, ProductDetailActivity.class);
                intent.putExtra("shop_spot_id", collectEntityList.get(position).getShop_spot_id() + "");
                startActivity(intent);
            }
        });
        mRecyclerViewAdapter.setOnItemLongClickListener(new CollectRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Log.e("ttt", "onItemLongClick: "+position );
                if (isCancel)
                    ToastUtils.showShortToast(MyCollectAcitivity.this, getResources().getString(R.string.collect_is_cancel));
                else {
                    if (!TextUtils.isEmpty(list.get(position).getShop_spot_id()+""))
                        new CommomDialog(getApplicationContext(), R.style.progress_dialog, "您确定取消此收藏？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
//                                    new deleteCollect().execute(orderId);
                                }
                            }
                        })
                                .setTitle("提示").show();
                }
            }
        });
    }

    private void setrelist(){
        mAdapter=new CommonAdapter<ColletTeam>(MyCollectAcitivity.this,R.layout.item_tourist_point,mlist) {
            @Override
            protected void convert(ViewHolder holder, final ColletTeam bean, int position) {

                holder.setText(R.id.tourist_area,bean.getProductName());
                holder.setBitmapWithUrl(R.id.tourist_view,bean.getThumb());
                holder.setText(R.id.tourist_tag,bean.getDuration()+"日游");
                holder.setText(R.id.tourist_place,bean.getDesCityName());
                holder.setText(R.id.tourist_price,"￥" +bean.getPriceAdultMin());
                holder.setText(R.id.tourist_ms,bean.getDepartCitysName()+"-->"+bean.getDesCityName());

                 holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(MyCollectAcitivity.this, TeamDetailActivity.class);
                         intent.putExtra("spot_team_id", bean.getProductId());
                         startActivity(intent);
                     }
                 }) ;
                holder.setOnLongClickListener(R.id.main_top, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new CommomDialog(getApplicationContext(), R.style.progress_dialog, "您确定取消此收藏？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
//                                    new deleteCollect().execute(orderId);
                                }
                            }
                        })
                                .setTitle("提示").show();
                        return false;
                    }
                });
            }
        };
        mp_list.setLayoutManager(new LinearLayoutManager(MyCollectAcitivity.this));
        mp_list.setAdapter(mAdapter);
        mp_list.setNestedScrollingEnabled(false);
    }
}
