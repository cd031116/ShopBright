package com.zhl.huiqu.personal;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.RegisterActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.login.entity.RegisterInfo;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class SettingActivity extends BaseActivity {

    @Bind(R.id.change_nickname_text)
    TextView nickName;
    @Bind(R.id.setting_email_text)
    TextView emailText;
    @Bind(R.id.setting_sex_text)
    TextView sex;
    @Bind(R.id.setting_phone_text)
    TextView phone;
    @Bind(R.id.setting_name_text)
    TextView name;
    @Bind(R.id.top_title)
    TextView title;

    private String memberId;
    private RegisterInfo registerInfo;
    private static final int REQUEST_CODE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        super.initView();
        memberId = getIntent().getStringExtra("memberId");
        title.setText(getResources().getString(R.string.personal_setting));
    }

    @Override
    public void initData() {
        super.initData();
        new settingTask().execute(memberId);
    }

    @OnClick({R.id.change_nickname, R.id.setting_name, R.id.setting_psw, R.id.setting_phone, R.id.setting_email, R.id.top_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_nickname:
                Intent nickIntent = new Intent(this, ChangeNicknameActivity.class);
                nickIntent.putExtra("memberId", memberId);
                startActivityForResult(nickIntent, REQUEST_CODE);
                break;
            case R.id.setting_name:
                startActivity(new Intent(this, ChangeNameActivity.class));
                break;
            case R.id.setting_psw:
                Intent pswIntent = new Intent(this, ChangePswActivity.class);
                pswIntent.putExtra("memberId", memberId);
                startActivityForResult(pswIntent, REQUEST_CODE);
                break;
            case R.id.setting_phone:
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.setting_email:
                Intent emailIntent = new Intent(this, ChangeEmailActivity.class);
                emailIntent.putExtra("memberId", memberId);
                emailIntent.putExtra("phone", registerInfo.getMobile());
                startActivityForResult(emailIntent, REQUEST_CODE);
                break;
            case R.id.top_left:
                finish();
                break;
        }
    }

    class settingTask extends WorkTask<String, Void, RegisterEntity> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在提交..", false);
        }

        @Override
        public RegisterEntity workInBackground(String... params) throws TaskException {
            return SDK.newInstance(SettingActivity.this).personalSetting(params[0]);
        }

        @Override
        protected void onSuccess(RegisterEntity info) {
            super.onSuccess(info);
            dismissAlert();
            settingView(info.getBody());
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void settingView(RegisterInfo data) {
        registerInfo = data;
        nickName.setText(data.getNickname());
        emailText.setText(data.getEmail());
        phone.setText(data.getMobile());
        if ("0".equals(data.getSex()))
            sex.setText("男");
        if ("1".equals(data.getSex()))
            sex.setText("女");
        name.setText(data.getNickname());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("ttt", "onActivityResult: " + resultCode);

        if (resultCode == 1) {
            String nick_name = data.getStringExtra("nickname");
            Log.e("ttt", "onActivityResult: " + nick_name);
            nickName.setText(nick_name);
        } else if (resultCode == 2) {
            String change_psw = data.getStringExtra("change_psw");
            ToastUtils.showShortToast(SettingActivity.this, change_psw);
        } else if (resultCode == 3) {
            String email = data.getStringExtra("email");
            emailText.setText(email);
            ToastUtils.showShortToast(SettingActivity.this, getResources().getString(R.string.setting_email_success));
        }
    }
}
