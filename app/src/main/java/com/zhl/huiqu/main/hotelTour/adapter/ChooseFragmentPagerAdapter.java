package com.zhl.huiqu.main.hotelTour.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhl.huiqu.main.hotelTour.ChooseHotelFragment;
import com.zhl.huiqu.main.hotelTour.ChooseTourFragment;
import com.zhl.huiqu.main.hotelTour.GoodsFragment;

/**
 * Created by dawn4it on 2017/6/28.
 */

public class ChooseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;

    public ChooseFragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return GoodsFragment.newInstance(1);
        else
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
