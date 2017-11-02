package com.zhl.huiqu.main.team;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.main.team.bean.FilterBase;
import com.zhl.huiqu.main.team.bean.FilterDest;
import com.zhl.huiqu.main.team.bean.FilterTheme;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.TeamEvent;
import com.zhl.huiqu.utils.Constants;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelecTeamActivity extends BaseActivity {
    @Bind(R.id.top_image)
    ImageView top_image;
    @Bind(R.id.top_left_text)
    TextView top_left_text;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.name_t)
    TextView name_t;
    @Bind(R.id.text)
    TextView empty_text;

    @Bind(R.id.view_empty)
    LinearLayout view_empty;
    @Bind(R.id.theme_list)
    RecyclerView theme_list;
    @Bind(R.id.address_list)
    RecyclerView address_list;
    private CommonAdapter<FilterTheme> mAdapter;
    private List<FilterTheme> mlist=new ArrayList<>();
    private CommonAdapter<FilterDest> tAdapter;
    private List<FilterDest> tlist=new ArrayList<>();
    private String desCityId="";
    private String themeId="";
    private String type="";
    @Override
    protected int getLayoutId(){
        return R.layout.activity_selec_team;
    }

    @Override
    public void initView() {
        super.initView();

        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            type=bd.getString("type");
        }
        name_t.setText("目的地");
        top_image.setVisibility(View.GONE);
        top_left_text.setTextColor(Color.parseColor("#333333"));
        top_left_text.setText("取消");
        top_title.setText("筛选");
    }

    @Override
    public void initData() {
        super.initData();
        themeview();
        destview();
        new getListTask().execute();
    }


    @OnClick({R.id.top_left_text,R.id.reset,R.id.sure})
    void onclicks(View v){
        switch (v.getId()){
            case R.id.top_left_text:
                SelecTeamActivity.this.finish();
                break;
            case R.id.reset:
                setflase();
                setDesflase();
                BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                bg.setStringValue(Constants.TEAM_GradeId,"");
                bg.setStringValue(Constants.TEAM_ThemeId,"");
                mAdapter.notifyDataSetChanged();
                tAdapter.notifyDataSetChanged();
                break;
            case R.id.sure:
                NotificationCenter.defaultCenter().publish(new TeamEvent(desCityId,themeId));//主题
                SelecTeamActivity.this.finish();
                break;
        }
    }

    private void themeview() {
        mAdapter = new CommonAdapter<FilterTheme>(SelecTeamActivity.this, R.layout.team_selecet_item, mlist) {
            @Override
            protected void convert(ViewHolder holder, final FilterTheme data, int position) {
                holder.setText(R.id.contents, data.getName());
                if (data.isSelect()) {
                    holder.setSesect(R.id.contents, true);
                    holder.setVisible(R.id.iamge,true);
                } else {
                    holder.setSesect(R.id.contents, false);
                    holder.setVisible(R.id.iamge,false);
                }
                holder.setOnClickListener(R.id.item_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.isSelect()) {
                            setflase();
                            data.setSelect(false);
                            BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                            bg.setStringValue(Constants.TEAM_ThemeId,"");
                        } else {
                            setflase();
                            data.setSelect(true);
                            themeId=data.getTeam_attr_id();
                            BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                            bg.setStringValue(Constants.TEAM_ThemeId,themeId);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        theme_list.setLayoutManager(new GridLayoutManager(this, 3));
        theme_list.setAdapter(mAdapter);
        theme_list.setNestedScrollingEnabled(false);
    }


    private void destview() {
        tAdapter = new CommonAdapter<FilterDest>(SelecTeamActivity.this, R.layout.team_selecet_item, tlist) {
            @Override
            protected void convert(ViewHolder holder, final FilterDest info, int position) {
                if(!TextUtils.isEmpty(info.getDesCityName())){
                    holder.setText(R.id.contents, info.getDesCityName());
                }
                if (info.isSelect()) {
                    holder.setSesect(R.id.contents, true);
                    holder.setVisible(R.id.iamge,true);
                } else {
                    holder.setSesect(R.id.contents, false);
                    holder.setVisible(R.id.iamge,false);
                }

                holder.setOnClickListener(R.id.item_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (info.isSelect()) {
                            setDesflase();
                            info.setSelect(false);
                            BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                            bg.setStringValue(Constants.TEAM_GradeId,"");
                        } else {
                            setDesflase();
                            info.setSelect(true);
                            desCityId=info.getDesCityId();
                            BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                            bg.setStringValue(Constants.TEAM_GradeId,desCityId);
                        }
                        tAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        address_list.setLayoutManager(new GridLayoutManager(this, 3));
        address_list.setAdapter(tAdapter);
        address_list.setNestedScrollingEnabled(false);
    }

    private void setflase(){
        for (int i=0;i<mlist.size();i++){
            mlist.get(i).setSelect(false);
        }
    }
    private void setDesflase() {
        for (int i=0;i<tlist.size();i++){
            tlist.get(i).setSelect(false);
        }
    }
    /*列表数据
  * */
    class getListTask extends WorkTask<Void, Void, FilterBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..",false);
        }

        @Override
        public FilterBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(SelecTeamActivity.this).getCondition(type);
        }

        @Override
        protected void onSuccess(FilterBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody().getTheme() != null) {
                mlist.addAll(info.getBody().getTheme());

                BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                themeId= bg.getStringValue(Constants.TEAM_ThemeId,"");
                for(FilterTheme tdata: mlist){
                    if(themeId.equals(tdata.getTeam_attr_id())){
                        tdata.setSelect(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            if (info.getBody().getDestination() != null) {
                tlist.addAll(info.getBody().getDestination());
                BaseConfig bg=BaseConfig.getInstance(SelecTeamActivity.this);
                desCityId= bg.getStringValue(Constants.TEAM_GradeId,"");
                for(FilterDest tdata: tlist){
                    if(desCityId.equals(tdata.getDesCityId())){
                        tdata.setSelect(true);
                    }
                }
                tAdapter.notifyDataSetChanged();
                destview();
            }
            if(info.getBody()==null){
                view_empty.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onFailure(TaskException exception){
            dismissAlert();
            view_empty.setVisibility(View.VISIBLE);
            empty_text.setText(exception.getMessage());
        }
    }
}
