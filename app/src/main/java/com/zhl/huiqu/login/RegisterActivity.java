package com.zhl.huiqu.login;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/9.
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.register_area,R.id.register_psw_show, R.id.commit_text, R.id.register_push_code,
            R.id.zhifubao, R.id.wechat, R.id.login_qq, R.id.sina_weibo})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_area:
                //TODO 手机地点
                break;
            case R.id.commit_text:
                //TODO 提交注册
                break;
            case R.id.register_push_code:
                //TODO 发送验证码
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
            default:
                finish();
                break;
        }
    }

    /**
     * 显示或者隐藏密码
     */
    private void showOrHindPsw() {
        if (register_psw.getInputType()==129) {
            // 显示密码  其实就是setInputType（144）
            register_psw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // 隐藏密码  其实就是setInputType（129）
            register_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
