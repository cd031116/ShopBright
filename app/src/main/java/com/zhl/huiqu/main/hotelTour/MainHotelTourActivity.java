package com.zhl.huiqu.main.hotelTour;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.bean.HotelTourEntity;
import com.zhl.huiqu.main.hotelTour.adapter.HotelRecommendAdapter;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.widget.GlideImageLoader;
import com.zhl.huiqu.widget.calendar.CalendarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 跟团游首页
 * <p>
 * Created by Administrator on 2017/9/21.
 */

public class MainHotelTourActivity extends BaseActivity {
    @Bind(R.id.line_back)
    LinearLayout back;
    @Bind(R.id.searh_line)
    RelativeLayout searh_line;
    @Bind(R.id.info)
    ImageView info;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.hotel_recommend_more)
    TextView hotel_recommend_more;
    @Bind(R.id.hotel_recy)
    RecyclerView hotel_recy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_hotel_tour;
    }

    @Override
    public void initView() {
        super.initView();
        setBanner();
        setRecommendList();
    }

    String[] images = {"http://wechats.zhonghuilv.net/uploads/news/20170816/79e72b36ab76f3636f106aa73ef31f8b.jpg",
            "http://wechats.zhonghuilv.net/uploads/news/20170816/4182a75d53f3840d4603f5182f4b1336.jpg",
            "http://wechats.zhonghuilv.net/uploads/news/20170816/318794afd204a178db3320c69e54e530.jpg"};

    /**
     * 设置轮播图
     */
    private void setBanner() {

        List<String> titles = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            titles.add(images[i]);
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
                startActivity(new Intent(MainHotelTourActivity.this, HotelDetailActivity.class));
            }
        });
    }

    /**
     * 设置精选推荐
     */
    private void setRecommendList() {
        List<HotelTourEntity> hotelTourList = new ArrayList<>();
        HotelTourEntity hotelTourEntity = new HotelTourEntity();
        hotelTourEntity.setTitle("爱离开饭店和会计法皇室典范卡收到后付款了案说法了哈里森");
        hotelTourEntity.setImage("http://wechats.zhonghuilv.net/uploads/news/20170816/4182a75d53f3840d4603f5182f4b1336.jpg");
        hotelTourEntity.setPl_num("144");
        hotelTourEntity.setSatisfied("70%");
        hotelTourEntity.setPrice("1000");
        hotelTourList.add(hotelTourEntity);
        HotelTourEntity hotelTourEntity1 = new HotelTourEntity();
        hotelTourEntity1.setTitle("结婚了时代风格就回来的解放路了；\n可兑换啊；离开国际；案例四大家观看了；几个卡了；地方");
        hotelTourEntity1.setImage("http://wechats.zhonghuilv.net/uploads/news/20170816/318794afd204a178db3320c69e54e530.jpg");
        hotelTourEntity1.setPl_num("144");
        hotelTourEntity1.setSatisfied("60%");
        hotelTourEntity1.setPrice("54444");
        hotelTourList.add(hotelTourEntity1);
        HotelRecommendAdapter adapter = new HotelRecommendAdapter(MainHotelTourActivity.this, hotelTourList);
        hotel_recy.setLayoutManager(new MyLinearLayoutManager(this));
        hotel_recy.setAdapter(adapter);
        adapter.setOnItemClickListener(new HotelRecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @OnClick({R.id.line_back, R.id.hotel_driving, R.id.hotel_follow, R.id.hotel_spring, R.id.hotel_park,
            R.id.hotel_zhagnjiajie, R.id.hotel_city, R.id.hotel_parenting, R.id.hotel_all})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_back:
                finish();
                break;
            case R.id.hotel_driving:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.hotel_follow:
                break;
            case R.id.hotel_spring:
                break;
            case R.id.hotel_park:
                break;
            case R.id.hotel_zhagnjiajie:
                break;
            case R.id.hotel_city:
                break;
            case R.id.hotel_parenting:
                break;
            case R.id.hotel_all:
                break;
        }

    }
}