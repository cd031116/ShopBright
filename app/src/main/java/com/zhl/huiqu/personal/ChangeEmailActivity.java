package com.zhl.huiqu.personal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class ChangeEmailActivity extends BaseActivity {


    @Bind(R.id.top_title)
    TextView titleText;
    @Bind(R.id.email_edit)
    EditText emailEdit;
    @Bind(R.id.code_edit)
    EditText codeEdit;
    private String memberId;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_eamil;
    }

    @Override
    public void initView() {
        super.initView();
        titleText.setText(getResources().getString(R.string.change_setting_email));
    }

    @Override
    public void initData() {
        super.initData();

        memberId = getIntent().getStringExtra("memberId");
        phone = getIntent().getStringExtra("phone");
    }

    @OnClick({R.id.top_image, R.id.save_btn, R.id.obtain_code})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_image:
                finish();
                break;
            case R.id.save_btn:
                checkIsNull();
                break;
            case R.id.obtain_code:
                new obtainCodeTask().execute(phone, "changeEmail");
                break;
        }
    }

    private void checkIsNull() {
        String emailEditText = emailEdit.getText().toString().trim();
        String codeEditText = codeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(emailEditText))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_email_null));
        else if (TextUtils.isEmpty(codeEditText))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_code_null));
        else if (codeEditText.length() != 6)
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_msg_code));
        else
            new commitTask().execute(emailEditText, phone, codeEditText, memberId);
    }

    //TODO 保存邮箱
    class commitTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在提交..", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangeEmailActivity.this).changeEmail(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            TLog.log("tttt", "info=" + info);

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private class obtainCodeTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在提交..", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangeEmailActivity.this).getCode(params[0], params[1]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            if ("1".equals(info.getCode())) {
                Intent intent = new Intent();
                intent.putExtra("email", emailEdit.getText().toString().trim());
                setResult(3, intent);
                finish();
            } else {
                ToastUtils.showShortToast(ChangeEmailActivity.this, info.getMsg());
            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }
}
