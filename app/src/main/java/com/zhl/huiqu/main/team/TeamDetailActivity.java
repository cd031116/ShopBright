package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.hotelTour.HotelDetailActivity;
import com.zhl.huiqu.main.hotelTour.MainHotelTourActivity;
import com.zhl.huiqu.main.team.bean.LikeEntity;
import com.zhl.huiqu.main.team.bean.TeamBase;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.main.team.bean.TeamDetailEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.widget.GlideImageLoader;
import com.zhl.huiqu.widget.MyScroview;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamDetailActivity extends BaseActivity implements MyScroview.OnScrollListener {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.myscroview)
    MyScroview myscroview;
    @Bind(R.id.search02)
    LinearLayout search02;// 在MyScrollView里面的购买布局
    @Bind(R.id.search01)
    LinearLayout search01;

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tab_mian)
    LinearLayout tab_mian;
    @Bind(R.id.like_list)
    RecyclerView like_list;
    //切换
    @Bind(R.id.contents)
    TextView contents;
    @Bind(R.id.group_price)
    TextView group_price;
    @Bind(R.id.tese_content)
    TextView tese_content;
    @Bind(R.id.w_text)
    TextView w_text;
    @Bind(R.id.a_text)
    TextView a_text;
    @Bind(R.id.contain_text)
    TextView contain_text;
    @Bind(R.id.zili_text)
    TextView zili_text;
    @Bind(R.id.fp_text)
    TextView fp_text;
    @Bind(R.id.fu_text)
    TextView fu_text;
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
    private int select = 1;
    private int topHeight;
    private String spot_team_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_detail;
    }

    @Override
    public void initView() {
        super.initView();
        myscroview.setOnScrollListener(this);
        spot_team_id = getIntent().getStringExtra("spot_team_id");
    }

    @Override
    public void initData() {
        new obtainGroupDetail().execute();
//        new obtainGroupDetail().execute();
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        changeview(select);
        top_title.setText("详情");
        image.setVisibility(View.VISIBLE);
        image.setImageResource(R.drawable.fenxiang);
    }

    @OnClick({R.id.top_left, R.id.image, R.id.tab1_mian, R.id.tab2_mian, R.id.tab3_mian, R.id.submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                TeamDetailActivity.this.finish();
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
            case R.id.submit:
                startActivity(new Intent(TeamDetailActivity.this, TeamOrderActivity.class));
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            topHeight = search02.getBottom() - search02.getHeight();
        }
    }

    /*列表数据
   * */
    class obtainGroupDetail extends WorkTask<Void, Void, TeamDetailEntity> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中。。", false);
        }

        @Override
        public TeamDetailEntity workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamDetailActivity.this).obtainGroupDetail(spot_team_id);
        }

        @Override
        protected void onSuccess(TeamDetailEntity info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getCode().equals("1")) {
                Log.e("ddd", "onSuccess: " + true);
                setView(info.getBody());
                topHeight = search02.getBottom() - search02.getHeight();
            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 列表数据
     **/
    class obtainLike extends WorkTask<Void, Void, LikeEntity> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中。。", false);
        }

        @Override
        public LikeEntity workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamDetailActivity.this).obtainLike();
        }

        @Override
        protected void onSuccess(LikeEntity info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getCode().equals("1")) {
                Log.e("ddd", "onSuccess: " + true);

            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * @param body
     */
    private void setView(TeamDetailBean body) {
        group_price.setText(body.getGoods().getShop_price());
        tese_content.setText(body.getGoods().getDesc());
        contents.setText(body.getGoods().getTitle());
        w_text.setText(body.getGoods().getPrompt());
        a_text.setText(body.getGoods().getSecur());
        contain_text.setText(body.getGoods().getT_cost());
        zili_text.setText(body.getGoods().getP_cost());
        fu_text.setText(body.getGoods().getReserve());
        fp_text.setText(body.getGoods().getInvo());
        setBanner(body);
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

    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= topHeight) {
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
    }

    /**
     * 设置轮播图
     *
     * @param body
     */
    private void setBanner(final TeamDetailBean body) {

        List<String> titles = new ArrayList<>();
        if (body.getGoodsImg() != null && body.getGoodsImg().size() > 0) {
            for (int i = 0; i < body.getGoodsImg().size(); i++) {
                titles.add("http://www.zhonghuilv.net" + body.getGoodsImg().get(i).getImg());
            }
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(titles);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
//        设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("ttt", "OnBannerClick: " + body.getGoodsImg().get(position).getTeam_img_id());
            }
        });
    }
}