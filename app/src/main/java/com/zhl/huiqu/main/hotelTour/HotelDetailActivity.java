package com.zhl.huiqu.main.hotelTour;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HotelDetailActivity extends BaseActivity {


    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_tour_detail;
    }

    @Override
    public void initView() {
        super.initView();
        setBanner();
        setViewPager();
    }

    private void setViewPager() {
        String[] titles = new String[]{"产品特色","xiangqing","yuding"};
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(),titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }
}
