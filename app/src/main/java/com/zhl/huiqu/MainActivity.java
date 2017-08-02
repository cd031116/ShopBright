package com.zhl.huiqu;

import android.view.KeyEvent;

import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.DoubleClickExitHelper;

public class MainActivity extends BaseActivity {
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        mDoubleClickExit=new DoubleClickExitHelper(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
