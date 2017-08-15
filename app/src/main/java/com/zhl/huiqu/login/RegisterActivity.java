package com.zhl.huiqu.login;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.http.Params;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/9.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.register_psw)
    EditText register_psw;
    @Bind(R.id.register_code)
    EditText register_code;
    @Bind(R.id.register_phone)
    EditText register_phone;
    @Bind(R.id.register_area)
    TextView register_area;
    @Bind(R.id.register_psw_show)
    TextView register_psw_show;
    @Bind(R.id.commit_text)
    TextView commit_text;
    @Bind(R.id.register_push_code)
    TextView register_push_code;
    @Bind(R.id.zhifubao)
    TextView zhifubao;
    @Bind(R.id.wechat)
    TextView wechat;
    @Bind(R.id.login_qq)
    TextView login_qq;
    @Bind(R.id.sina_weibo)
    TextView sina_weibo;
    @Bind(R.id.top_title)
    TextView top_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText(getResources().getString(R.string.register_title));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.register_area, R.id.register_psw_show, R.id.commit_text, R.id.register_push_code,
            R.id.zhifubao, R.id.wechat, R.id.login_qq, R.id.sina_weibo, R.id.has_account, R.id.top_image})
    void onClick(View view) {
        String phone = register_phone.getText().toString().trim();
        String password = register_psw.getText().toString().trim();
        String code = register_code.getText().toString().trim();
        switch (view.getId()) {
            case R.id.register_area:
                //TODO 手机地点
                break;
            case R.id.commit_text:
                //TODO 提交注册
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (TextUtils.isEmpty(code))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_msg_code_null));
                else if (TextUtils.isEmpty(password))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_psw_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else if (code.length() != 6)
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_msg_code));
                else if (password.length() < 6 || password.length() > 6)
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_psw));
                else
                    new commitTask().execute(phone, code, password);
                break;
            case R.id.register_push_code:
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else
                    new checkCodeTask().execute(phone);
                break;
            case R.id.register_psw_show:
                showOrHindPsw();
                break;
            case R.id.zhifubao:
                break;
            case R.id.wechat:
                break;
            case R.id.login_qq:
                break;
            case R.id.sina_weibo:
                break;
            case R.id.has_account:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
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
     * 获取验证码接口
     */
    class checkCodeTask extends WorkTask<String, Void, String> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public String workInBackground(String... params) throws TaskException {
            return SDK.newInstance(RegisterActivity.this).getCheckCode(params[0]);
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            dismissAlert();
            TLog.log("tttt", "info=" + info);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    class commitTask extends WorkTask<String, Void, String> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public String workInBackground(String... params) throws TaskException {
            return SDK.newInstance(RegisterActivity.this).registCommit(params[0], params[1], params[2]);
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            dismissAlert();
            TLog.log("tttt", "info=" + info);

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 显示或者隐藏密码
     */
    private void showOrHindPsw() {
        if (register_psw.getInputType() == 129) {
            // 显示密码  其实就是setInputType（144）
            register_psw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // 隐藏密码  其实就是setInputType（129）
            register_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
