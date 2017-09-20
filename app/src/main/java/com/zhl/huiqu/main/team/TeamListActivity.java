package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.utils.Constants;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamListActivity extends BaseActivity {

    @Bind(R.id.tab1_t)
    TextView tab1_t;
    @Bind(R.id.tab1_v)
    TextView tab1_v;
    @Bind(R.id.tab2_t)
    TextView tab2_t;
    @Bind(R.id.tab2_v)
    TextView tab2_v;
    @Bind(R.id.tab3_t)
    TextView tab3_t;
    @Bind(R.id.tab3_v)
    TextView tab3_v;
    private int select=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_list;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.address,R.id.tab1_mian,R.id.tab2_mian,R.id.tab3_mian})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.address:
                TeamListActivity.this.finish();
                break;
            case R.id.image:

                break;
            case R.id.tab1_mian:
                select = 1;
                changeview(select);
                break;
            case R.id.tab2_mian:
                select = 2;
                changeview(select);
                break;
            case R.id.tab3_mian:
                select = 3;
                changeview(select);
                break;
        }
    }

    @OnClick({R.id.info})
    void onclixk(View v){
        switch (v.getId()){
            case R.id.info:
                startActivity(new Intent(TeamListActivity.this, TeamDetailActivity.class));
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        changeview(select);
    }

    private void changeview(int index) {
        tab1_t.setTextColor(Color.parseColor("#333333"));
        tab2_t.setTextColor(Color.parseColor("#333333"));
        tab3_t.setTextColor(Color.parseColor("#333333"));
        tab1_v.setSelected(false);
        tab2_v.setSelected(false);
        tab3_v.setSelected(false);
        if (index == 1) {
            tab1_t.setTextColor(Color.parseColor("#59c2de"));
            tab1_v.setSelected(true);
        } else if (index == 2) {
            tab2_t.setTextColor(Color.parseColor("#59c2de"));
            tab2_v.setSelected(true);
        } else {
            tab3_t.setTextColor(Color.parseColor("#59c2de"));
            tab3_v.setSelected(true);
        }
    }

}
