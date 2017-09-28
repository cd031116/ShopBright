package com.zhl.huiqu.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.team.MainTeamActivity;
import com.zhl.huiqu.personal.bean.UsePerList;
import com.zhl.huiqu.personal.bean.UsePersonBean;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.CityEvent;
import com.zhl.huiqu.sdk.eventbus.CitySubscriber;
import com.zhl.huiqu.sdk.eventbus.UseInfoRefreshEvent;
import com.zhl.huiqu.sdk.eventbus.UseInfoSubscriber;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class UseInfoActivity extends BaseActivity {
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    private RegisterEntity account;
    private CommonAdapter<UsePerList> mAdapter;
    private List<UsePerList> madatas;
    @Bind(R.id.view_empty)
    LinearLayout view_empty;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_info;
    }

    @Override
    public void initView() {
        super.initView();
        NotificationCenter.defaultCenter().subscriber(UseInfoRefreshEvent.class, freshEvent);
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
    }

    @Override
    public void initData() {
        super.initData();
        new getTopTask().execute();
    }

    @OnClick({R.id.add_t, R.id.ledt_image})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.add_t:
                Intent intent=new Intent(UseInfoActivity.this, AddUserInfoActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.ledt_image:
                UseInfoActivity.this.finish();
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
            return SDK.newInstance(UseInfoActivity.this).getContactInfo(account.getBody().getMember_id());
        }

        @Override
        protected void onSuccess(UsePersonBean info){
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null) {
                showview(info);
            } else {
                view_empty.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onFailure(TaskException exception){
            dismissAlert();
            view_empty.setVisibility(View.VISIBLE);
        }
    }

    private void showview(UsePersonBean info) {
        madatas = info.getBody();
        if (madatas != null && madatas.size() >= 1) {
            madatas.get(0).setCheck(true);
        }
        mAdapter = new CommonAdapter<UsePerList>(UseInfoActivity.this, R.layout.use_info_list, madatas) {
            @Override
            protected void convert(ViewHolder holder, final UsePerList infos, final int position) {
                holder.setText(R.id.name, infos.getName());
                holder.setText(R.id.phone, "手机号  " + infos.getMobile());
                holder.setText(R.id.id_card, "身份证  " + infos.getCertificate());
//                if (infos.isCheck()) {
//                    holder.setTextColor(R.id.text, Color.parseColor("#fcad3a"));
//                    holder.setSesect(R.id.man, true);
//                } else {
//                    holder.setTextColor(R.id.text, Color.parseColor("#666666"));
//                    holder.setSesect(R.id.man, false);
//                }
//                holder.setOnClickListener(R.id.man, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (infos.isCheck()) {
//                            infos.setCheck(false);
//                        } else {
//                            infos.setCheck(true);
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });
                holder.setOnClickListener(R.id.delete_ima, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog(position);
                    }
                });
                holder.setOnClickListener(R.id.delete_t, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog(position);
                    }
                });

                holder.setOnClickListener(R.id.edit_ima, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(UseInfoActivity.this, AddUserInfoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("infos",infos);
                        intent.putExtras(mBundle);
                        intent.putExtra("type","1");
                        startActivity(intent);

                    }
                });
                holder.setOnClickListener(R.id.edit_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(UseInfoActivity.this, AddUserInfoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("infos",infos);
                        intent.putExtras(mBundle);
                        intent.putExtra("type","1");
                        startActivity(intent);
                    }
                });
            }
        };
        recycleview.setLayoutManager(new LinearLayoutManager(UseInfoActivity.this));
        recycleview.setAdapter(mAdapter);
    }


    /*数据
     * */
    class deleteTask extends WorkTask<Void, Void, String> {
        private int position;

        public deleteTask(int position) {
            this.position = position;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在提交..", true);
        }

        @Override
        public String workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(UseInfoActivity.this).getDeleteInfo(account.getBody().getMember_id(), madatas.get(position).getContact_id());
        }

        @Override
        protected void onSuccess(String info) {
            super.onSuccess(info);
            dismissAlert();
            madatas.remove(position);
            mAdapter.notifyDataSetChanged();
            if(madatas.size()<=0){
                view_empty.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
            view_empty.setVisibility(View.VISIBLE);
        }
    }

    public void exitDialog(final int index) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定删除此联系人?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new deleteTask(index).execute();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter().unsubscribe(UseInfoRefreshEvent.class, freshEvent);
    }
}
