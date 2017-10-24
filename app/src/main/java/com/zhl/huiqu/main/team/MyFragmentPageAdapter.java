package com.zhl.huiqu.main.team;

import android.app.Fragment;
import android.app.FragmentManager;

import com.zhl.huiqu.main.MainProductListFragment;
import com.zhl.huiqu.main.TickListFragment;

import org.aisen.android.ui.fragment.adapter.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyFragmentPageAdapter  extends FragmentPagerAdapter {
    private String desCityId;

    public MyFragmentPageAdapter(FragmentManager fm,String desCityId) {
        super(fm);
        this.desCityId=desCityId;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TeamListFragment.newInstance(desCityId);
            case 1:
                return TickListFragment.newInstance(desCityId);
            default:
                return null;
        }
    }

    @Override
    protected String makeFragmentName(int i) {
        return null;
    }
}
