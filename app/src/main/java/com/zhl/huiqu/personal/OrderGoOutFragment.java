package com.zhl.huiqu.personal;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.personal.bean.OrderDetailBean;
import com.zhl.huiqu.personal.bean.OrderDetailEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.permission.CallPhonePermissionAction;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.action.IAction;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.OnClick;

/**
 * Created by dw on 2017/8/16.
 */

public class OrderGoOutFragment extends BaseFragment implements View.OnClickListener {
    private TextView ticketToWhere, ticket_type, ticket_type_text, ticket_price, ticket_outing_time_text,
            ticket_in_type_text, price_all_text, refund_layout_text, refund_order_num_text, refund_time_text,
            refund_pay_type_text, order_account_name_text, order_account_phone_text, huiqu_tel_text,
            ticket_num_text, refund_order_goout_text, apply_refund_btn;

    private String orderId;
    private ImageView refundGzImg;
    private RelativeLayout huiqu_tel_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
        initView(view);
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_go_out;
    }

    private void initView(View view) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        RegisterEntity registerInfo = SaveObjectUtils.getInstance(getActivity()).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (bundle != null) {
            orderId = bundle.getString("order_sn");
        }
        ticketToWhere = (TextView) view.findViewById(R.id.order_where);
        ticket_type = (TextView) view.findViewById(R.id.ticket_type);
        ticket_type_text = (TextView) view.findViewById(R.id.ticket_type_text);
        ticket_price = (TextView) view.findViewById(R.id.ticket_price);
        ticket_outing_time_text = (TextView) view.findViewById(R.id.ticket_outing_time_text);
        ticket_num_text = (TextView) view.findViewById(R.id.ticket_num_text);
        ticket_in_type_text = (TextView) view.findViewById(R.id.ticket_in_type_text);
        price_all_text = (TextView) view.findViewById(R.id.price_all_text);
        refundGzImg = (ImageView) view.findViewById(R.id.refund_gz_img);
        refund_layout_text = (TextView) view.findViewById(R.id.refund_layout_text);
        refund_order_num_text = (TextView) view.findViewById(R.id.refund_order_num_text);
        refund_time_text = (TextView) view.findViewById(R.id.refund_time_text);
        refund_order_goout_text = (TextView) view.findViewById(R.id.refund_order_goout_text);
        refund_pay_type_text = (TextView) view.findViewById(R.id.refund_pay_type_text);
        order_account_name_text = (TextView) view.findViewById(R.id.order_account_name_text);
        order_account_phone_text = (TextView) view.findViewById(R.id.order_account_phone_text);
        huiqu_tel_text = (TextView) view.findViewById(R.id.huiqu_tel_text);
        apply_refund_btn = (TextView) view.findViewById(R.id.apply_refund_btn);
        apply_refund_btn.setOnClickListener(this);
        huiqu_tel_text.setOnClickListener(this);
        new getOrderinfoTask().execute(registerInfo.getBody().getMember_id(), orderId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.huiqu_tel_text:
                callKefu();
                break;
            case R.id.apply_refund_btn:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
        }
    }

    class getOrderinfoTask extends WorkTask<String, Void, OrderDetailBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载中..", false);
        }

        @Override
        public OrderDetailBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getOrderinfo(params[0], params[1]);
        }

        @Override
        protected void onSuccess(OrderDetailBean info) {
            super.onSuccess(info);
            dismissAlert();
            Log.e("ttt", "onSuccess: info.getPrice():" + info.getBody().getPrice());
            settingView(info.getBody());
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void settingView(OrderDetailEntity info) {
        ticketToWhere.setText(info.getSpot_name());
        ticket_type.setText(info.getName());
        ticket_price.setText("￥" + info.getPrice());
        ticket_outing_time_text.setText(info.getUse_date());
        ticket_num_text.setText(info.getNum() + "张");
        ticket_in_type_text.setText(info.getTake());
        price_all_text.setText("￥" + info.getOrder_total());
        refund_order_num_text.setText(info.getOrder_sn());
        refund_time_text.setText(timedate(info.getAdd_time() + ""));
        refund_order_goout_text.setText("待出行");
        refund_pay_type_text.setText(info.getPay_way());
        order_account_name_text.setText(info.getUse_name());
        order_account_phone_text.setText(info.getMobile());
        Log.e("ttt", "settingView: " + info.getPay_way());
    }

    public String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd ");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    private void callKefu() {
        if (getActivity() instanceof org.aisen.android.ui.activity.basic.BaseActivity) {
            org.aisen.android.ui.activity.basic.BaseActivity aisenBaseActivity =
                    (org.aisen.android.ui.activity.basic.BaseActivity) getActivity();

            new IAction(aisenBaseActivity, new CallPhonePermissionAction(aisenBaseActivity,
                    null)) {
                @Override
                public void doAction() {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getResources().getString(R.string.order_account_phone_text)));//跳转到拨号界面
                    startActivity(dialIntent);
                }
            }.run();
        }
    }
}
