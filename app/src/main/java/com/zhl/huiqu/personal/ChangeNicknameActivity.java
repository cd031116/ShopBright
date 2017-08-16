package com.zhl.huiqu.personal;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
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

public class ChangeNicknameActivity extends BaseActivity {

    @Bind(R.id.top_title)
    TextView titleText;
    @Bind(R.id.name_text)
    EditText nameText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_nickname;
    }

    @Override
    public void initView() {
        super.initView();
        titleText.setText(getResources().getString(R.string.personal_setting_nick));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.top_image, R.id.save_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_image:
                finish();
                break;
            case R.id.save_btn:
                checkIsNull();
                break;
        }
    }

    private void checkIsNull() {
        String nameEdit = nameText.getText().toString().trim();
        if (TextUtils.isEmpty(nameEdit))
            ToastUtils.showShortToast(this, getResources().getString(R.string.setting_name_write));
        else
            new commitTask().execute(nameEdit);
    }

    //TODO 保存名字
    class commitTask extends WorkTask<String, Void, String> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public String workInBackground(String... params) throws TaskException {
            return SDK.newInstance(ChangeNicknameActivity.this).changePsw(params[0], params[1]);
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
}
