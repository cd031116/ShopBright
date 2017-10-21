package com.zhl.huiqu.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.personal.bean.UsePerList;
import com.zhl.huiqu.personal.bean.UsePersonBean;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.UseInfoRefreshEvent;
import com.zhl.huiqu.sdk.eventbus.UseInfoSubscriber;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ChooseTourerActivity extends BaseActivity {
    @Bind(R.id.choose_tour_list)
    RecyclerView recycleview;
    private RegisterEntity account;
    private CommonAdapter<UsePerList> mAdapter;
    private List<UsePerList> madatas;
    private int normalChooseNum=0;
    @Bind(R.id.top_left_text)
    TextView title;
    @Bind(R.id.choose_normal_num)
    TextView choose_normal_num;
    @Bind(R.id.top_right_text)
    TextView label;

    @Override
    protected int getLayoutId() {
        return R.layout.choose_tourer;
    }

    @Override
    public void initView() {
        super.initView();
        NotificationCenter.defaultCenter().subscriber(UseInfoRefreshEvent.class, freshEvent);
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        label.setText("新增游客");
        label.setTextColor(Color.BLACK);
        title.setTextColor(Color.BLACK);
        title.setText("选择常用游客");
    }

    @Override
    public void initData() {
        super.initData();
        new getTopTask().execute();
    }

    @OnClick({R.id.top_right_text, R.id.top_left})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.top_right_text:
                Intent intent = new Intent(ChooseTourerActivity.this, AddUserInfoActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.top_left:
                break;
        }
    }

    UseInfoSubscriber freshEvent = new UseInfoSubscriber() {
        @Override
        public void onEvent(UseInfoRefreshEvent v) {
            new getTopTask().execute();
        }
    };


    /*数据
     * */
    class getTopTask extends WorkTask<Void, Void, UsePersonBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", true);
        }

        @Override
        public UsePersonBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(ChooseTourerActivity.this).getContactInfo(account.getBody().getMember_id());
        }

        @Override
        protected void onSuccess(UsePersonBean info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null) {
                showview(info);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void showview(UsePersonBean info) {
        madatas = info.getBody();
        if (madatas != null && madatas.size() >= 1) {
            madatas.get(0).setCheck(true);
        }
        mAdapter = new CommonAdapter<UsePerList>(ChooseTourerActivity.this, R.layout.choose_tourer_item, madatas) {
            @Override
            protected void convert(ViewHolder holder, final UsePerList infos, final int position) {
                Log.e("ddd", "convert: "+madatas.get(position).getName());
                holder.setText(R.id.name, madatas.get(position).getName());
                holder.setText(R.id.phone, "手机号  " + madatas.get(position).getMobile());
                holder.setText(R.id.id_card, "身份证  " + madatas.get(position).getCertificate());

                holder.setOnClickListener(R.id.edit_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseTourerActivity.this, AddUserInfoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("infos",madatas.get(position));
                        intent.putExtras(mBundle);
                        intent.putExtra("type","1");
                        startActivity(intent);
                    }
                });
                holder.setOnCheckedChangeListener(R.id.tour_choose, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            normalChooseNum++;
                        } else {
                            normalChooseNum--;
                        }
                        choose_normal_num.setText(normalChooseNum+"");
                    }
                });
            }
        };
        recycleview.setLayoutManager(new LinearLayoutManager(ChooseTourerActivity.this));
        recycleview.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter().unsubscribe(UseInfoRefreshEvent.class, freshEvent);
    }
}
