package com.zhl.huiqu.personal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderPayFragment extends Fragment implements View.OnClickListener {
    private TextView ticketToWhere, ticket_type, ticket_type_text, ticket_price, ticket_outing_time_text,
            ticket_in_type_text, price_all_text, refund_layout_text, refund_order_num_text, refund_time_text,
            refund_pay_type_text, order_account_name_text, order_account_phone_text, apply_cancel,
            ticket_num_text, refund_order_goout_text, apply_refund_btn;

    private ImageView refundGzImg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_should_pay, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
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
        apply_refund_btn = (TextView) view.findViewById(R.id.apply_refund_btn);
        apply_cancel.setOnClickListener(this);
        apply_refund_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_cancel:
                break;
            case R.id.apply_refund_btn:
                startActivity(new Intent(getActivity(),OrderWriteActivity.class));
                break;
        }
    }
}
