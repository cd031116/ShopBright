package com.zhl.huiqu.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;

import org.aisen.android.support.inject.ViewInject;


/**
 * Created by lyj on 2017/8/4.
 */

public class LoginFragment extends BaseFragment{
    TextView webView;

    public static void launch(Activity from){
        ContainerActivity.launch(from, LoginFragment.class, null);
    }
    @Override
    public int inflateContentView() {
        return 0;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }






    private void initView() {


    }

}
