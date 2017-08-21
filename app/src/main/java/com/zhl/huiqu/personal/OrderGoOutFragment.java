package com.zhl.huiqu.personal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import butterknife.OnClick;

/**
 * Created by dw on 2017/8/16.
 */

public class OrderGoOutFragment extends Fragment {

    TextView apply_refund_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_out, container, false);
        SupportMultipleScreensUtil.scale(view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        apply_refund_btn=(TextView) view.findViewById(R.id.apply_refund_btn);
        apply_refund_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ApplyRefundActivity.class));
            }
        });
    }


}
