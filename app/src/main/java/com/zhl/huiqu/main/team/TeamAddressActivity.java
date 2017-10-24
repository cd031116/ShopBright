package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.team.bean.GoalBean;
import com.zhl.huiqu.main.team.bean.GoalHot;
import com.zhl.huiqu.main.team.bean.GoalProInfo;
import com.zhl.huiqu.main.team.bean.TeamTopMain;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.Utils;
import com.zhl.huiqu.widget.GlideRoundTransform;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamAddressActivity extends BaseActivity {
    @Bind(R.id.provity)
    RecyclerView mProvity;
    @Bind(R.id.recycleview)
    RecyclerView city_list;

    @Bind(R.id.city_p)
    ImageView city_p;
    @Bind(R.id.city_name)
    TextView city_name;
    private CommonAdapter<GoalProInfo> mAdapter;
    private CommonAdapter<GoalHot> nAdapter;
    private List<GoalProInfo> mList = new ArrayList<>();
    private List<GoalHot> nList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_address;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        setmProvdata();
        setCityData();
        new getDataTask().execute();
    }

    @OnClick({R.id.left_image, R.id.city_p})
    void onclik(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                TeamAddressActivity.this.finish();
                break;
            case R.id.city_p:
                Intent intent = new Intent(TeamAddressActivity.this, TeamListActivity.class);
                intent.putExtra("desCityId",(String) city_p.getTag(R.id.TAG_IMAGE));
                startActivity(intent);
                break;
        }
    }


    /*获取数据
 * */
    class getDataTask extends WorkTask<Void, Void, GoalBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", false);
        }

        @Override
        public GoalBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamAddressActivity.this).getDestination("");
        }

        @Override
        protected void onSuccess(GoalBean info) {
            super.onSuccess(info);
            dismissAlert();
//            SaveObjectUtils.getInstance(TeamAddressActivity.this).setObject(Constants.TEAM_ADDRESS, info.getBody());
            if (info != null) {
                mList.addAll(info.getBody().getProvince());
                nList.addAll(info.getBody().getCity());
                if (mList.size() > 0) {
                    mList.get(0).setIscheck(true);
                }
                setTopData(info.getBody().getThumb());
                mAdapter.notifyDataSetChanged();
                nAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void setmProvdata() {
        if (mList.size() > 0) {
            mList.get(0).setIscheck(true);
        }
        mAdapter = new CommonAdapter<GoalProInfo>(TeamAddressActivity.this, R.layout.team_address_left, mList) {
            @Override
            protected void convert(ViewHolder holder, final GoalProInfo info, int position) {
                if (info.ischeck()) {
                    holder.setBackgroundColor(R.id.left_bg, Color.parseColor("#e11818"));
                    holder.setTextColor(R.id.name, Color.parseColor("#e11818"));
                } else {
                    holder.setBackgroundColor(R.id.left_bg, Color.parseColor("#ffffff"));
                    holder.setTextColor(R.id.name, Color.parseColor("#333333"));
                }
                holder.setText(R.id.name, info.getDesProvinceName());
                holder.setOnClickListener(R.id.mian_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (info.ischeck()) {

                        } else {
                            resitcheck();
                            info.setIscheck(true);
                            mAdapter.notifyDataSetChanged();
                            new getListTask(info.getDesProvinceId()).execute();
                        }
                    }
                });
            }
        };
        mProvity.setLayoutManager(new LinearLayoutManager(TeamAddressActivity.this));
        mProvity.setAdapter(mAdapter);
    }

    private void resitcheck() {
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setIscheck(false);
            }
        }
    }

    private void setCityData() {
        nAdapter = new CommonAdapter<GoalHot>(TeamAddressActivity.this, R.layout.team_address_right, nList) {
            @Override
            protected void convert(ViewHolder holder,final GoalHot unfo, int position) {
                holder.setRunderWithUrl(R.id.city_p, unfo.getCityImg());
                holder.setText(R.id.city_name, unfo.getDesCityName());
                holder.setOnClickListener(R.id.main_line, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TeamAddressActivity.this, TeamListActivity.class);
                        intent.putExtra("desCityId",unfo.getDesCityId());
                        intent.putExtra("select",0);
                        startActivity(intent);
                    }
                });
            }
        };
        city_list.setLayoutManager(new GridLayoutManager(TeamAddressActivity.this, 3));
        city_list.setAdapter(nAdapter);
    }

    private void setTopData(GoalHot gh) {

        if(gh!=null){
            city_p.setTag(R.id.TAG_IMAGE, gh.getDesCityId());
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(this, 8));
            Glide.with(this)
                    .load(gh.getCityImg())
                    .apply(myOptions)
                    .into(city_p);
            city_name.setText(gh.getDesCityName());
        }else {
            Glide.with(this).asBitmap().load("").into(city_p);
            city_name.setText("");
        }


    }


    /*获取数据
* */
    class getListTask extends WorkTask<Void, Void, GoalBean> {
        private String desProvinceId;

        public getListTask(String desProvinceId) {
            this.desProvinceId = desProvinceId;
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", false);
        }

        @Override
        public GoalBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamAddressActivity.this).getDestination(desProvinceId);
        }

        @Override
        protected void onSuccess(GoalBean info) {
            super.onSuccess(info);
            dismissAlert();
//            SaveObjectUtils.getInstance(TeamAddressActivity.this).setObject(Constants.TEAM_ADDRESS, info.getBody());
            if (info != null) {
                if (nList.size() > 0) {
                    nList.clear();
                }
                nList.addAll(info.getBody().getCity());
                setTopData(info.getBody().getThumb());
                nAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


}
