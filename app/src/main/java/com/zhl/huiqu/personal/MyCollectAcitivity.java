package com.zhl.huiqu.personal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/8/11.
 */

public class MyCollectAcitivity extends BaseActivity {
    @Bind(R.id.my_collect_list)
    RecyclerView my_collect_list;
    @Bind(R.id.his_is_null)
    TextView his_is_null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        super.initView();
        my_collect_list.setVisibility(View.GONE);
        his_is_null.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
