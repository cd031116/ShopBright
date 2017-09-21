package com.zhl.huiqu.main.hotelTour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dawn4it on 2017/6/28.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles ;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return GoodsFragment.newInstance(1);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
