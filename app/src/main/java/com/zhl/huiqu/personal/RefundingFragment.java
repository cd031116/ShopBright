package com.zhl.huiqu.personal;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhl.huiqu.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/12.
 */

public class RefundingFragment extends Fragment {


    private RefundLisener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refunding, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("退出");
//                mListener.onFragmentInteraction("refundingFragment");
                this.cancel();
            }
        }, 2000);//五百毫秒
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RefundLisener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RefundLisener");
        }
    }
}
