package com.zhl.huiqu.personal;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.personal.bean.AllOrderEntity;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;

/**
 * Created by dw on 2017/8/14.
 */

public class OrderItemView extends ARecycleViewItemView<AllOrderEntity> {

    public static final int LAYOUT_RES = R.layout.item_all_order_list;

    @ViewInject(id = R.id.all_order_ticket_state)
    TextView ticketState;
    @ViewInject(id = R.id.order_ticket_type)
    TextView ticketWhere;
    @ViewInject(id = R.id.order_ticket_type_text)
    TextView ticketType;
    @ViewInject(id = R.id.order_ticket_number_text)
    TextView ticketNum;
    @ViewInject(id = R.id.order_goout_time_text)
    TextView ticketTime;
    @ViewInject(id = R.id.order_price)
    TextView ticketPrice;
    @ViewInject(id = R.id.order_ticket_num_text)
    TextView orderNum;

    public OrderItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        SupportMultipleScreensUtil.scale(convertView);
        int width = SystemUtils.getScreenWidth(getContext());
//        imgCover.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Math.round(360 * 1.0f / 750 * width)));
    }

    @Override
    public void onBindData(View view, AllOrderEntity allOrderEntity, int i) {

        try {
            String status = null;
            if (allOrderEntity.getStatus()==0)
                status = "未付款";
            else if (allOrderEntity.getStatus()==1)
                status = "已支付";
            else if (allOrderEntity.getStatus()==5)
                status = "已核销";
            else if (allOrderEntity.getStatus()==6)
                status="已完成";
            ticketState.setText(status);
            ticketWhere.setText(allOrderEntity.getProduct_name());
//            ticketType.setText(orderEntity.getTicketType());
            ticketNum.setText(allOrderEntity.getProduct_num() + "");
            ticketTime.setText(allOrderEntity.getUse_date());
            ticketPrice.setText(allOrderEntity.getTotal() + "");
            orderNum.setText(allOrderEntity.getOrder_sn());
        } catch (Exception e) {
            Log.i("tttt", "tname=" + e.toString());
        }


        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
