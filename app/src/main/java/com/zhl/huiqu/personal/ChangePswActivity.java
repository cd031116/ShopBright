package com.zhl.huiqu.personal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.RegisterActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class ChangePswActivity extends BaseActivity {

    @Bind(R.id.top_title)
    TextView titleText;
    @Bind(R.id.old_psw)
    EditText oldPsw;
    @Bind(R.id.new_psw)
    EditText newPsw;
    @Bind(R.id.new_sure_psw)
    EditText newSurePsw;
    private String memberId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_psw;
    }

    @Override
    public void initView() {
        super.initView();
        titleText.setText(getResources().getString(R.string.personal_setting_psw));
    }

    @Override
    public void initData() {
        super.initData();
        memberId = getIntent().getStringExtra("memberId");
    }

    @OnClick({R.id.top_image, R.id.save_text})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_image:
                finish();
                break;
            case R.id.save_text:
                checkIsNull();
                break;
        }
    }

    private void checkIsNull() {
        String oldPswStr = oldPsw.getText().toString().trim();
        String newPswStr = newPsw.getText().toString().trim();
        String newSurePswStr = newSurePsw.getText().toString().trim();
        if (TextUtils.isEmpty(oldPswStr))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_old_psw_null));
        else if (TextUtils.isEmpty(newPswStr))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_new_psw_null));
        else if (TextUtils.isEmpty(newSurePswStr))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_new_sure_null));
        else if (!newPswStr.equals(newSurePswStr))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_psw_is_equals));
        else if (newPswStr.length() < 6 || newPswStr.length() > 16)
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_psw));
        else
            new commitTask().execute(oldPswStr, newPswStr, newSurePswStr, memberId);
    }

    //TODO 保存密码
    class commitTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangePswActivity.this).changePsw(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            if ("1".equals(info.getCode())) {
                Intent intent = new Intent();
                intent.putExtra("change_psw", "密码修改成功");
                setResult(2, intent);
                finish();
            } else {
                ToastUtils.showShortToast(ChangePswActivity.this, info.getMsg());
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

}
