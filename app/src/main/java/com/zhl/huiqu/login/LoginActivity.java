package com.zhl.huiqu.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.sdk.SDK;


import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.top_layout)
    RelativeLayout top_layout;

    @Bind(R.id.top_left)
    LinearLayout top_left;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.login)
    TextView login;
    @Bind(R.id.tab1)
    TextView tab1;
    @Bind(R.id.tab2)
    TextView tab2;
    //one
    @Bind(R.id.shouji)
    EditText shouji;

    //one
    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.view1)
    LinearLayout view1;
    @Bind(R.id.view2)
    LinearLayout view2;
    @Bind(R.id.code)
    TextView code;//获取验证码
    @Bind(R.id.top_image)
    ImageView top_image;

    TimerCount timerCount;
    private int select = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        super.initView();
        top_layout.setVisibility(View.GONE);
        top_title.setText("登录");
        timerCount = new TimerCount(60000, 1000, code);
//        top_image.setImageResource(R.drawable.login_close);
        top_image.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();
        tab1.setSelected(true);
        tab2.setSelected(false);
    }

    @OnClick({R.id.login, R.id.tab1, R.id.tab2, R.id.code, R.id.top_left, R.id.forget, R.id.register})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.top_left:
                LoginActivity.this.finish();
                break;
            case R.id.login:
                  startActivity(new Intent(LoginActivity.this,MainActivity.class));
//               if (select == 1) {
//                    String co = account.getText().toString();
//                    String psd = password.getText().toString();
//                    if (TextUtils.isEmpty(co)) {
//                        Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    if (TextUtils.isEmpty(psd)) {
//                        Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    new LoginTask().execute();
//                } else {
//                    String acou=shouji.getText().toString();
//                    String code_=code.getText().toString();
//                    if (TextUtils.isEmpty(acou)) {
//                        Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    if (TextUtils.isEmpty(code_)) {
//                        Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    new LoginTask().execute();
//                }
                break;
            case R.id.tab1:
                if (select == 1) {
                    return;
                }
                select = 1;
                tab1.setSelected(true);
                tab2.setSelected(false);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                break;
            case R.id.tab2:
                if (select == 2) {
                    return;
                }
                select = 2;
                tab1.setSelected(false);
                tab2.setSelected(true);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.code:
                String s = shouji.getText().toString();
                if (s == null || TextUtils.isEmpty(s)) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    break;
                }
                new GetCodeTask().execute();
                break;
            case R.id.forget:
                startActivity(new Intent(LoginActivity.this, ResetPswActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }


    /*
  * 发送验证码
  * */
    class GetCodeTask extends WorkTask<Void, Void, String> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
        }

        @Override
        public String workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(LoginActivity.this).getCode(account.getText().toString());
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            timerCount.start();
            String code = info.toString();
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            Toast.makeText(LoginActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
*登录
* */
    class LoginTask extends WorkTask<Void, Void, String> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在登陆..", false);
        }

        @Override
        public String workInBackground(Void... voids) throws TaskException {
            String accounst = account.getText().toString();
            String psd = password.getText().toString();
            //2
            String acou=shouji.getText().toString();
            String code_=code.getText().toString();
            if(select==1){
                return SDK.newInstance(LoginActivity.this).login(accounst, psd, "0", "");
            }else {
                return SDK.newInstance(LoginActivity.this).login(acou, "", "1", code_);
            }
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            dismissAlert();
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            dismissAlert();
            Toast.makeText(LoginActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (tab1.isSelected()) {
            select = 1;
        } else {
            select = 2;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

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
