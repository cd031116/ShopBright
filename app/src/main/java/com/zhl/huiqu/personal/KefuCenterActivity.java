package com.zhl.huiqu.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.sdk.permission.CallPhonePermissionAction;

import org.aisen.android.support.action.IAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 客服中心
 * Created by dw on 2017/8/11.
 */

public class KefuCenterActivity extends BaseActivity {

    @Bind(R.id.kefu_list)
    RecyclerView recyclerView;
    @Bind(R.id.top_title)
    TextView titleText;
    private KefuRecyclerViewAdapter mRecyclerViewAdapter;
    List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kefu_center;
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setVisibility(View.VISIBLE);
        titleText.setText(getResources().getString(R.string.personal_kefu_center));
    }

    @Override
    public void initData() {
        super.initData();
        String string = getResources().getString(R.string.call_kefu_phone);
        list.add(string);
        String string1 = getResources().getString(R.string.call_kefu_phone);
        list.add(string1);
        mRecyclerViewAdapter = new KefuRecyclerViewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick({R.id.call_kefu, R.id.top_image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_kefu:
                Log.e("tttt", "onClick: call_kefu");
                if (KefuCenterActivity.this instanceof org.aisen.android.ui.activity.basic.BaseActivity) {
                    org.aisen.android.ui.activity.basic.BaseActivity aisenBaseActivity =
                            (org.aisen.android.ui.activity.basic.BaseActivity) KefuCenterActivity.this;

                    new IAction(aisenBaseActivity, new CallPhonePermissionAction(aisenBaseActivity,
                            null)) {
                        @Override
                        public void doAction() {
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18656666666"));//跳转到拨号界面
                            startActivity(dialIntent);
                        }
                    }.run();
                }
                break;
            case R.id.top_image:
                finish();
                break;
        }
    }

}
