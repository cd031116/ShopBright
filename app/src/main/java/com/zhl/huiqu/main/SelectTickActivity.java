package com.zhl.huiqu.main;

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
import com.zhl.huiqu.main.ticket.TickBase;
import com.zhl.huiqu.main.ticket.TickGraeld;
import com.zhl.huiqu.main.ticket.TickTheme;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.TickEvent;
import com.zhl.huiqu.utils.Constants;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectTickActivity extends BaseActivity {
    @Bind(R.id.top_image)
    ImageView top_image;
    @Bind(R.id.top_left_text)
    TextView top_left_text;
    @Bind(R.id.top_title)
    TextView top_title;

    @Bind(R.id.text)
    TextView empty_text;

    @Bind(R.id.view_empty)
    LinearLayout view_empty;
    @Bind(R.id.theme_list)
    RecyclerView theme_list;
    @Bind(R.id.address_list)
    RecyclerView address_list;
    private CommonAdapter<TickTheme> mAdapter;
    private List<TickTheme> mlist=new ArrayList<>();
    private CommonAdapter<TickGraeld> tAdapter;
    private List<TickGraeld> tlist=new ArrayList<>();
    private String gradeId = "";
    private String themeId = "";
    private String type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selec_team;
    }

    @Override
    public void initView() {
        super.initView();

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            type = bd.getString("type");
        }

        top_image.setVisibility(View.GONE);
        top_left_text.setTextColor(Color.parseColor("#333333"));
        top_left_text.setText("取消");
        top_title.setTextColor(Color.parseColor("#59c2de"));
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
    void onclicks(View v) {
        switch (v.getId()) {
            case R.id.top_left_text:
                SelectTickActivity.this.finish();
                break;
            case R.id.reset:
                setflase();
                setDesflase();
                BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                bg.setStringValue(Constants.TICK_ThemeId,"");
                bg.setStringValue(Constants.TICK_GradeId,"");
                mAdapter.notifyDataSetChanged();
                tAdapter.notifyDataSetChanged();
                break;
            case R.id.sure:
                NotificationCenter.defaultCenter().publish(new TickEvent(gradeId, themeId));//主题
                SelectTickActivity.this.finish();
                break;
        }
    }

    private void themeview() {
        mAdapter = new CommonAdapter<TickTheme>(SelectTickActivity.this, R.layout.team_selecet_item, mlist) {
            @Override
            protected void convert(ViewHolder holder, final TickTheme data, int position) {
                holder.setText(R.id.contents, data.getName());
                if (data.isSelect()) {
                    holder.setSesect(R.id.contents, true);
                    holder.setVisible(R.id.iamge, true);
                } else {
                    holder.setSesect(R.id.contents, false);
                    holder.setVisible(R.id.iamge, false);
                }
                holder.setOnClickListener(R.id.item_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.isSelect()) {
                            setflase();
                            data.setSelect(false);
                            BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                            bg.setStringValue(Constants.TICK_ThemeId,"");
                        } else {
                            setflase();
                            data.setSelect(true);
                            themeId = data.getThemeId();
                            BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                            bg.setStringValue(Constants.TICK_ThemeId,themeId);
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
        tAdapter = new CommonAdapter<TickGraeld>(SelectTickActivity.this, R.layout.team_selecet_item, tlist) {
            @Override
            protected void convert(ViewHolder holder, final TickGraeld info, int position) {
                if (!TextUtils.isEmpty(info.getName())) {
                    holder.setText(R.id.contents, info.getName());
                }
                if (info.isSelect()) {
                    holder.setSesect(R.id.contents, true);
                    holder.setVisible(R.id.iamge, true);
                } else {
                    holder.setSesect(R.id.contents, false);
                    holder.setVisible(R.id.iamge, false);
                }

                holder.setOnClickListener(R.id.item_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (info.isSelect()) {
                            setDesflase();
                            info.setSelect(false);
                            BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                            bg.setStringValue(Constants.TICK_GradeId,"");
                        } else {
                            setDesflase();
                            info.setSelect(true);
                            gradeId = info.getGradeId();
                            BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                            bg.setStringValue(Constants.TICK_GradeId,gradeId);
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

    private void setflase() {

        for (int i = 0; i < mlist.size(); i++) {
            mlist.get(i).setSelect(false);
        }
    }

    private void setDesflase() {
        for (int i = 0; i < tlist.size(); i++) {
            tlist.get(i).setSelect(false);
        }
    }

    /*列表数据
  * */
    class getListTask extends WorkTask<Void, Void, TickBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", false);
        }

        @Override
        public TickBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(SelectTickActivity.this).getTickTheme(type);
        }

        @Override
        protected void onSuccess(TickBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody().getTheme() != null) {
                mlist.addAll(info.getBody().getTheme());
                BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                themeId= bg.getStringValue(Constants.TICK_ThemeId,"");
                for(TickTheme tdata: mlist){
                    if(themeId.equals(tdata.getThemeId())){
                        tdata.setSelect(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            if (info.getBody().getGrade() != null) {
                tlist.addAll( info.getBody().getGrade());
                BaseConfig bg=BaseConfig.getInstance(SelectTickActivity.this);
                gradeId= bg.getStringValue(Constants.TICK_GradeId,"");
                for(TickGraeld tdata: tlist){
                    if(gradeId.equals(tdata.getGradeId())){
                        tdata.setSelect(true);
                    }
                }
                tAdapter.notifyDataSetChanged();
            }
            if (info.getBody() == null) {
                view_empty.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
            view_empty.setVisibility(View.VISIBLE);
            empty_text.setText(exception.getMessage());
        }
    }
}
