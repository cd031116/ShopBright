package com.zhl.huiqu.personal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhl.huiqu.R;

/**
 * Created by Administrator on 2017/8/12.
 */

public class RefundApplyFragment extends Fragment {
    //成人票
    TextView ticketTypeText;
    TextView orderTicketType;
    TextView ticketOutingTimeText;
    //门票数量
    TextView ticketNumText;
    TextView orderTicketNumText;
    //价格
    TextView ticketPriceText;
    TextView refundKnowText;
    TextView commitApply;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private RefundLisener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refund_apply, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ticketPriceText = (TextView) view.findViewById(R.id.order_price);
        ticketOutingTimeText = (TextView) view.findViewById(R.id.order_goout_time_text);
        ticketTypeText = (TextView) view.findViewById(R.id.ticket_type_text);
        orderTicketType = (TextView) view.findViewById(R.id.order_ticket_type);
        orderTicketNumText = (TextView) view.findViewById(R.id.order_ticket_num_text);
        ticketNumText = (TextView) view.findViewById(R.id.ticket_num_text);
        refundKnowText = (TextView) view.findViewById(R.id.refund_know_text);
        commitApply = (TextView) view.findViewById(R.id.commit_refund_apply);
        checkBox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkbox4);
        commitApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction("refundApplyFragment");
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RefundLisener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RefundLisener");
        }
    }

}
