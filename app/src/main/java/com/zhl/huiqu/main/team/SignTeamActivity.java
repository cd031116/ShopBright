package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.PayActivity;
import com.zhl.huiqu.main.team.bean.InsuranceBase;
import com.zhl.huiqu.main.team.bean.OrderBuyBase;
import com.zhl.huiqu.main.team.bean.OrderPut;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import butterknife.Bind;
import butterknife.OnClick;

public class SignTeamActivity extends BaseActivity {
    @Bind(R.id.submit)
    TextView submit;
    private OrderPut info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_team;
    }

    @Override
    public void initView() {
        super.initView();
         info = (OrderPut)getIntent().getSerializableExtra("info");
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.submit,R.id.top_left})
    void click(View v) {
        switch (v.getId()) {
            case R.id.submit:
                new getOrderTask().execute();
                break;
            case R.id.top_left:
                SignTeamActivity.this.finish();
                break;
        }
    }

    /*
        * */
    class getOrderTask extends WorkTask<Void, Void, OrderBuyBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载...", true);
        }

        @Override
        public OrderBuyBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(SignTeamActivity.this).CreateOrder(info);
        }

        @Override
        protected void onSuccess(OrderBuyBase info) {
            super.onSuccess(info);
            dismissAlert();
            if(info.getBody()!=null){
                OrderEntity mPerson=new OrderEntity();
                mPerson.setOrder_total(info.getBody().getAmoutPrice());
                mPerson.setOrder_sn(info.getBody().getOrderSn());
                mPerson.setName(info.getBody().getProductName());
                mPerson.setUse_date(info.getBody().getDepartureTime());
                if(!TextUtils.isEmpty(info.getBody().getOrderId())){
                    mPerson.setOrder_id(Integer.parseInt(info.getBody().getOrderId()));
                }
                Intent intent = new Intent(SignTeamActivity.this, PayActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("body", mPerson);
                intent.putExtra("type","team");
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
            ToastUtils.showShortToast(SignTeamActivity.this,""+exception.getMessage());
        }
    }
}
