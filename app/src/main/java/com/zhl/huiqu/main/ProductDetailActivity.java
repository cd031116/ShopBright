package com.zhl.huiqu.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.ABaseFragment;

import butterknife.Bind;

/**
 * Created by lyj on 2017/8/12.
 */

public class ProductDetailActivity extends BaseActivity {
    @Bind( R.id.top_title)
    TextView top_title;
    @Bind( R.id.image)
    ImageView soucang;
    @Bind(R.id.image_t)
    ImageView fenxiang;

    //切换
    @Bind(R.id.tab1_t)
    TextView tab1_t;
    @Bind(R.id.tab1_v)
    TextView tab1_v;
    @Bind(R.id.tab2_t)
    TextView tab2_t;
    @Bind(R.id.tab2_v)
    TextView tab2_v;
    @Bind( R.id.tab3_t)
    TextView tab3_t;
    @Bind( R.id.tab3_v)
    TextView tab3_v;
    private int select = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_profuct;
    }

    @Override
    public void initView() {
        super.initView();
        changeview(1);
        top_title.setText("产品详情");
        soucang.setVisibility(View.VISIBLE);
        fenxiang.setVisibility(View.VISIBLE);
        soucang.setBackgroundResource(R.drawable.mpxq_sc);
        fenxiang.setBackgroundResource(R.drawable.mpxq_black_fx);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.top_left, R.id.image, R.id.image_t, R.id.tab1_mian, R.id.tab2_mian, R.id.tab3_mian})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                ProductDetailActivity.this.finish();
                break;
            case R.id.image:

                break;
            case R.id.image_t:

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

    private void changeview(int index) {
        tab1_t.setTextColor(Color.parseColor("#333333"));
        tab2_t.setTextColor(Color.parseColor("#333333"));
        tab3_t.setTextColor(Color.parseColor("#333333"));
        tab1_v.setSelected(false);
        tab2_v.setSelected(false);
        tab3_v.setSelected(false);
        if(index==1){
            tab1_t.setTextColor(Color.parseColor("#e11818"));
            tab1_v.setSelected(true);
        }else if(index==2){
            tab2_t.setTextColor(Color.parseColor("#e11818"));
            tab2_v.setSelected(true);
        }else {
            tab3_t.setTextColor(Color.parseColor("#e11818"));
            tab3_v.setSelected(true);
        }

    }

}
