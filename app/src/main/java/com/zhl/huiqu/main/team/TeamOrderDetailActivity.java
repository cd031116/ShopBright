package com.zhl.huiqu.main.team;

import android.util.Log;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.team.bean.OrderDetailBase;
import com.zhl.huiqu.personal.bean.OrderDetailBean;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

public class TeamOrderDetailActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_order_detail;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        RegisterEntity registerInfo = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        new getOrderinfoTask(registerInfo.getBody().getMember_id()).execute();
    }


    class getOrderinfoTask extends WorkTask<String, Void, OrderDetailBase> {
        private String member_id;
        public getOrderinfoTask(String member_id ){
            this.member_id=member_id;
        }
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中..", false);
        }

        @Override
        public OrderDetailBase workInBackground(String... params) throws TaskException {
            return SDK.newInstance(TeamOrderDetailActivity.this).getOrderInfo(member_id,"");
        }

        @Override
        protected void onSuccess(OrderDetailBase info) {
            super.onSuccess(info);
            dismissAlert();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }
}
