package com.zhl.huiqu.main.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.OnClick;

public class TeamOrderActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_order;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.top_main})
    void onclicj(View v){
        switch (v.getId()){
            case R.id.top_main:
                TeamOrderActivity.this.finish();
                break;
        }
    }
}
