package com.zhl.huiqu.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.bean.DetailMainBean;
import com.zhl.huiqu.main.bean.DitalTickList;
import com.zhl.huiqu.main.ticket.LocationActivity;
import com.zhl.huiqu.personal.OrderWriteActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.GlideImageLoader;
import com.zhl.huiqu.widget.MyScroview;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/*
*
* @author lyj
* @describe  门票详情
* @data 2017/11/9
* */


public class ProductDetailActivity extends BaseActivity implements MyScroview.OnScrollListener {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.image)
    ImageView soucang;
    @Bind(R.id.image_t)
    ImageView fenxiang;
    @Bind(R.id.myscroview)
    MyScroview myscroview;
    @Bind(R.id.search02)
    LinearLayout search02;// 在MyScrollView里面的购买布局
    @Bind(R.id.search01)
    LinearLayout search01;

    @Bind(R.id.top_layout)
    RelativeLayout top_layout;
    @Bind(R.id.tab_mian)
    LinearLayout tab_mian;


    //切换
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

    //
    @Bind(R.id.jd_js)
    RelativeLayout jd_js;//景点介绍
    @Bind(R.id.crp_line)
    LinearLayout crp_line;//成人票
    //    @Bind(R.id.main_image)
//    ImageView main_image;//top图片
    @Bind(R.id.num)
    TextView num;//top图片数
    @Bind(R.id.title)
    TextView title;//top图片标题
    @Bind(R.id.address)
    TextView address;//旅游地址
    @Bind(R.id.open_time)
    TextView open_time;//开放时间
    @Bind(R.id.jd_tupian)
    ImageView jd_tupian;//酒店介绍
    @Bind(R.id.jd_content)
    TextView jd_content;//
    @Bind(R.id.cr_p)
    RecyclerView pRecycle;//
    @Bind(R.id.mp_rela)
    RelativeLayout mp_rela;//
    @Bind(R.id.js_line)
    LinearLayout js_line;//
    @Bind(R.id.yd_content)
    TextView yd_content;//预定须知

    @Bind(R.id.progress)
    RelativeLayout progress;//预定须知

    @Bind(R.id.layoutLoading)
    LinearLayout layoutLoading;//预定须知

    @Bind(R.id.banner)
    Banner banner;
    private int select = 1;
    private int topHeight;
    private int jd_hight = -1;//景点介绍的高度
    private int crp_hight = -1;//成人票的高度
    private int tab_hight;
    private CommonAdapter<DitalTickList> mAdapter;
    private List<DitalTickList> mlist = new ArrayList<>();
    private DetailBean info;
    private String shop_spot_id = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_profuct;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            shop_spot_id = bd.getString("shop_spot_id");
        }
        changeview(1);
        myscroview.setOnScrollListener(this);
        top_title.setText("产品详情");
        soucang.setVisibility(View.VISIBLE);
        fenxiang.setVisibility(View.GONE);
        fenxiang.setBackgroundResource(R.drawable.mpxq_black_fx);
    }

    @Override
    public void initData() {
        super.initData();
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        new getInfoTask().execute(deviceId);
    }

    private void showcollection(DetailBean info) {
        if (info.getSpot_info() == null) {
            soucang.setBackgroundResource(R.drawable.mpxq_sc);
            return;
        }
        String ss = info.getSpot_info().getCollect_status();
        if (!TextUtils.isEmpty(ss)) {
            if ("1".equals(ss)) {
                soucang.setBackgroundResource(R.drawable.mpxq_sc_red);
            } else {
                soucang.setBackgroundResource(R.drawable.mpxq_sc);
            }
        } else {
            soucang.setBackgroundResource(R.drawable.mpxq_sc);
        }

    }


    private void showlist() {
        mAdapter = new CommonAdapter<DitalTickList>(this, R.layout.mp_item, mlist) {
            @Override
            protected void convert(ViewHolder holder, final DitalTickList bean, final int position) {
                holder.setText(R.id.content, bean.getTitle());
                holder.setText(R.id.now_price, "￥" + bean.getShop_price() + "起");
                holder.setText(R.id.old_price, "￥" + bean.getMarket_price());
                holder.setOnClickListener(R.id.submit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductDetailActivity.this, OrderWriteActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("pay", bean);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                });
            }
        };
        pRecycle.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this));
        pRecycle.addItemDecoration(new SimpleDividerItemDecoration(this, null, 1));
        pRecycle.setAdapter(mAdapter);
        pRecycle.setNestedScrollingEnabled(false);
    }


    @OnClick({R.id.top_left, R.id.image, R.id.image_t, R.id.tab1_mian, R.id.tab2_mian, R.id.tab3_mian, R.id.look_detail, R.id.location, R.id.js_line})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                ProductDetailActivity.this.finish();
                break;
            case R.id.image:
                new toTask().execute();
                break;
            case R.id.image_t:

                break;
            case R.id.tab1_mian:
                select = 1;
                changeview(select);
                tpscoll(crp_hight);
                break;
            case R.id.tab2_mian:
                select = 2;
                changeview(select);
                tpscoll(jd_hight);
                break;
            case R.id.look_detail:
            case R.id.js_line:
                if(info!=null&&info.getSpot_info()!=null){
                    Intent intent = new Intent(ProductDetailActivity.this, WebviewActivity.class);
                    intent.putExtra("title", info.getSpot_info().getTitle()!=null?info.getSpot_info().getTitle():"");
                    intent.putExtra("content", info.getSpot_info().getContent()!=null? info.getSpot_info().getContent():"");
                    startActivity(intent);
                }else {
                    ToastUtils.showShortToast(ProductDetailActivity.this,"为获取到内容");
                }
                break;
            case R.id.location:
                Intent intent1 = new Intent(ProductDetailActivity.this, LocationActivity.class);
                if(info!=null&&info.getSpot_info()!=null&&!TextUtils.isEmpty(info.getSpot_info().getLatitude())){
                    intent1.putExtra("latitude", info.getSpot_info().getLatitude());//纬度
                }else {
                    intent1.putExtra("latitude", "");//纬度
                }
                if(info!=null&&info.getSpot_info()!=null&&!TextUtils.isEmpty(info.getSpot_info().getLongitude())){
                    intent1.putExtra("longitude", info.getSpot_info().getLongitude());//经度
                }else {
                    intent1.putExtra("longitude", "");//经度
                }
                intent1.putExtra("address", info.getSpot_info().getAddress());
                startActivity(intent1);
                break;
        }
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
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;//状态栏高度
//        int titleBarHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//标题栏高度
//        topHeight = titleBarHeight + statusBarHeight;
        if (hasFocus) {
            topHeight = search02.getBottom() - search02.getHeight();
            int[] position = new int[2];
            mp_rela.getLocationOnScreen(position);
            crp_hight = position[1] - top_layout.getHeight() - getStatusBarHeight() - mp_rela.getHeight();
            gethight();
        }
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

        if (y < jd_hight) {
            select = 1;
            changeview(select);
        } else {
            select = 2;
            changeview(select);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        topHeight = search02.getBottom() - search02.getHeight();

        gethight();
    }

    private void tpscoll(int y) {
        if (y == -1) {
            return;
        }
        myscroview.scrollTo(0, y);
    }

    private void gethight() {
        int[] position1 = new int[2];
        jd_js.getLocationOnScreen(position1);
        jd_hight = position1[1] - search02.getHeight() - top_layout.getHeight() - getStatusBarHeight() + crp_line.getHeight();
        TLog.log("tttt", "jd_hight=" + jd_hight);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /*
      * */
    class getInfoTask extends WorkTask<String, Void, DetailMainBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", true);
        }

        @Override
        public DetailMainBean workInBackground(String... voids) throws TaskException {

            return SDK.newInstance(ProductDetailActivity.this).getGoodsDetail(shop_spot_id, voids[0]);
        }

        @Override
        protected void onSuccess(DetailMainBean infot) {
            super.onSuccess(infot);

            if(ProductDetailActivity.this.isFinishing()){
                return;
            }
            info = infot.getBody();
            dismissAlert();
            progress.setVisibility(View.GONE);
            TLog.log("tttt", "info=" + infot);
            if (infot.getBody() != null) {
                showView(infot.getBody());
                showcollection(info);
            } else {
                layoutLoading.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            progress.setVisibility(View.GONE);
            dismissAlert();
        }
    }

    /*收藏
    * */
    class toTask extends WorkTask<Void, Void, BaseInfo> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在提交..", true);
        }

        @Override
        public BaseInfo workInBackground(Void... voids) throws TaskException {
            RegisterEntity data = (RegisterEntity) SaveObjectUtils.getInstance(ProductDetailActivity.this).getObject(Constants.USER_INFO, null);
            return SDK.newInstance(ProductDetailActivity.this).getCollect(data.getBody().getMember_id(), shop_spot_id,"ticket");
        }

        @Override
        protected void onSuccess(BaseInfo infot) {
            super.onSuccess(infot);
            dismissAlert();
            ToastUtils.showShortToast(ProductDetailActivity.this, infot.getMsg());
            if(info!=null){
                if ("1".equals(info.getSpot_info().getCollect_status())) {
                    info.getSpot_info().setCollect_status("0");
                } else {
                    info.getSpot_info().setCollect_status("1");
                }
                showcollection(info);
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


    private void showView(DetailBean info) {
        progress.setVisibility(View.GONE);
        if (info == null) {
            return;
        }
        if (info.getTicket_list() != null) {
            if (mlist != null) {
                mlist.clear();
            }

        }
        setBanner(info.getThumb_list());
        if (info.getThumb_list() != null) {
//            Glide.with(ProductDetailActivity.this)
//                    .load(info.getThumb_list().get(0))
//                    .into(main_image);
            num.setText(info.getThumb_list().size() + "");
        }

        if (info.getSpot_info() != null) {
            title.setText(info.getSpot_info().getTitle());
            address.setText(info.getSpot_info().getAddress());
            open_time.setText(info.getSpot_info().getOpening());
            Glide.with(ProductDetailActivity.this)
                    .load(info.getSpot_info().getThumb())
                    .into(jd_tupian);
            jd_content.setText(info.getSpot_info().getDesc());
            yd_content.setText(info.getSpot_info().getReminder());
        }


        mlist = info.getTicket_list();
        showlist();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gethight();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);//3秒后执行TimeTask的run方法
    }


    private void setBanner(List<String> images) {
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        banner.releaseBanner();
    }
}
