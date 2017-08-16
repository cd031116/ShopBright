package com.zhl.huiqu.personal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderGoOutFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_out, container, false);
        SupportMultipleScreensUtil.scale(view);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }
}
