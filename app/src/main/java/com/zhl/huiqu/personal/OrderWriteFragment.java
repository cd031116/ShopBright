package com.zhl.huiqu.personal;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.CodeUtils;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

/**
 * Created by dw on 2017/8/15.
 */

public class OrderWriteFragment extends BaseFragment {

    @ViewInject(id = R.id.check_code_img)
    ImageView checkCodeImg;
    @ViewInject(id = R.id.fymx_arrow)
    ImageView fymxArrow;
    @ViewInject(id = R.id.fymx_layout)
    RelativeLayout fymxLayout;
    @ViewInject(id = R.id.fymx_type)
    TextView fymxType;
    @ViewInject(id = R.id.fymx_price_text)
    TextView fymxPrice;

    @ViewInject(id = R.id.take_person_name_text)
    EditText nameText;
    @ViewInject(id = R.id.take_person_id_card_text)
    EditText idCardText;
    @ViewInject(id = R.id.take_person_phone_text)
    EditText phoneText;
    @ViewInject(id = R.id.take_person_code_text)
    EditText codeText;
    @ViewInject(id = R.id.check_code_text)
    EditText checkCodeText;

    private String realCode;
    private boolean isExpande = false;

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode().toLowerCase();
        fymxLayout.setVisibility(View.GONE);
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_write_order;
    }

    public static void launch(Activity from) {
        ContainerActivity.launch(from, OrderWriteFragment.class, null);
    }

    @OnClick({R.id.down_btn, R.id.add_btn, R.id.commit_order_btn, R.id.check_code_img, R.id.fymx_arrow,
            R.id.take_person_free_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_btn:
                break;
            case R.id.add_btn:
                break;
            case R.id.take_person_free_btn:
                String phone = phoneText.getText().toString().trim();
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.register_phone_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.register_phone));
                else
                    new checkCodeTask().execute(phone);
                break;
            case R.id.commit_order_btn:

                break;
            case R.id.fymx_arrow:
                if (isExpande) {
                    isExpande = false;
                    fymxLayout.setVisibility(View.GONE);
                    fymxArrow.setImageResource(R.drawable.order_write_up);
                } else {
                    isExpande = true;
                    fymxLayout.setVisibility(View.VISIBLE);
                    fymxArrow.setImageResource(R.drawable.order_write_down);
                }
                break;
            case R.id.check_code_img:
                checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
                realCode = CodeUtils.getInstance().getCode().toLowerCase();
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
            return SDK.newInstance(getActivity()).getCode(params[0]);
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
