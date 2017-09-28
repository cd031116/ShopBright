package com.zhl.huiqu.personal;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.team.MainTeamActivity;
import com.zhl.huiqu.personal.bean.UsePerList;
import com.zhl.huiqu.personal.bean.UsePersonBean;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_info;
    }

    @Override
    public void initView() {
        super.initView();
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
    }

    @Override
    public void initData() {
        super.initData();
        new getTopTask().execute();
    }

    @OnClick({R.id.add_t,R.id.ledt_image})
    void onclick(View v){
        switch (v.getId()){
            case R.id.add_t:
                startActivity(new Intent(UseInfoActivity.this,AddUserInfoActivity.class));
                break;
            case R.id.ledt_image:
                UseInfoActivity.this.finish();
                break;
        }
    }

    /*数据
     * */
    class getTopTask extends WorkTask<Void, Void, UsePersonBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();

        }

        @Override
        public UsePersonBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(UseInfoActivity.this).getContactInfo(account.getBody().getMember_id());
        }

        @Override
        protected void onSuccess(UsePersonBean info) {
            super.onSuccess(info);
            dismissAlert();
            if(info.getBody()!=null){
                showview(info);
            }
        }
        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void showview(UsePersonBean info){
        madatas=info.getBody();
        mAdapter=new CommonAdapter<UsePerList>(UseInfoActivity.this,R.layout.use_info_list,madatas) {
            @Override
            protected void convert(ViewHolder holder, UsePerList infos, int position) {
                holder.setText(R.id.name,infos.getName());
                holder.setText(R.id.phone,"手机号  "+infos.getMobile());
                holder.setText(R.id.id_card,"身份证  "+infos.getCertificate());
            }
        };
        recycleview.setLayoutManager(new LinearLayoutManager(UseInfoActivity.this));
        recycleview.addItemDecoration(new SimpleDividerItemDecoration(this, null, 1));
        recycleview.setAdapter(mAdapter);
    }


}
