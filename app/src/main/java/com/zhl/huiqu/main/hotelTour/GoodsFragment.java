package com.zhl.huiqu.main.hotelTour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.bean.HotelTourEntity;
import com.zhl.huiqu.main.hotelTour.adapter.HotelRecommendAdapter;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class GoodsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private RecyclerView lv;

    public static GoodsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        GoodsFragment pageFragment = new GoodsFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_viewpager, null);

        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);
        lv = (RecyclerView) view.findViewById(R.id.lv);
        // 创建一个线性布局管理器

        List<HotelTourEntity> hotelTourList = new ArrayList<>();
        HotelTourEntity hotelTourEntity = new HotelTourEntity();
        hotelTourEntity.setTitle("爱离开饭店和会计法皇室典范卡收到后付款了案说法了哈里森");
        hotelTourEntity.setImage("http://wechats.zhonghuilv.net/uploads/news/20170816/4182a75d53f3840d4603f5182f4b1336.jpg");
        hotelTourEntity.setPl_num("144");
        hotelTourEntity.setSatisfied("70%");
        hotelTourEntity.setPrice("1000");
        hotelTourList.add(hotelTourEntity);
        hotelTourList.add(hotelTourEntity);
        HotelTourEntity hotelTourEntity1 = new HotelTourEntity();
        hotelTourEntity1.setTitle("结婚了时代风格就回来的解放路了；\n可兑换啊；离开国际；案例四大家观看了；几个卡了；地方");
        hotelTourEntity1.setImage("http://wechats.zhonghuilv.net/uploads/news/20170816/318794afd204a178db3320c69e54e530.jpg");
        hotelTourEntity1.setPl_num("144");
        hotelTourEntity1.setSatisfied("60%");
        hotelTourEntity1.setPrice("54444");
        hotelTourList.add(hotelTourEntity1);
        HotelRecommendAdapter adapter = new HotelRecommendAdapter(getActivity(), hotelTourList);
        lv.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        lv.setAdapter(adapter);
        return view;
    }
}