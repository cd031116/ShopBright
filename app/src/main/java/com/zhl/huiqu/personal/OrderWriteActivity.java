package com.zhl.huiqu.personal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.CodeUtils;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/15.
 */

public class OrderWriteActivity extends BaseActivity {

    @Bind(R.id.check_code_img)
    ImageView checkCodeImg;
    @Bind(R.id.fymx_arrow)
    ImageView fymxArrow;
    @Bind(R.id.fymx_layout)
    RelativeLayout fymxLayout;
    @Bind(R.id.fymx_type)
    TextView fymxType;
    @Bind(R.id.fymx_price_text)
    TextView fymxPrice;
    @Bind(R.id.more_time)
    TextView moreTime;
    @Bind(R.id.tomorrow_time)
    TextView tomorrowTime;

    @Bind(R.id.take_person_name_text)
    EditText nameText;
    @Bind(R.id.take_person_id_card_text)
    EditText idCardText;
    @Bind(R.id.take_person_phone_text)
    EditText phoneText;
    @Bind(R.id.take_person_code_text)
    EditText codeText;
    @Bind(R.id.check_code_text)
    EditText checkCodeText;

    private String realCode;
    private boolean isExpande = false;
    private static final int REQUEST_CODE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }

    @Override
    public void initView() {
        super.initView();
        checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode().toLowerCase();
        fymxLayout.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.getTime().getMonth() + 1;
        int date = calendar.getTime().getDate() + 1;
        tomorrowTime.setText("明天\n" + month + "-" + date);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.down_btn, R.id.add_btn, R.id.commit_order_btn, R.id.check_code_img, R.id.fymx_arrow,
            R.id.take_person_free_btn, R.id.more_time,R.id.tomorrow_time})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_btn:
                break;
            case R.id.add_btn:
                break;
            case R.id.tomorrow_time:
                tomorrowTime.setSelected(true);
                moreTime.setSelected(false);
                break;
            case R.id.more_time:
                startActivityForResult(new Intent(this, MoreCalendarActivity.class), REQUEST_CODE);
                break;
            case R.id.take_person_free_btn:
                String phone = phoneText.getText().toString().trim();
                if (TextUtils.isEmpty(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone_null));
                else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone))
                    ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
                else
                    new checkCodeTask().execute(phone, Constants.TYPE_ORDER );
                break;
            case R.id.commit_order_btn:
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra("order_state", getResources().getString(R.string.personal_out_order));
                startActivity(intent);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String time = data.getStringExtra("time");
            moreTime.setText("更多日期\n" + time);
            moreTime.setSelected(true);
            tomorrowTime.setSelected(false);
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
            return SDK.newInstance(OrderWriteActivity.this).getCode(params[0],params[1]);
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
