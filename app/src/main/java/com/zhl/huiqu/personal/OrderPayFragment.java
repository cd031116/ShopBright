package com.zhl.huiqu.personal;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.PayActivity;
import com.zhl.huiqu.personal.bean.OrderDetailBean;
import com.zhl.huiqu.personal.bean.OrderDetailEntity;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.CommomDialog;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.ShowMsgDialog;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderPayFragment extends BaseFragment {
    private TextView ticketToWhere, ticket_type, ticket_type_text, ticket_price, ticket_outing_time_text,
            ticket_in_type_text, price_all_text, refund_layout_text, refund_order_num_text, refund_time_text,
            refund_pay_type_text, order_account_name_text, order_account_phone_text, apply_cancel,
            ticket_num_text, refund_order_goout_text, order_pay_btn;
    private RelativeLayout huiqu_tel_layout;

    private ImageView refundGzImg;
    private String orderId;
    private OrderEntity entity;
    private boolean isCancel = false;

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
        return R.layout.fragment_should_pay;
    }

    private void initView(View view) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        RegisterEntity registerInfo = SaveObjectUtils.getInstance(getActivity()).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (bundle != null) {
            orderId = bundle.getString("order_sn");
        }
        ticketToWhere = (TextView) view.findViewById(R.id.order_to_where);
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
        apply_cancel = (TextView) view.findViewById(R.id.apply_cancel);
        order_pay_btn = (TextView) view.findViewById(R.id.order_pay_btn);
        apply_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder();
            }
        });
        order_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitOrder();
            }
        });
        new getOrderinfoTask().execute(registerInfo.getBody().getMember_id(), orderId);
    }

    private void commitOrder() {
        if (isCancel)
            ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.order_is_cancel));
        else {
            if (entity != null) {
                Intent intent = new Intent(getActivity(), PayActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("body", entity);
                intent.putExtras(mBundle);
                intent.putExtra("type","ticket");
                startActivity(intent);
            } else
                ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.order_detail_msg));
        }
    }

    private void cancelOrder() {
        if (isCancel)
            ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.order_is_cancel));
        else {
            if (!TextUtils.isEmpty(orderId))
                new CommomDialog(getActivity(), R.style.progress_dialog, "您确定取消此订单？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            new cancelOrder().execute(orderId);
                        }
                    }
                })
                        .setTitle("提示").show();
        }
    }


    class getOrderinfoTask extends WorkTask<String, Void, OrderDetailBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中..", false);
        }

        @Override
        public OrderDetailBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getOrderinfo(params[0], params[1]);
        }

        @Override
        protected void onSuccess(OrderDetailBean info) {
            super.onSuccess(info);
            dismissAlert();
            Log.e("tttt", "onSuccess: info.getPrice():" + info.getBody().getPrice());
            settingView(info.getBody());
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    class cancelOrder extends WorkTask<String, Void, BaseInfo> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在取消订单", false);
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).cancelOrder(params[0]);
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            if ("1".equals(info.getCode())) {
                isCancel = true;
                refund_order_goout_text.setText("已取消");
                ToastUtils.showShortToast(getActivity(), "该订单已成功取消");
            } else
                ToastUtils.showShortToast(getActivity(), info.getMsg());
            Log.e("ttt", "onSuccess: info.getPrice():" + info.getMsg());
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    private void settingView(OrderDetailEntity info) {
        Log.e("ttt", "settingView: " + info.getAdd_time());
        ticketToWhere.setText(info.getSpot_name());
        ticket_type.setText(info.getName());
        ticket_price.setText("￥" + info.getPrice());
        ticket_outing_time_text.setText(info.getUse_date());
        ticket_num_text.setText(info.getNum() + "张");
        ticket_in_type_text.setText(info.getTake());
        price_all_text.setText("￥" + info.getOrder_total());
        refund_order_num_text.setText(info.getOrder_sn());
        refund_time_text.setText(timedate(info.getAdd_time() + ""));
        refund_order_goout_text.setText("待付款");
        refund_pay_type_text.setText(info.getPay_way());
        order_account_name_text.setText(info.getUse_name());
        order_account_phone_text.setText(info.getMobile());
        entity = new OrderEntity();
        entity.setOrder_total(info.getOrder_total());
        entity.setOrder_sn(info.getOrder_sn());
        entity.setName(info.getName());
        entity.setUse_date(info.getUse_date());
        entity.setOrder_id(info.getOrder_id());
    }

    public String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd ");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
}
