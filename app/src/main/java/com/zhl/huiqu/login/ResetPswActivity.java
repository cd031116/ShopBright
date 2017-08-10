package com.zhl.huiqu.login;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/10.
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
    @Bind(R.id.register_area)
    TextView register_area;
    @Bind(R.id.commit_text)
    TextView commit_text;
    @Bind(R.id.reset_account_login)
    TextView reset_account_login;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_psw;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.register_area, R.id.reset_account_login, R.id.commit_text, R.id.register_push_code,
            R.id.zhifubao, R.id.wechat, R.id.login_qq, R.id.sina_weibo})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_area:
                //TODO 手机地点
                break;
            case R.id.reset_account_login:
                //TODO 跳转登录页面
                break;
            case R.id.commit_text:
                //TODO 提交注册
                checkIsNull();

                ToastUtils.showShortToast(this,getResources().getString(R.string.register_phone_null));
                break;
            case R.id.register_push_code:
                checkIsNull();

                //TODO 发送验证码
                break;
            case R.id.zhifubao:
                break;
            case R.id.wechat:
                break;
            case R.id.login_qq:
                break;
            case R.id.sina_weibo:
                break;
            default:
                finish();
                break;
        }
    }

    /**
     * 判断填写
     */
    private void checkIsNull() {
        String psw=register_psw.toString().trim();
        String pswSure=register_psw_sure.toString().trim();
        String phoneNum=register_phone.toString().trim();
        String msgCode=register_code.toString().trim();
        if (TextUtils.isEmpty(phoneNum))
            ToastUtils.showShortToast(this,getResources().getString(R.string.register_phone_null));
        if (TextUtils.isEmpty(msgCode))
            ToastUtils.showShortToast(this,getResources().getString(R.string.register_msg_code_null));
        if (TextUtils.isEmpty(psw))
            ToastUtils.showShortToast(this,getResources().getString(R.string.register_psw_null));
        if (TextUtils.isEmpty(pswSure))
            ToastUtils.showShortToast(this,getResources().getString(R.string.register_psw_sure_null));
    }
}
