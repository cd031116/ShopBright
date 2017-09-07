package com.zhl.huiqu.personal;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.personal.bean.CollectBean;
import com.zhl.huiqu.personal.bean.CollectEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.TLog;

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

    List<CollectEntity> list = new ArrayList<>();
    private CollectRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setVisibility(View.VISIBLE);
        top_title.setText(getResources().getString(R.string.personal_my_collect));
    }

    @Override
    public void initData() {
        super.initData();
        String memberId = getIntent().getStringExtra("memberId");
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
            TLog.log("tttt", "info=" + info);
            list.addAll(info.getBody());
            setReListView(list);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void setReListView(final List<CollectEntity> collectEntityList) {
        mRecyclerViewAdapter = new CollectRecyclerViewAdapter(MyCollectAcitivity.this, collectEntityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCollectAcitivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter.setOnItemClickListener(new CollectRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyCollectAcitivity.this, ProductDetailActivity.class);
                intent.putExtra("shop_spot_id", collectEntityList.get(position).getProduct_id() + "");
                startActivity(intent);
            }
        });
    }
}
