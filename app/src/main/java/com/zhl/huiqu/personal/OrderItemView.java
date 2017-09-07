package com.zhl.huiqu.personal;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.personal.bean.AllOrderEntity;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;

/**
 * Created by dw on 2017/8/14.
 */

public class OrderItemView extends ARecycleViewItemView<AllOrderEntity> {

    public static final int LAYOUT_RES = R.layout.item_all_order_list;
    private OrderItemInterface orderItemInterface;
    private Activity context;

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
    @ViewInject(id = R.id.order_delete_text)
    TextView orderDeleteText;
    private AllOrderEntity allOrderEntity;

    public OrderItemView(Activity context, View itemView, OrderItemInterface orderItemInterface) {
        super(context, itemView);
        this.context = context;
        this.orderItemInterface = orderItemInterface;

    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        SupportMultipleScreensUtil.scale(convertView);
        int width = SystemUtils.getScreenWidth(getContext());
    }

    @OnClick({R.id.order_delete_text})
    void onClick(View view) {
        int position = (int) view.getTag();
        if (allOrderEntity != null) {
            new deleteOrder().execute(allOrderEntity.getOrder_id() + "", position + "");
        }
    }


    class deleteOrder extends WorkTask<String, Void, BaseInfo> {
        int positions = 0;

        @Override
        protected void onPrepare() {
            super.onPrepare();
        }

        @Override
        public BaseInfo workInBackground(String... params) throws TaskException {
            positions = Integer.parseInt(params[1]);
            return SDK.newInstance(context).deleteOrder(params[0]);

        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            if ("1".equals(info.getCode())) {
                ToastUtils.showShortToast(context, "该订单已成功删除");
                orderItemInterface.orderItemClick(positions);
            } else
                ToastUtils.showShortToast(context, info.getMsg());
        }

        @Override
        protected void onFailure(TaskException exception) {
        }
    }


    @Override
    public void onBindData(View view, AllOrderEntity allOrderEntity, int i) {
        orderDeleteText.setTag(i);
        this.allOrderEntity = allOrderEntity;
        try {
            String status = null;
            if (allOrderEntity.getStatus() == 0)
                status = "未付款";
            else if (allOrderEntity.getStatus() == 1)
                status = "已支付";
            else if (allOrderEntity.getStatus() == 3)
                status = "已取消";
            else if (allOrderEntity.getStatus() == 5)
                status = "已核销";
            else if (allOrderEntity.getStatus() == 6)
                status = "已完成";
            ticketState.setText(status);
            ticketWhere.setText(allOrderEntity.getName());
//            ticketType.setText(orderEntity.getTicketType());
            ticketNum.setText("(" + allOrderEntity.getNum() + "张)");
            ticketTime.setText(allOrderEntity.getUse_date());
            ticketPrice.setText("￥" + allOrderEntity.getPrice());
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
