package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.main.bean.HotInfo;
import com.zhl.huiqu.main.team.bean.CityList;
import com.zhl.huiqu.main.team.bean.TeamBase;
import com.zhl.huiqu.main.team.bean.TeamMainList;
import com.zhl.huiqu.main.team.bean.TeamTop;
import com.zhl.huiqu.main.team.bean.TeamTopMain;
import com.zhl.huiqu.main.ticket.ViewPagerAdapter;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.widget.MyScroview;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.ViewInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainTeamActivity extends BaseActivity implements MyScroview.OnScrollListener {
    @Bind(R.id.search02)
    LinearLayout search02;// 在MyScrollView里面的购买布局
    @Bind(R.id.search01)
    LinearLayout search01;
    @Bind(R.id.myscroview)
    MyScroview myscroview;
    @Bind(R.id.tab_mian)
    LinearLayout tab_mian;
    @Bind(R.id.tab1_t)
    TextView tab1_t;
    @Bind(R.id.tab2_t)
    TextView tab2_t;
    @Bind(R.id.tab3_t)
    TextView tab3_t;
    @Bind(R.id.tab4_t)
    TextView tab4_t;
    @Bind(R.id.tab5_t)
    TextView tab5_t;
    //
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.ll_dot)
    LinearLayout ll_dot;
    @ViewInject(id = R.id.hot_1)
    ImageView hot_1;
    @ViewInject(id = R.id.hot_2)
    ImageView hot_2;
    @ViewInject(id = R.id.hot_3)
    ImageView hot_3;

    @ViewInject(id = R.id.view_empty)
    LinearLayout view_empty;
    @ViewInject(id = R.id.view_progress)
    LinearLayout view_progress;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;
    private CommonAdapter<TeamMainList> madapter;
    private List<TeamMainList> mList;
    private int topHeight = 0;
    private int select = 0;
    //热门城市
    private List<CityList> mDatas;
    private List<View> mPagerList;
    private LayoutInflater inflater_d;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    private TeamTop t_info = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_team;
    }

    @Override
    public void initView() {
        super.initView();
        myscroview.setOnScrollListener(this);
        BaseConfig bg = BaseConfig.getInstance(this);
        topHeight = bg.getIntValue(Constants.TEAM_HIGHT, 0);
        t_info = (TeamTop) SaveObjectUtils.getInstance(MainTeamActivity.this).getObject(Constants.TEAM_INFO, null);
        if (t_info != null) {
            showview(t_info);
        }

    }

    @Override
    public void initData() {
        super.initData();
        new getTopTask().execute();
        new getListTask().execute();
        initDatas();
    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        if (mDatas == null) {
            return;
        }
        inflater_d = LayoutInflater.from(this);
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater_d.inflate(R.layout.team_gridview, viewpager, false);
            gridView.setAdapter(new GridViewsAdapter(this, mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;

                }
            });
        }
        //设置适配器
        viewpager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        if (ll_dot.getChildCount() > 0) {
            ll_dot.removeAllViews();
        }
        for (int i = 0; i < pageCount; i++) {
            ll_dot.addView(inflater_d.inflate(R.layout.team_dot, null));
        }
        // 默认显示第一页
        ll_dot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.team_dot_select);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                ll_dot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.team_dot_normal);
                // 圆点选中
                ll_dot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.team_dot_select);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;//状态栏高度
//        int titleBarHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//标题栏高度
//        topHeight = titleBarHeight + statusBarHeight;
        if (hasFocus) {
            topHeight = search02.getBottom() - search02.getHeight();
            BaseConfig bg = BaseConfig.getInstance(this);
            bg.setIntValue(Constants.TEAM_HIGHT, topHeight);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeview(select);
        BaseConfig bg = BaseConfig.getInstance(this);
        topHeight = bg.getIntValue(Constants.TEAM_HIGHT, 0);
    }

    @OnClick({R.id.all_line, R.id.search_re})
    void enter(View v) {
        switch (v.getId()) {
            case R.id.all_line:
                startActivity(new Intent(MainTeamActivity.this, TeamListActivity.class));
                break;
            case R.id.search_re:
                TeamSearchFragment.launch(this);
                break;
        }
    }


    @OnClick({R.id.tab1_t, R.id.tab2_t, R.id.tab3_t, R.id.tab4_t, R.id.tab5_t, R.id.line_back, R.id.btnBack})
    void onclixk(View v) {
        switch (v.getId()) {
            case R.id.tab1_t:
                if (select == 0) {
                    return;
                }
                select = 0;
                changeview(select);
                tpscoll(topHeight);
                new getListTask().execute();
                break;
            case R.id.tab2_t:
                if (select == 1) {
                    return;
                }
                select = 1;
                changeview(select);
                tpscoll(topHeight);
                new getListTask().execute();
                break;
            case R.id.tab3_t:
                if (select == 2) {
                    return;
                }
                select = 2;
                changeview(select);
                tpscoll(topHeight);
                new getListTask().execute();
                break;
            case R.id.tab4_t:
                if (select == 3) {
                    return;
                }
                select = 3;
                changeview(select);
                tpscoll(topHeight);
                new getListTask().execute();
                break;
            case R.id.tab5_t:
                if (select == 4) {
                    return;
                }
                select = 4;
                changeview(select);
                tpscoll(topHeight);
                new getListTask().execute();
                break;
            case R.id.line_back:
            case R.id.btnBack:
//                MainTeamActivity.this.finish();
                Intent intent = new Intent(MainTeamActivity.this, TeamDetailActivity.class);
                intent.putExtra("spot_team_id", "6");
                startActivity(intent);
                break;
        }
    }

    private void changeview(int select) {
        tab1_t.setSelected(false);
        tab2_t.setSelected(false);
        tab3_t.setSelected(false);
        tab4_t.setSelected(false);
        tab5_t.setSelected(false);
        tab1_t.setTextColor(Color.parseColor("#333333"));
        tab2_t.setTextColor(Color.parseColor("#333333"));
        tab3_t.setTextColor(Color.parseColor("#333333"));
        tab4_t.setTextColor(Color.parseColor("#333333"));
        tab5_t.setTextColor(Color.parseColor("#333333"));
        if (select == 0) {
            tab1_t.setSelected(true);
            tab1_t.setTextColor(Color.parseColor("#ffffff"));
        } else if (select == 1) {
            tab2_t.setSelected(true);
            tab2_t.setTextColor(Color.parseColor("#ffffff"));
        } else if (select == 2) {
            tab3_t.setSelected(true);
            tab3_t.setTextColor(Color.parseColor("#ffffff"));
        } else if (select == 3) {
            tab4_t.setSelected(true);
            tab4_t.setTextColor(Color.parseColor("#ffffff"));
        } else {
            tab5_t.setSelected(true);
            tab5_t.setTextColor(Color.parseColor("#ffffff"));
        }
    }


    private void tpscoll(int y) {
        if (y == -1) {
            return;
        }
        myscroview.scrollTo(0, y);
    }

    @Override
    public void onScroll(int y) {
        if (y >= topHeight) {
            if (tab_mian.getParent() != search01) {
                search02.removeView(tab_mian);
                search01.addView(tab_mian);
            }
        } else {
            if (tab_mian.getParent() != search02) {
                search01.removeView(tab_mian);
                search02.addView(tab_mian);
            }
        }
        if (y == (myscroview.getChildAt(0).getMeasuredHeight() - myscroview.getMeasuredHeight())) {

        }
    }

    private void showview(TeamTop data) {
        mDatas = data.getCity_list();
        initDatas();
        List<HotInfo> list = data.getHot();
        if (!TextUtils.isEmpty(list.get(0).getThumb())) {
            Glide.with(this)
                    .load(list.get(0).getThumb())
                    .into(hot_1);
        }
        if (!TextUtils.isEmpty(list.get(1).getThumb())) {
            Glide.with(this)
                    .load(list.get(1).getThumb())
                    .into(hot_2);
        }
        if (!TextUtils.isEmpty(list.get(2).getThumb())) {
            Glide.with(this)
                    .load(list.get(2).getThumb())
                    .into(hot_3);
        }
    }


    /*上部分数据
    * */
    class getTopTask extends WorkTask<Void, Void, TeamTopMain> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            if (t_info == null) {
                showAlert("..正在加载..", false);
            }
        }

        @Override
        public TeamTopMain workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(MainTeamActivity.this).getTeamTop();
        }

        @Override
        protected void onSuccess(TeamTopMain info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null) {
                SaveObjectUtils.getInstance(MainTeamActivity.this).setObject(Constants.TEAM_INFO, info.getBody());
                if (t_info == null) {
                    showview(info.getBody());
                }
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    /*列表数据
    * */
    class getListTask extends WorkTask<Void, Void, TeamBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showrecy(2);
        }

        @Override
        public TeamBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(MainTeamActivity.this).getListTop(select + "");
        }

        @Override
        protected void onSuccess(TeamBase info) {
            super.onSuccess(info);
            if (info.getBody()!= null) {
                showrecy(0);
                mList = info.getBody();
                showList();
            } else {
                showrecy(1);
            }


        }

        @Override
        protected void onFailure(TaskException exception) {
            showrecy(1);
        }
    }


    private void showrecy(int curIndex) {
        if (curIndex == 1) {
            recycleview.setVisibility(View.GONE);
            view_empty.setVisibility(View.VISIBLE);
            view_progress.setVisibility(View.GONE);
        } else if (curIndex == 2) {
            recycleview.setVisibility(View.GONE);
            view_progress.setVisibility(View.VISIBLE);
            view_empty.setVisibility(View.GONE);
        } else {
            recycleview.setVisibility(View.VISIBLE);
            view_empty.setVisibility(View.GONE);
            view_progress.setVisibility(View.GONE);
        }
    }


    private void showList() {
        madapter = new CommonAdapter<TeamMainList>(MainTeamActivity.this, R.layout.team_list_item, mList){
            @Override
            protected void convert(ViewHolder holder, final TeamMainList info, final int position){
                if (info.getThumb() != null) {
                    holder.setRunderWithUrl(R.id.photo, info.getThumb());
                }
                holder.setText(R.id.title, info.getProductName());
                holder.setText(R.id.price, "￥" + info.getPriceAdultMin());
                holder.setText(R.id.manyidu, info.getCsr());

                holder.setText(R.id.address, "→" + info.getDepartCitysName());
                holder.setText(R.id.day_time, info.getDuration() + "日游");
//                if (TextUtils.isEmpty(info.get)) {
//                    holder.setVisible(R.id.arrow, false);
//                    holder.setEnableds(R.id.u_click, false);
//                } else {
//                    holder.setVisible(R.id.arrow, true);
//                    holder.setEnableds(R.id.u_click, true);
//                }
//                if (info.isup()) {
//                    holder.setText(R.id.neirong, info.getDesc());
//                    holder.setImageResource(R.id.arrow, R.drawable.mpxq_up);
//                    holder.setVisible(R.id.nei_line, true);
//                } else {
//                    holder.setText(R.id.neirong, "");
//                    holder.setImageResource(R.id.arrow, R.drawable.mpxq_down);
//                    holder.setVisible(R.id.nei_line, false);
//                }

                holder.setOnClickListener(R.id.u_click, new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (info.isup()) {
                            info.setIsup(false);
                        } else {
                            info.setIsup(true);
                        }
                        madapter.notifyItemChanged(position);
                    }
                });
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainTeamActivity.this, TeamDetailActivity.class);
                        intent.putExtra("spot_team_id", info.getProductId());
                        startActivity(intent);
                    }
                });
            }
        };
        recycleview.setLayoutManager(new LinearLayoutManager(MainTeamActivity.this));
        recycleview.addItemDecoration(new SimpleDividerItemDecoration(this, null, 1));
        recycleview.setAdapter(madapter);
    }
}
