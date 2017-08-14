package com.zhl.huiqu.personal;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的收藏
 * Created by dw on 2017/8/11.
 */

public class MyCollectAcitivity extends BaseActivity {
    @Bind(R.id.my_collect_list)
    RecyclerView recyclerView;
    @Bind(R.id.his_is_null)
    TextView his_is_null;
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
//        his_is_null.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        CollectEntity collectEntity = new CollectEntity();
        collectEntity.setCollectAddress("cs");
        collectEntity.setCollectJb("4a");
        collectEntity.setCollectMs("最美石燕湖");
        collectEntity.setCollectPf("4.4fen");
        collectEntity.setCollectPrice("$50");
        collectEntity.setCollectTag("风景名胜");
        collectEntity.setCollectWhere("石燕湖");
        collectEntity.setImgUrl("http://wx.qlogo.cn/mmhead/MhptZYyBT66XRj8aYSynIHRaGGIo7LCZvcUk7QBGXrU/0");
        list.add(collectEntity);
        mRecyclerViewAdapter = new CollectRecyclerViewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter.setOnItemClickListener(new CollectRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("onItemClick: ", position + "");
            }
        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider));
    }
}
