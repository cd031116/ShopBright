package com.zhl.huiqu.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

public class AddUserInfoActivity extends BaseActivity {

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.number)
    EditText number;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.youxiang)
    EditText youxiang;

    @Bind(R.id.man)
    ImageView man;
    @Bind(R.id.woman)
    ImageView woman;
    private RegisterEntity account;
    private String se_t = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user_info;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        man.setSelected(true);
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
    }


    @OnClick({R.id.submit, R.id.man, R.id.woman, R.id.ledt_image})
    void onclix(View v) {
        switch (v.getId()) {
            case R.id.ledt_image:
                AddUserInfoActivity.this.finish();
                break;
            case R.id.submit:
                String name_t = name.getText().toString();
                String id_card = number.getText().toString();
                String t_phone = phone.getText().toString();
                if (TextUtils.isEmpty(name_t)) {
                    ToastUtils.showShortToast(this, "请输入名字");
                    break;
                }
                if (TextUtils.isEmpty(id_card)) {
                    ToastUtils.showShortToast(this, "请输入身份证号码");
                    break;
                }
                if (TextUtils.isEmpty(t_phone)) {
                    ToastUtils.showShortToast(this, "请输入手机号");
                    break;
                }
                new getTopTask().execute();
                break;
            case R.id.man:
                setselect(0);
                break;
            case R.id.woman:
                setselect(1);
                break;
        }

    }

    private void setselect(int index) {
        man.setSelected(false);
        woman.setSelected(false);
        if(index==0){
            man.setSelected(true);
        }else {
            woman.setSelected(true);
        }
    }


    /*数据
  * */
    class getTopTask extends WorkTask<Void, Void, String> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在提交..", true);
        }

        @Override
        public String workInBackground(Void... voids) throws TaskException {
            String name_t = name.getText().toString();
            String id_card = number.getText().toString();
            String t_phone = phone.getText().toString();
            String emails = youxiang.getText().toString();
            if (man.isSelected()) {
                se_t = "男";
            } else {
                se_t = "女";
            }
            return SDK.newInstance(AddUserInfoActivity.this).addContact(account.getBody().getMember_id(), name_t, id_card, t_phone, se_t, emails, "0", "");
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            dismissAlert();
            ToastUtils.showShortToast(AddUserInfoActivity.this, "提交成功");
            AddUserInfoActivity.this.finish();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }
}