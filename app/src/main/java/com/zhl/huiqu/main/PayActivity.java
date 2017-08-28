package com.zhl.huiqu.main;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.Consts;
import com.zhl.huiqu.bean.WeiChatBean;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.bean.DitalTickList;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.MapUtil;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToBuyUtils;
import com.zhl.huiqu.widget.RushBuyCountDownTimerView;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

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

    private int select = 1;
    private  OrderEntity mPerson;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        super.initView();
         mPerson = (OrderEntity) getIntent().getSerializableExtra("body");
        if (mPerson != null) {
            price.setText("￥" + mPerson.getOrder_total());
            order_num.setText("订单号:" + mPerson.getOrder_sn());
            order_name.setText("产品名称:" + mPerson.getName());
            order_time.setText("游玩时间:" + mPerson.getUse_date());
            order_money.setText("订单金额:" + mPerson.getOrder_total() + "(在线支付)");
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
            case R.id.zfb_rela:
                if (select == 1) {
                    return;
                }
                select = 1;
                setview(select);
                break;
            case R.id.wechat_rela:
                if (select == 2) {
                    return;
                }
                select = 2;
                setview(select);
                break;
            case R.id.submit:
                if(mPerson!=null){
                    new PayOrderTask().execute();
                }
                break;
        }
    }


    private void setview(int index) {
        image_zfb.setImageResource(R.drawable.order_pay_gray_gou);
        iamge_wechat.setImageResource(R.drawable.order_pay_gray_gou);
        if (index == 1) {
            image_zfb.setImageResource(R.drawable.order_pay_red_gou);
        } else {
            iamge_wechat.setImageResource(R.drawable.order_pay_red_gou);
        }
    }


    /*
  *
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
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_PRODUCT_ID,mPerson.getOrder_sn());
            //支付的金钱
            MapUtil.sharedInstance().putDefaultValue(Constants.PAY_MONEY, mPerson.getOrder_total());
            return SDK.newInstance(PayActivity.this).getPrePayOrder(mPerson.getName(),mPerson.getOrder_sn(), mPerson.getOrder_total());
        }

        @Override
        protected void onSuccess(WeiChatBean info) {
            super.onSuccess(info);
            dismissAlert();
            if(info.getBody()!=null){
                ToBuyUtils.lunchWeChat(PayActivity.this, Consts.PayType.Pay_Product_Buy,info.getBody());
            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            dismissAlert();
            Toast.makeText(PayActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
