package com.zhl.huiqu.main.hotelTour;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.hotelTour.adapter.ChooseFragmentPagerAdapter;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

/**
 * 景点详情页 换门票
 * Created by Administrator on 2017/9/22.
 */

public class ChooseTourFragment extends Fragment implements View.OnClickListener {

    TextView next_step;
    TabLayout tabs;
    ViewPager mViewPager;
    private int tag = 0;

    public static ChooseTourFragment newInstance(int tag) {
        Bundle args = new Bundle();
        args.putInt("tag", tag);
        ChooseTourFragment fragment = new ChooseTourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_layout, null);

        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);

        tag = getArguments().getInt("tag");
        next_step = (TextView) view.findViewById(R.id.next_step);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        setViewPager();
        return view;
    }

    private void setViewPager() {
        String[] titles = new String[]{"产品特色", "xiangqing", "yuding"};
        ChooseFragmentPagerAdapter adapter = new ChooseFragmentPagerAdapter(getActivity().getSupportFragmentManager(), titles);
        mViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_step:
                break;
        }
    }
}