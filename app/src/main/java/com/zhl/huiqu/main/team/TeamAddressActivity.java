package com.zhl.huiqu.main.team;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;

import butterknife.Bind;

public class TeamAddressActivity extends BaseActivity {
    @Bind(R.id.provity)
    RecyclerView mProvity;
    @Bind(R.id.recycleview)
    RecyclerView city_list;

    private CommonAdapter<String> mAdapter;
    private CommonAdapter<String> nAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_address);
    }

    @Override
    protected int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }






}
