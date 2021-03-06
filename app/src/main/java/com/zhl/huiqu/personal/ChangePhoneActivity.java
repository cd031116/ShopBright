package com.zhl.huiqu.personal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.base.StartActivity;
import com.zhl.huiqu.login.RegisterActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.TimerCount;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class ChangePhoneActivity extends BaseActivity {

    @Bind(R.id.top_title)
    TextView titleText;
    @Bind(R.id.change_phone)
    TextView changePhone;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.obtain_code_text)
    TextView obtainCodeText;
    @Bind(R.id.phone_text)
    EditText phoneText;
    @Bind(R.id.psw_text)
    EditText pswText;
    private TimerCount timerCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        super.initView();
        timerCount = new TimerCount(60000, 1000, obtainCodeText);
        titleText.setText(getResources().getString(R.string.setting_phone));
        changePhone.setText(getResources().getString(R.string.order_detail));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.obtain_code_text, R.id.save_btn,R.id.top_image})
    void onClick(View view) {
        String phone = phoneText.getText().toString().trim();
        String code = pswText.getText().toString().trim();
        switch (view.getId()) {
            case R.id.obtain_code_text:
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else
                    new checkCodeTask().execute(phone, Constants.TYPE_CHANGEMOBILE);
                break;
            case R.id.save_btn:
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (TextUtils.isEmpty(code))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_msg_code_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else if (code.length() != 6)
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_msg_code));
                else
                    new changeMobileTask().execute(phone, code);
                break;
            case R.id.top_image:
                ChangePhoneActivity.this.finish();
                break;
        }
    }

    /**
     * 获取验证码接口
     */
    class checkCodeTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中...", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangePhoneActivity.this).getCode(params[0], params[1]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            timerCount.start();
            TLog.log("tttt", "info=" + info);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 获取验证码接口
     */
    class changeMobileTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中...", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangePhoneActivity.this).changeMobile(params[0], params[1]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            TLog.log("tttt", "info=" + info);
            if ("1".equals(info.getCode())) {
                startActivity(new Intent(ChangePhoneActivity.this,ChangedPhoneActivity.class));
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

}
