package com.zhl.huiqu.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.RegisterActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.PayActivity;
import com.zhl.huiqu.main.bean.DitalTickList;
import com.zhl.huiqu.personal.bean.OrderBean;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.CodeUtils;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.PhoneFormatCheckUtils;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.TimerCount;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.utils.Utils;

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
    @Bind(R.id.fymx_price)
    TextView fymxPriceNum;
    @Bind(R.id.more_time)
    TextView moreTime;
    @Bind(R.id.tomorrow_time)
    TextView tomorrowTime;
    @Bind(R.id.is_login_text)
    TextView isLoginText;
    @Bind(R.id.top_title)
    TextView titleText;
    @Bind(R.id.show_num)
    TextView showNum;
    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.take_pay_total_text)
    TextView totalPrice;
    @Bind(R.id.take_person_free_btn)
    TextView code;

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
    @Bind(R.id.take_person_check_code_layout)
    RelativeLayout takeCheckCodeLayout;

    private DitalTickList mPerson = null;
    private String realCode;
    private boolean isExpande = false;
    private static final int REQUEST_CODE = 0;
    private int type_num = 1;
    private String use_date = null;
    private String memberId = null;
    private String price = null;
    private TimerCount timerCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }

    @Override
    public void initView() {
        super.initView();
        takeCheckCodeLayout.setVisibility(View.GONE);
        mPerson = (DitalTickList) getIntent().getSerializableExtra("pay");
        Log.e("ttt", "initView: " + mPerson.getShop_ticket_id() + "--" + mPerson.getTitle());
        checkCodeImg.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode().toLowerCase();
        fymxLayout.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.getTime().getMonth() + 1;
        int date = calendar.getTime().getDate() + 1;
        tomorrowTime.setText("明天\n" + month + "-" + date);
        titleText.setText(getResources().getString(R.string.write_order));
        if (mPerson != null) {
            price=mPerson.getShop_price();
           String ticket_price = "￥" + mPerson.getShop_price();
            orderPrice.setText(ticket_price);
            fymxPrice.setText(ticket_price);
            totalPrice.setText(ticket_price);
        }
        timerCount = new TimerCount(60000, 1000, code);
    }

    private void settingLoginText() {
        isLoginText.setVisibility(View.VISIBLE);
        isLoginText.setHighlightColor(getResources().getColor(android.R.color.transparent));
        SpannableString spanableInfo = new SpannableString("有账号可先登录");
        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(OrderWriteActivity.this,LoginActivity.class));
            }
        }, 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        isLoginText.setText(spanableInfo);
        isLoginText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initData() {
        super.initData();
        RegisterEntity account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (account != null) {
            isLoginText.setVisibility(View.GONE);
            memberId = account.getBody().getMember_id();
        } else {
            settingLoginText();
        }
    }


    @OnClick({R.id.down_btn, R.id.add_btn, R.id.commit_order_btn, R.id.check_code_img, R.id.fymx_arrow,
            R.id.take_person_free_btn, R.id.more_time, R.id.tomorrow_time, R.id.top_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_btn:
                if (type_num == 1) {
                    showNum.setText("1");
                    fymxPriceNum.setText("*1");
                    totalPrice.setText(mul(price, 1)+"");
                } else if (type_num > 1) {
                    type_num--;
                    showNum.setText(type_num + "");
                    fymxPriceNum.setText("*"+type_num);
                    totalPrice.setText(mul(price, type_num)+"");
                }
                break;
            case R.id.add_btn:
                type_num++;
                showNum.setText(type_num + "");
                fymxPriceNum.setText("*"+type_num);
                totalPrice.setText(mul(price, type_num)+"");
                break;
            case R.id.top_left:
                finish();
                break;
            case R.id.tomorrow_time:
                tomorrowTime.setSelected(true);
                moreTime.setSelected(false);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.getTime().getYear() + 1900;
                int month = calendar.getTime().getMonth() + 1;
                int date = calendar.getTime().getDate() + 1;
                use_date = year + "-" + month + "-" + date;
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
                    new checkCodeTask().execute(phone, Constants.TYPE_ORDER);
                break;
            case R.id.commit_order_btn:
                checkIsNull();
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

    private void checkIsNull() {
        String num = showNum.getText().toString();
        String use_name = nameText.getText().toString().trim();
        String use_card = idCardText.getText().toString().trim();
        String mobile = phoneText.getText().toString().trim();
        String code = codeText.getText().toString().trim();
        String ticket_id = mPerson.getShop_ticket_id();//门票id
        String status = "0";

        if (TextUtils.isEmpty(num))
            ToastUtils.showShortToast(this, "门票数量不能为空");
        else if (TextUtils.isEmpty(use_date))
            ToastUtils.showShortToast(this, "请选择出行日期");
        else if (TextUtils.isEmpty(use_name))
            ToastUtils.showShortToast(this, "请填写姓名");
        else if (TextUtils.isEmpty(use_card))
            ToastUtils.showShortToast(this, "请填写身份证");
        else if (TextUtils.isEmpty(mobile))
            ToastUtils.showShortToast(this, "请填写手机号");
        else if (TextUtils.isEmpty(code))
            ToastUtils.showShortToast(this, "请填写验证码");
        else if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mobile))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_phone));
        else if (!Utils.isIdNum(use_card))
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_id_code));
        else if (code.length() != 6)
            ToastUtils.showShortToast(this, getResources().getString(R.string.register_check_msg_code));
        else if (TextUtils.isEmpty(memberId)) {
            status = "0";
            new commitOrderTask().execute(status, use_date, use_name, use_card, mobile, code, ticket_id, num);
        } else {
            status = "1";
            new commitOrderTask().execute(status, use_date, use_name, use_card, mobile, code, memberId, ticket_id, num);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String time = data.getStringExtra("time");
            moreTime.setText("更多日期\n" + time);
            use_date = time;
            moreTime.setSelected(true);
            tomorrowTime.setSelected(false);
        }
    }

    /**
     * 获取验证码接口
     */
    class checkCodeTask extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(OrderWriteActivity.this).getCode(params[0], params[1]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            timerCount.start();
            TLog.log("tttt", "info=" + info.getMsg());
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 获取验证码接口
     */
    class commitOrderTask extends WorkTask<String, Void, OrderBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("订单提交中...", false);
        }

        @Override
        public OrderBean workInBackground(String... params) throws TaskException {
            if ("0".equals(params[0]))
                return SDK.newInstance(OrderWriteActivity.this).insertOrderInfo(params[0], params[1],
                        params[2], params[3], params[4], params[5], params[6], params[7]);
            else
                return SDK.newInstance(OrderWriteActivity.this).insertOrderInfo(params[0], params[1],
                        params[2], params[3], params[4], params[5], params[6], params[7], params[8]);

        }

        @Override
        protected void onSuccess(OrderBean info) {
            super.onSuccess(info);
            dismissAlert();
            TLog.log("tttt", "info=" + info.getBody().getMobile());
            Intent intent = new Intent(OrderWriteActivity.this, PayActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("body", info.getBody());
            intent.putExtras(mBundle);
            startActivity(intent);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private double mul(String prices, double num) {
        double v = Double.parseDouble(prices);
        double resultMul = v * num;
        return resultMul;
    }

}
