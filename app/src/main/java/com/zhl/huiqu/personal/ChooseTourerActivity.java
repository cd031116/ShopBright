package com.zhl.huiqu.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.team.TeamOrderActivity;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
/*
*
* @author lyj
* @describe 选择常用联系人
* @data 2017/11/9
* */

public class ChooseTourerActivity extends BaseActivity {
    @Bind(R.id.choose_tour_list)
    RecyclerView recycleview;
    private RegisterEntity account;
    private CommonAdapter<UsePerList> mAdapter;
    private List<UsePerList> madatas=new ArrayList<>();
    private int normalChooseNum = 0;
    @Bind(R.id.top_left_text)
    TextView title;
    @Bind(R.id.choose_normal_num)
    TextView choose_normal_num;
    @Bind(R.id.choose_child_num)
    TextView choose_child_num;
    @Bind(R.id.top_right_text)
    TextView label;
    @Bind(R.id.normal_num)
    TextView normal_num;
    @Bind(R.id.child_num)
    TextView child_num;
    @Bind(R.id.submit)
    TextView submit;
    @Bind(R.id.top_info)
    LinearLayout top_info;
    private List<UsePerList> oList=new ArrayList<>();
    private int adultCount = 0;
    private int childCount = 0;
    private int chose = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.choose_tourer;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            adultCount = Integer.parseInt(bd.getString("adultCount"));
            childCount = Integer.parseInt(bd.getString("childCount"));
            Log.i("gggg","adultCount="+adultCount);
            Log.i("gggg","childCount="+childCount);
            chose = bd.getInt("chose");
            oList.addAll((ArrayList<UsePerList>)bd.getSerializable("olist"));
        }
        NotificationCenter.defaultCenter().subscriber(UseInfoRefreshEvent.class, freshEvent);
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        label.setText("新增游客");
        label.setTextColor(Color.BLACK);
        title.setTextColor(Color.BLACK);
        title.setText("选择常用游客");
        normal_num.setText("/"+adultCount+"成人");
        child_num.setText("/"+childCount+"儿童");
        if(chose==1){
            submit.setVisibility(View.VISIBLE);
            top_info.setVisibility(View.VISIBLE);
        }else {
            submit.setVisibility(View.GONE);
            top_info.setVisibility(View.GONE);
        }
        show();
    }

    private  void show(){
        int fu=0;
        int child=0;
        for(UsePerList infos:oList){
            if(infos.isCheck()&&"1".equals(infos.getTypes())){
                fu=fu+1;
            }else if(infos.isCheck()&&"0".equals(infos.getTypes())){
                child=child+1;
            }
        }
        choose_normal_num.setText(fu+"");
        choose_child_num.setText(child+"");
    }



    @Override
    public void initData() {
        super.initData();
        showview();
        new getTopTask().execute();
    }

    @OnClick({R.id.top_right_text, R.id.top_left,R.id.submit})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.top_right_text:
                Intent intent = new Intent(ChooseTourerActivity.this, AddUserInfoActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.top_left:
                ChooseTourerActivity.this.finish();
                break;
            case R.id.submit:
                List<UsePerList> list=new ArrayList();
                for(int i=0;i<madatas.size();i++){
                    if(madatas.get(i).isCheck()){
                        list.add(madatas.get(i));
                    }
                }
                Intent intentd = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("plist", (Serializable) list);
                intentd.setClass(ChooseTourerActivity.this, TeamOrderActivity.class);
                intentd.putExtras(bundle);
                setResult(RESULT_OK,intentd);
                ChooseTourerActivity.this.finish();
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
                madatas.addAll(info.getBody());
                setda();
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void setda(){
        for (UsePerList infdo:oList){
            if(infdo.isCheck()){
                for(UsePerList ins:madatas){
                    if(infdo.getContact_id().equals(ins.getContact_id())){
                        ins.setCheck(true);
                    }
                }

            }
        }

    }



    private void showview() {
        mAdapter = new CommonAdapter<UsePerList>(ChooseTourerActivity.this, R.layout.choose_tourer_item, madatas) {
            @Override
            protected void convert(ViewHolder holder, final UsePerList infos, final int position) {
                if (chose==1) {
                    holder.setVisible(R.id.tour_choose, true);
                } else {
                    holder.setVisible(R.id.tour_choose, false);
                }
                if (infos.isCheck()) {
                    holder.setSesect(R.id.tour_choose, true);
                } else {
                    holder.setSesect(R.id.tour_choose, false);
                }

                holder.setText(R.id.name, infos.getName());
                holder.setText(R.id.phone, "手机号  " + infos.getMobile());
                holder.setText(R.id.id_card, "身份证  " + infos.getCertificate());
                if ("1".equals(infos.getTypes())) {
                    holder.setVisible(R.id.child, false);
                } else {
                    holder.setVisible(R.id.child, true);
                }
                holder.setOnClickListener(R.id.edit_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseTourerActivity.this, AddUserInfoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("infos", infos);
                        intent.putExtras(mBundle);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    }
                });
                holder.setOnClickListener(R.id.main_s, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (infos.isCheck()) {
                            infos.setCheck(false);
                        } else {
                            //成人
                            if ("1".equals(infos.getTypes())) {
                                Log.i("gggg","num="+getparentNum());
                                if (getparentNum() < adultCount) {
                                    infos.setCheck(true);
                                } else {

                                }
                            } else {
                                //儿童
                                if ("0".equals(infos.getTypes())) {
                                    Log.i("gggg","num="+getchildNum());
                                    if (getchildNum() < childCount) {
                                        infos.setCheck(true);
                                    } else {

                                    }
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        setview();
                    }
                });
            }
        };
        recycleview.setLayoutManager(new LinearLayoutManager(ChooseTourerActivity.this));
        recycleview.setAdapter(mAdapter);
    }


    private int getparentNum() {
        int num = 0;
        for (UsePerList data : madatas) {
            if ("1".equals(data.getTypes()) && data.isCheck()) {
                num++;
            }
        }
        return num;
    }

    private int getchildNum() {
        int num = 0;
        for (UsePerList data : madatas) {
            if ("0".equals(data.getTypes()) && data.isCheck()) {
                num++;
            }
        }
        return num;
    }
    private void setview(){
        int nums = 0;
        int nump = 0;
        for (UsePerList data : madatas) {
            if ("0".equals(data.getTypes()) && data.isCheck()) {
                nums=nums+1;
            }else if("1".equals(data.getTypes()) && data.isCheck()) {
                nump=nump+1;
            }
        }
        choose_normal_num.setText(nump+"");
        choose_child_num.setText(nums+"");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter().unsubscribe(UseInfoRefreshEvent.class, freshEvent);
    }
}
