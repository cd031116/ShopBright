package com.zhl.huiqu.main.team;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.hotelTour.HotelDetailActivity;
import com.zhl.huiqu.main.hotelTour.MainHotelTourActivity;
import com.zhl.huiqu.main.team.bean.LikeBean;
import com.zhl.huiqu.main.team.bean.LikeEntity;
import com.zhl.huiqu.main.team.bean.TeamBase;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.main.team.bean.TeamDetailEntity;
import com.zhl.huiqu.main.team.bean.TeamListInfo;
import com.zhl.huiqu.main.team.bean.TeamPriceBean;
import com.zhl.huiqu.main.team.bean.TeamPriceEntity;
import com.zhl.huiqu.main.team.teamdetailadapter.JourneyAdapter;
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.GlideImageLoader;
import com.zhl.huiqu.widget.MyScroview;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;
import com.zhl.huiqu.widget.calendar.CalendarActivity;

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
* @describe 跟团游详情
* @data 2017/11/9
* */

public class TeamDetailActivity extends BaseActivity implements MyScroview.OnScrollListener {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.myscroview)
    MyScroview myscroview;
    @Bind(R.id.search02)
    LinearLayout search02;// 在MyScrollView里面的购买布局
    @Bind(R.id.search01)
    LinearLayout search01;
    @Bind(R.id.pay_know_layout)
    LinearLayout payKnowLayout;

    @Bind(R.id.reminder_layout)
    LinearLayout reminder_layout;
    @Bind(R.id.diff_layout)
    LinearLayout diff_layout;
    @Bind(R.id.safe_layout)
    LinearLayout safe_layout;

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tab_mian)
    LinearLayout tab_mian;
    @Bind(R.id.like_list)
    RecyclerView like_list;
    @Bind(R.id.t_calender)
    RecyclerView t_calender;
    @Bind(R.id.more_calender)
    TextView more_calender;
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    //切换
    @Bind(R.id.contents)
    TextView contents;
    @Bind(R.id.group_price)
    TextView group_price;
    @Bind(R.id.tese_content)
    TextView tese_content;
    @Bind(R.id.w_text)
    TextView w_text;
    @Bind(R.id.a_text)
    TextView a_text;
    @Bind(R.id.contain_text)
    TextView contain_text;
    @Bind(R.id.zili_text)
    TextView zili_text;
    @Bind(R.id.fp_text)
    TextView fp_text;
    @Bind(R.id.fu_text)
    TextView fu_text;
    @Bind(R.id.submit)
    TextView submit;
    @Bind(R.id.tab1_t)
    TextView tab1_t;
    @Bind(R.id.tab1_v)
    TextView tab1_v;
    @Bind(R.id.tab2_t)
    TextView tab2_t;
    @Bind(R.id.tab2_v)
    TextView tab2_v;
    @Bind(R.id.tab3_t)
    TextView tab3_t;
    @Bind(R.id.tab3_v)
    TextView tab3_v;
    @Bind(R.id.collect_ima)
    ImageView collect_ima;
    private int select = 1;
    private int topHeight;
    private int journeyHeight;
    private int payknowHeight;
    private String spot_team_id;
    private CommonAdapter<TeamDetailBean.GetYouLikeBean> madapter;
    private CommonAdapter<TeamPriceBean> teamPriceAdapter;
    private JourneyAdapter journeyAdapter;
    private String deviceId;
    private String memberId;
    private String title;
    private TeamDetailBean beans;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_detail;
    }

    @Override
    public void initView() {
        super.initView();
        myscroview.setOnScrollListener(this);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        spot_team_id = getIntent().getStringExtra("spot_team_id");
        RegisterEntity account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (account != null) {
            memberId = account.getBody().getMember_id();
        }
    }

    @Override
    public void initData() {
        new obtainGroupDetail().execute();
        new obtainProductPrice().execute();
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
        changeview(select);
//        myscroview.scrollTo(0, 120);
        top_title.setText("详情");
        image.setVisibility(View.GONE);
        image.setImageResource(R.drawable.share);
    }

    @OnClick({R.id.top_left, R.id.image, R.id.tab1_mian, R.id.tab2_mian, R.id.tab3_mian, R.id.collect_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                TeamDetailActivity.this.finish();
                break;
            case R.id.image:

                break;
            case R.id.tab1_mian:
                select = 1;
                changeview(select);
                myscroview.scrollTo(0, topHeight);
                break;
            case R.id.tab2_mian:
                select = 2;
                changeview(select);
                myscroview.scrollTo(0, journeyHeight);
                break;
            case R.id.tab3_mian:
                select = 3;
                changeview(select);
                myscroview.scrollTo(0, payknowHeight);
                break;
            case R.id.collect_layout:
                new obtainCollectDate().execute();
                break;
        }
    }

    /**
     * 收藏
     */
    class obtainCollectDate extends WorkTask<Void, Void, BaseInfo> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中。。", false);
        }

        @Override
        public BaseInfo workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamDetailActivity.this).getCollect(memberId, spot_team_id, "team");
        }

        @Override
        protected void onSuccess(BaseInfo info) {
            super.onSuccess(info);
            dismissAlert();
            ToastUtils.showShortToast(TeamDetailActivity.this, info.getMsg());
            if(beans==null){

            }else {
                if ("1".equals(beans.getCollectStatus())) {
                    beans.setCollectStatus("0");
                } else {
                    beans.setCollectStatus("1");
                }
                showcollection(beans);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    private void showcollection(TeamDetailBean info) {
        if (info.getCollectStatus()== null) {
            collect_ima.setBackgroundResource(R.drawable.mpxq_sc);
            return;
        }
        String ss = info.getCollectStatus();
        if (!TextUtils.isEmpty(ss)) {
            if ("1".equals(ss)) {
                collect_ima.setBackgroundResource(R.drawable.mpxq_sc_red);
            } else {
                collect_ima.setBackgroundResource(R.drawable.mpxq_sc);
            }
        } else {
            collect_ima.setBackgroundResource(R.drawable.mpxq_sc);
        }

    }



    /**
     * 行程数据
     */
    class obtainGroupDetail extends WorkTask<Void, Void, TeamDetailEntity> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中。。", false);
        }

        @Override
        public TeamDetailEntity workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamDetailActivity.this).obtainGroupDetail(spot_team_id, deviceId);
        }

        @Override
        protected void onSuccess(TeamDetailEntity info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getCode().equals("1")) {
                Log.e("ddd", "onSuccess: " + true);
                setView(info.getBody());
                beans=info.getBody();
                showcollection(beans);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 行程数据
     */
    class obtainProductPrice extends WorkTask<Void, Void, TeamPriceEntity> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载中。。", false);
        }

        @Override
        public TeamPriceEntity workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamDetailActivity.this).obtainProductPrice(spot_team_id);
        }

        @Override
        protected void onSuccess(TeamPriceEntity info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getCode().equals("1")) {
                setPriceView(info.getBody());
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /**
     * 设置跟团游日历价格
     *
     * @param body
     */
    private void setPriceView(final List<TeamPriceBean> body) {
        teamPriceAdapter = new CommonAdapter<TeamPriceBean>(TeamDetailActivity.this, R.layout.item_calender_price, body) {
            @Override
            protected void convert(ViewHolder holder, TeamPriceBean teamPriceBean, int position) {
                if (position <= 2) {
                    holder.setText(R.id.calender_text, teamPriceBean.getYear() + "-" + teamPriceBean.getMonth() + "-" + teamPriceBean.getDay());
                    holder.setText(R.id.price_text, "￥" + teamPriceBean.getTicketPrice());
                    holder.setOnClickListener(R.id.price_layout, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toCalenderView((Serializable) body);
                        }
                    });
                }
            }
        };
        t_calender.setLayoutManager(new MyGridLayoutManager(TeamDetailActivity.this, 3));
        t_calender.setAdapter(teamPriceAdapter);
        more_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalenderView((Serializable) body);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalenderView((Serializable) body);
            }
        });
    }

    private void toCalenderView(Serializable body) {
        Intent intent = new Intent(TeamDetailActivity.this, CalendarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("body", body);
        intent.putExtra("spot_team_id", spot_team_id);
        intent.putExtra("team_title", title);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void setListView(List<TeamDetailBean.GetYouLikeBean> likeList) {
        madapter = new CommonAdapter<TeamDetailBean.GetYouLikeBean>(TeamDetailActivity.this, R.layout.team_like_layout_item, likeList) {

            @Override
            protected void convert(ViewHolder holder, final TeamDetailBean.GetYouLikeBean getYouLikeBean, int position) {
                holder.setText(R.id.like_price, getYouLikeBean.getPriceAdultMin() + "");
                holder.setText(R.id.like_title, getYouLikeBean.getProductName());
                holder.setText(R.id.out_address, getYouLikeBean.getDepartCitysName());
                holder.setText(R.id.go_address, getYouLikeBean.getDesCityName());
                holder.setText(R.id.like_tag, getYouLikeBean.getDuration() + "日游");
                holder.setBackgroundRes(R.id.like_tag,R.drawable.label);
                holder.setText(R.id.like_pl, "评论：" + getYouLikeBean.getCommentNum());
                holder.setText(R.id.like_my, "满意度：" + getYouLikeBean.getCsr());
                holder.setBitmapWithUrl(R.id.like_img, getYouLikeBean.getThumb());
                holder.setOnClickListener(R.id.your_like_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spot_team_id = getYouLikeBean.getProductId() + "";
                        Intent intent = new Intent(TeamDetailActivity.this, TeamDetailActivity.class);
                        intent.putExtra("spot_team_id", spot_team_id);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        like_list.setLayoutManager(new MyGridLayoutManager(TeamDetailActivity.this, 2));
        like_list.addItemDecoration(new SimpleDividerItemDecoration(this, null, 1));
        like_list.setAdapter(madapter);
    }


    /**
     * 设置详情页View
     *
     * @param body
     */
    private void setView(TeamDetailBean body) {

        group_price.setText(body.getTeamInfo().getPriceAdultMin() + "");
        contain_text.setText(body.getCostNotice().getCostInclude());
        tese_content.setText(body.getProductFeature().getContent());
        if (!TextUtils.isEmpty(body.getTeamInfo().getProductName())) {
            title = body.getTeamInfo().getProductName();
            contents.setText(body.getTeamInfo().getProductName());
        }
        zili_text.setText(body.getCostNotice().getCostExclude());
        setBookNoticeView(body);
        setListView(body.getGetYouLike());

        journeyAdapter = new JourneyAdapter(TeamDetailActivity.this, body.getJourneyInfo());
        recycleview.setLayoutManager(new MyLinearLayoutManager(TeamDetailActivity.this));
        recycleview.addItemDecoration(new SimpleDividerItemDecoration(this, null, 1));
        recycleview.setAdapter(journeyAdapter);
        journeyAdapter.setOnDrawFinishListener(new JourneyAdapter.OnDrawListener() {
            @Override
            public void onDrawFinish(int position) {
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        journeyHeight = recycleview.getBottom() - 120;
                        payknowHeight = payKnowLayout.getBottom() - 120;
                    }

                }, 500);
            }
        });
        setBanner(body.getImg());
    }

    /**
     * 设置预定须知
     *
     * @param body
     */
    private void setBookNoticeView(TeamDetailBean body) {
        fu_text.setText("交通信息：" + body.getBookNotice().getTrafficInfos() +
                "\n住宿信息：" + body.getBookNotice().getAccInfos() +
                "\n购物信息：" + body.getBookNotice().getShopping() +
                "\n游览信息：" + body.getBookNotice().getTour());
        if (body.getBookNotice().getDiffPrice() != null) {
            diff_layout.setVisibility(View.VISIBLE);
            w_text.setText(body.getBookNotice().getDiffPrice());
        } else diff_layout.setVisibility(View.GONE);
        if (body.getBookNotice().getWarmAttention() != null) {
            reminder_layout.setVisibility(View.VISIBLE);
            fp_text.setText(body.getBookNotice().getWarmAttention());
        } else reminder_layout.setVisibility(View.GONE);
        if (body.getBookNotice().getAbroadNotice() != null) {
            safe_layout.setVisibility(View.VISIBLE);
            a_text.setText(body.getBookNotice().getAbroadNotice());
        } else safe_layout.setVisibility(View.GONE);
    }


    private void changeview(int index) {
        tab1_t.setTextColor(Color.parseColor("#333333"));
        tab2_t.setTextColor(Color.parseColor("#333333"));
        tab3_t.setTextColor(Color.parseColor("#333333"));
        tab1_v.setSelected(false);
        tab2_v.setSelected(false);
        tab3_v.setSelected(false);
        if (index == 1) {
            tab1_t.setTextColor(Color.parseColor("#e11818"));
            tab1_v.setSelected(true);
        } else if (index == 2) {
            tab2_t.setTextColor(Color.parseColor("#e11818"));
            tab2_v.setSelected(true);
        } else {
            tab3_t.setTextColor(Color.parseColor("#e11818"));
            tab3_v.setSelected(true);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            topHeight = search02.getBottom() - search02.getHeight();
        }
    }

    @Override
    public void onScroll(int scrollY) {
        Log.e("ddd", "onScroll: " + scrollY + ",journeyHeight:" + journeyHeight + ",payknowHeight:" + payknowHeight);
        if (scrollY >= topHeight) {
            if (tab_mian.getParent() != search01) {
                search02.removeView(tab_mian);
                search01.addView(tab_mian);
            }
            changeview(1);
            if (scrollY >= journeyHeight && scrollY <= payknowHeight) {
                changeview(2);
            }
            if (scrollY >= payknowHeight) {
                changeview(3);
            }
        } else {
            if (tab_mian.getParent() != search02) {
                search01.removeView(tab_mian);
                search02.addView(tab_mian);
            }

        }
    }

    /**
     * 设置轮播图
     *
     * @param imgSrc
     */
    private void setBanner(List<String> imgSrc) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imgSrc);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
//        设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        topHeight = search02.getBottom() - search02.getHeight();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}