package com.zhl.huiqu.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/10.
 */

public class ResetPswActivity extends BaseActivity {
    @Bind(R.id.register_psw)
    EditText register_psw;
    @Bind(R.id.register_psw_sure)
    EditText register_psw_sure;
    @Bind(R.id.register_code)
    EditText register_code;
    @Bind(R.id.register_phone)
    EditText register_phone;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.register_push_code)
     TextView push_code;
    TimerCount timerCount;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_psw;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText(getResources().getString(R.string.reset_psw));
        timerCount = new TimerCount(60000, 1000, push_code);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.register_area, R.id.reset_account_login, R.id.commit_text, R.id.register_push_code,
            R.id.zhifubao, R.id.wechat, R.id.login_qq, R.id.sina_weibo, R.id.top_image})
    void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_area:
                //TODO 手机地点
                break;
            case R.id.reset_account_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.commit_text:
                commitReset();
                break;
            case R.id.register_push_code:
                String phone = register_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else
                    new checkCodeTask().execute(phone,"resetPassword");
                break;
            case R.id.zhifubao:
                break;
            case R.id.wechat:
                break;
            case R.id.login_qq:
                break;
            case R.id.sina_weibo:
                break;
            case R.id.top_image:
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    /**
     * 判断填写
     */
    private void commitReset() {
        String phone = register_phone.getText().toString().trim();
        String password = register_psw.getText().toString().trim();
        String passwordSure = register_psw_sure.getText().toString().trim();
        String code = register_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
        else if (TextUtils.isEmpty(code))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_msg_code_null));
        else if (TextUtils.isEmpty(password))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_psw_null));
        else if (TextUtils.isEmpty(passwordSure))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_psw_sure_null));
        else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
        else if (code.length() != 6)
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_msg_code));
        else if (password.length() < 6 || password.length() > 6)
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_psw));
        else if (!password.equals(passwordSure))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_psw_is_equals));
        else
            new resetPassword().execute(phone, code, password, passwordSure);
    }

    class resetPassword extends WorkTask<String, Void, String> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }

        @Override
        protected void onSuccess(String info) {
            dismissAlert();
            ToastUtils.showShortToast(ResetPswActivity.this,"重置成功");
           startActivity(new Intent(ResetPswActivity.this,LoginActivity.class));
            ResetPswActivity.this.finish();
        }

        @Override
        public String workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ResetPswActivity.this).forgetPassword(params[0], params[1], params[2], params[3]);
        }
    }

    /**
     * 获取验证码接口
     */
    class checkCodeTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ResetPswActivity.this).getCode(params[0],params[1]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            timerCount.start();
            dismissAlert();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    public class TimerCount extends CountDownTimer {
        private TextView bnt;

        public TimerCount(long millisInFuture, long countDownInterval,
                          TextView bnt) {
            super(millisInFuture, countDownInterval);
            this.bnt = bnt;
        }

        @Override
        public void onFinish() {
            bnt.setClickable(true);
            bnt.setText("重获验证码");
            bnt.setEnabled(true);
        }

        @Override
        public void onTick(long arg0) {
            // if(bnt!=null){
            bnt.setClickable(false);
            bnt.setText("验证码" + arg0 / 1000 + "S");
            bnt.setEnabled(false);
            // }
        }
    }
}
