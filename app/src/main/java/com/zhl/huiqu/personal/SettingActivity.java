package com.zhl.huiqu.personal;

import android.content.Intent;
import android.view.View;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.change_nickname, R.id.setting_name, R.id.setting_psw, R.id.setting_phone, R.id.setting_email})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_nickname:
                startActivity(new Intent(this, ChangeNicknameActivity.class));
                break;
            case R.id.setting_name:
                startActivity(new Intent(this, ChangeNameActivity.class));
                break;
            case R.id.setting_psw:
                startActivity(new Intent(this, ChangePswActivity.class));
                break;
            case R.id.setting_phone:
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.setting_email:
                startActivity(new Intent(this, ChangeEmailActivity.class));
                break;
        }
    }
}
