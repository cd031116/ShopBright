package com.zhl.huiqu.main.team;

import android.app.Fragment;
import android.app.FragmentManager;

import com.zhl.huiqu.main.MainProductListFragment;

import org.aisen.android.ui.fragment.adapter.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyFragmentPageAdapter  extends FragmentPagerAdapter {
    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TeamListFragment.newInstance("0");
            case 1:
                return TeamListFragment.newInstance("1");
            case 2:
                return TeamListFragment.newInstance("2");
            default:
                return null;
        }
    }

    @Override
    protected String makeFragmentName(int i) {
        return null;
    }
}
