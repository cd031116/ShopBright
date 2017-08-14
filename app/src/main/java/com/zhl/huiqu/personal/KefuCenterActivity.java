package com.zhl.huiqu.personal;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 客服中心
 * Created by dw on 2017/8/11.
 */

public class KefuCenterActivity extends BaseActivity{

    @Bind(R.id.kefu_list)
    RecyclerView recyclerView;
    private KefuRecyclerViewAdapter mRecyclerViewAdapter;
    List<String> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kefu_center;
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        String string=getResources().getString(R.string.call_kefu_phone);
        list.add(string);
        String string1=getResources().getString(R.string.call_kefu_phone);
        list.add(string1);
        mRecyclerViewAdapter = new KefuRecyclerViewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
