package com.zhl.huiqu.main;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.GlideImageLoader;

import org.aisen.android.common.utils.Logger;
import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.bean.TabItem;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 2017/8/11.
 */

public class MainTabFragment extends ATabsTabLayoutFragment<TabItem> {
    @ViewInject(id = R.id.banner)
    Banner banner;
    private List<String> images=new ArrayList<>();
    private List<String> titles=new ArrayList<>();

    public static MainTabFragment newInstance() {
        return new MainTabFragment();
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView() {

        return R.layout.main_tab_fragment;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);
        int width = SystemUtils.getScreenWidth(getActivity());
        setBanner();
    }


    @Override
    public void onPause() {
        super.onPause();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected boolean asyncTabInit() {
        return true;
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem("1", "详情"));
        tabs.add(new TabItem("2", "测试"));

        return tabs;
    }

    @Override
    protected void setupTabLayout(Bundle savedInstanceSate) {
        super.setupTabLayout(savedInstanceSate);
        if (getTabItems().size() <= 3) {
            getTablayout().setTabMode(TabLayout.MODE_FIXED);
        }
        getTablayout().setTabTextColors(Color.parseColor("#333333"), Color.parseColor("#00d2ec"));
    }


    @Override
    protected Fragment newFragment(TabItem tabItem) {
        return MainProductListFragment.newInstance(tabItem.getType());
    }

    @Override
    public void requestData() {
//        throw new TaskException(TaskException.TaskError.resultIllegal + "");

        setTabItems(generateTabs());
        setTabInit(null);
    }


    private void setBanner(){
        images.add( "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        images.add( "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        images.add( "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }



}
