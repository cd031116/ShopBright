package com.zhl.huiqu.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.Consts;
import com.zhl.huiqu.bean.WeiChatBean;
import com.zhl.huiqu.main.team.bean.AlipayBase;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.MapUtil;
import com.zhl.huiqu.utils.ToBuyUtils;
import com.zhl.huiqu.widget.RushBuyCountDownTimerView;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.count_down)
    RushBuyCountDownTimerView count_down;

    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.image_zfb)
    ImageView image_zfb;
    @Bind(R.id.iamge_wechat)
    ImageView iamge_wechat;
    @Bind(R.id.order_num)
    TextView order_num;
    @Bind(R.id.order_name)
    TextView order_name;
    @Bind(R.id.order_time)
    TextView order_time;
    @Bind(R.id.order_money)
    TextView order_money;
    @Bind(R.id.pay_total)
    TextView pay_total;
    private String type="";
    private int select = 1;
    private OrderEntity mPerson;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            type=bd.getString("type");
        }


        mPerson = (OrderEntity) getIntent().getSerializableExtra("body");
        if (mPerson != null) {
            price.setText("￥" + mPerson.getOrder_total());
            order_num.setText("订单号:" + mPerson.getOrder_sn());
            order_name.setText("产品名称:" + mPerson.getName());
            order_time.setText("游玩时间:" + mPerson.getUse_date());
            order_money.setText("订单金额:" + mPerson.getOrder_total() + "(在线支付)");
            pay_total.setText("￥" + mPerson.getOrder_total());
            long time = 1000 * 60 * 30;
            count_down.setTime(time);
            count_down.start();
        }
        top_title.setText("订单支付");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.top_left, R.id.zfb_rela, R.id.wechat_rela, R.id.submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                PayActivity.this.finish();
                break;
            case R.id.wechat_rela:
                if (select == 1) {
                    return;
                }
                select = 1;
                setview(select);
                break;
            case R.id.zfb_rela:
                if (select == 2) {
                    return;
                }
                select = 2;
                setview(select);
                break;
            case R.id.submit:
                if (mPerson != null) {
                    if (select == 1) {
                        new PayOrderTask().execute();
                    } else {
                        new aliPayOrderTask().execute();
                    }
                }
                break;
        }
    }


    private void setview(int index) {
        image_zfb.setImageResource(R.drawable.order_pay_gray_gou);
        iamge_wechat.setImageResource(R.drawable.order_pay_gray_gou);
        if (index == 1) {
            iamge_wechat.setImageResource(R.drawable.order_pay_red_gou);
        } else {
            image_zfb.setImageResource(R.drawable.order_pay_red_gou);
        }
    }


    /*
  *微信下单
  * */
    class PayOrderTask extends WorkTask<Void, Void, WeiChatBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在购买..", false);
        }

        @Override
        public WeiChatBean workInBackground(Void... voids) throws TaskException {
            //产品编号
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_PRODUCT_ID, mPerson.getOrder_sn());
            MapUtil.sharedInstance().putDefaultValue(Constants.ORDER_ID, mPerson.getOrder_id());
            //支付的金钱
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_MONEY, mPerson.getOrder_total());
            return SDK.newInstance(PayActivity.this).getPrePayOrder(mPerson.getName(), mPerson.getOrder_sn(),type);
        }

        @Override
        protected void onSuccess(WeiChatBean info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null) {
                ToBuyUtils.lunchWeChat(PayActivity.this, Consts.PayType.Pay_Product_Buy, info.getBody());
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            dismissAlert();
            Toast.makeText(PayActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


/*支付宝下单接口
* */

    class aliPayOrderTask extends WorkTask<Void, Void, AlipayBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在购买..", false);
        }

        @Override
        public AlipayBase workInBackground(Void... voids) throws TaskException {
            //产品编号
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_PRODUCT_ID, mPerson.getOrder_sn());
            MapUtil.sharedInstance().putDefaultValue(Constants.ORDER_ID, mPerson.getOrder_id());
            //支付的金钱
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_MONEY, mPerson.getOrder_total());
            return SDK.newInstance(PayActivity.this).getAliPay(mPerson.getOrder_sn());
        }

        @Override
        protected void onSuccess(AlipayBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info != null) {
                Log.i("hhhh","info="+info.getBody().getResponse());
                Topay(info.getBody().getResponse());
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            dismissAlert();
            Toast.makeText(PayActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void Topay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

}
