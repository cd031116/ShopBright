package com.zhl.huiqu.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.base.StartActivity;

import org.aisen.android.support.inject.OnClick;
import butterknife.Bind;


/**
 * Created by lyj on 2017/8/4.
 */

public class LoginFragment extends BaseFragment{

    @Bind(R.id.login)
    TextView login;

    public static void launch(Activity from){
        ContainerActivity.launch(from, LoginFragment.class, null);
    }
    @Override
    public int inflateContentView() {
        return R.layout.fragment_login;
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

    @OnClick(R.id.login)
    void onclick(View v){
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;
        }
    }


    private void initView() {


    }

}
