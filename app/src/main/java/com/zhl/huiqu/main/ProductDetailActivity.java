package com.zhl.huiqu.main;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.widget.MyScroview;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lyj on 2017/8/12.
 */

public class ProductDetailActivity extends BaseActivity implements MyScroview.OnScrollListener {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.image)
    ImageView soucang;
    @Bind(R.id.image_t)
    ImageView fenxiang;
    @Bind(R.id.myscroview)
    MyScroview myscroview;
    @Bind(R.id.search02)
    LinearLayout search02;// 在MyScrollView里面的购买布局
    @Bind(R.id.search01)
    LinearLayout search01;

    @Bind(R.id.top_layout)
    RelativeLayout top_layout;
    @Bind(R.id.tab_mian)
    LinearLayout tab_mian;


    //切换
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

    //
    @Bind(R.id.jd_js)
    RelativeLayout jd_js;//景点介绍
    @Bind(R.id.crp_line)
    LinearLayout crp_line;//成人票


    private int select = 1;
    private int topHeight;
    private int jd_hight = -1;//景点介绍的高度
    private int crp_hight = -1;//成人票的高度
    private int tab_hight;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_profuct;
    }

    @Override
    public void initView() {
        super.initView();
        changeview(1);
        myscroview.setOnScrollListener(this);
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
                tpscoll(crp_hight);
                break;
            case R.id.tab2_mian:
                select = 2;
                changeview(select);
                tpscoll(jd_hight);
                break;

//            case R.id.tab3_mian:
//                select = 3;
//                changeview(select);
//                break;
        }
    }

    private void changeview(int index) {
        tab1_t.setTextColor(Color.parseColor("#333333"));
        tab2_t.setTextColor(Color.parseColor("#333333"));
        tab3_t.setTextColor(Color.parseColor("#333333"));
        tab1_v.setSelected(false);
        tab2_v.setSelected(false);
        tab3_v.setSelected(false);
        if (index == 1) {
            tab1_t.setTextColor(Color.parseColor("#e11818"));
            tab1_v.setSelected(true);
        } else if (index == 2) {
            tab2_t.setTextColor(Color.parseColor("#e11818"));
            tab2_v.setSelected(true);
        } else {
            tab3_t.setTextColor(Color.parseColor("#e11818"));
            tab3_v.setSelected(true);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;//状态栏高度
//        int titleBarHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//标题栏高度
//        topHeight = titleBarHeight + statusBarHeight;
        if (hasFocus) {

            topHeight = search02.getBottom() - search02.getHeight();
            gethight();
        }

    }

    @Override
    public void onScroll(int y) {
        Log.i("tttt", "yyyyyyyyy=" + y);
        if (y >= topHeight) {
            if (tab_mian.getParent() != search01) {
                search02.removeView(tab_mian);
                search01.addView(tab_mian);
            }
        } else {
            if (tab_mian.getParent() != search02) {
                search01.removeView(tab_mian);
                search02.addView(tab_mian);
            }
        }

        if (y < jd_hight) {
            select = 1;
            changeview(select);
        } else {
            select = 2;
            changeview(select);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        topHeight = search02.getBottom() - search02.getHeight();
        gethight();
    }

    private void tpscoll(int y) {
        if (y == -1) {
            return;
        }
        myscroview.scrollTo(0, y);
    }

    private void gethight() {
        int[] position = new int[2];
        crp_line.getLocationOnScreen(position);
        crp_hight = position[1] - top_layout.getHeight() - getStatusBarHeight();

        int[] position1 = new int[2];
        jd_js.getLocationOnScreen(position1);
        jd_hight = position1[1] - search02.getHeight() - top_layout.getHeight() - getStatusBarHeight();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
