package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.search.SearchFragment;
import com.zhl.huiqu.main.team.MainTeamActivity;
import com.zhl.huiqu.main.team.TeamListActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.CityEvent;
import com.zhl.huiqu.sdk.eventbus.CitySubscriber;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.GlideImageLoader;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;

import java.util.ArrayList;
import java.util.List;

/*
*
* @author lyj
* @describe  门票首页
* @data 2017/8/12
* */


public class TicketMainFragment extends BaseFragment {
    private List<String> imaged = new ArrayList<>();
    private List<CityData> mcity=new ArrayList<>();
    private List<View> mPagerList;
    private List<Model> mDatas;
    private LayoutInflater inflater_d;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;


    @ViewInject(id = R.id.viewpager)
    ViewPager viewpager;
    @ViewInject(id = R.id.ll_dot)
    LinearLayout ll_dot;
    @ViewInject(id = R.id.banner)
    Banner banner;
    @ViewInject(id = R.id.top_text)
    TextView top_text;
    @ViewInject(id = R.id.hot_recy)
    RecyclerView hot_recy;//热点
    @ViewInject(id = R.id.lear_city)
    RecyclerView lear_city;//
    @ViewInject(id = R.id.id_content)
    RecyclerView lear_jd;//

    private TickMainBean tickInfo;

    private CommonAdapter<TickMianHot> adapter;//热点
    private CommonAdapter<CityData> mdapter;//附近

    private CommonAdapter<TickCircum> jdapter;//热点
    private List<TickCircum> jData = new ArrayList<>();
    private List<TickMianHot> aData = new ArrayList<>();
    private LinearLayoutManager mLayoutManage;
    private LinearLayoutManager jLayoutManage;
    private LayoutInflater minflater;
    private boolean isdestory = false;

    public static TicketMainFragment newInstance() {
        return new TicketMainFragment();
    }

    public static void launch(Activity from) {
        ContainerActivity.launch(from, TicketMainFragment.class, null);
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationCenter.defaultCenter().subscriber(CityEvent.class, cityEvent);
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_ticket_main;
    }


    CitySubscriber cityEvent = new CitySubscriber(){
        @Override
        public void onEvent(CityEvent v) {
            BaseConfig bg = new BaseConfig(getActivity());
            String addre = bg.getStringValue(Constants.Address, "");
            top_text.setText(addre);
        }
    };

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate){
        super.layoutInit(inflater, savedInstanceSate);
        this.minflater = inflater;
        tickInfo = SaveObjectUtils.getInstance(getActivity()).getObject(Constants.TICK_DATA, null);
        if (tickInfo != null) {
            aData = tickInfo.getBody().getHot();
            jData = tickInfo.getBody().getAround();
            mDatas = tickInfo.getBody().getTheme();
            initDatas();
            setlist();
            sethot();
            setBanner();
        }
        new getData().execute();
        new getCity().execute();
    }

    @Override
    public void onDestroy() {
        if (mDatas != null) {
            mDatas.clear();
        }
        if (jData != null) {
            jData.clear();
        }
        isdestory = true;
        TLog.log("tttt", "onDestroy");
        NotificationCenter.defaultCenter().unsubscribe(CityEvent.class, cityEvent);
        super.onDestroy();
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


    /**
     * 初始化数据源
     */
    private void initDatas() {
        if (mDatas == null) {
            return;
        }
        inflater_d = LayoutInflater.from(getActivity());
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) minflater.inflate(R.layout.gridview, viewpager, false);
            gridView.setAdapter(new GridViewAdapter(getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Intent intent = new Intent(getActivity(), TicketListActivity.class);
                    intent.putExtra("title", mDatas.get(pos).getName());
                    intent.putExtra("theme_id", mDatas.get(pos).getShop_spot_attr_id());
                    startActivity(intent);
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
            ll_dot.addView(inflater_d.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        ll_dot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                ll_dot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                ll_dot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void setBanner() {
        if (tickInfo == null) {
            return;
        }
        if (imaged != null) {
            imaged.clear();
        }

        for (int i = 0; i < tickInfo.getBody().getNav().size(); i++) {
            imaged.add(tickInfo.getBody().getNav().get(i).getBig_img());
        }

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imaged);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("shop_spot_id", tickInfo.getBody().getNav().get(position).getShop_spot_id());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.btnBack, R.id.line_back, R.id.searh_line, R.id.editSearch, R.id.temai, R.id.jf_image, R.id.xr_image, R.id.yj_image,R.id.go_search})
    void onBtnBackClicked(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.searh_line:
            case R.id.editSearch:
                SearchFragment.launch(getActivity());
                break;
            case R.id.temai:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.jf_image:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.xr_image:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.yj_image:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.go_search:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;

        }
    }

    /*热点*/
    private void sethot() {
        final int width = SystemUtils.getScreenWidth(getActivity());
        adapter = new CommonAdapter<TickMianHot>(getActivity(), R.layout.item_hot, aData) {
            @Override
            protected void convert(ViewHolder holder, final TickMianHot hot, int position) {
//                holder.setRelatHW(R.id.main_top,width*1/3, Math.round(1/4* width));

                holder.setVisible(R.id.ur_like_tag, false);
                holder.setVisible(R.id.ur_like_dp, false);
                holder.setBitmapWithUrl(R.id.ur_like_img, hot.getThumb());
                holder.setText(R.id.ms_tourist_text, hot.getTitle());
                holder.setText(R.id.price_text, "￥" + hot.getShop_price()+"起");
//                holder.setText(R.id.price_ms_text, );
                String manyidu = hot.getCsr();
                if (!TextUtils.isEmpty(manyidu)&&!manyidu.equals("暂无评价")) {
                    holder.setText(R.id.address_text, hot.getCsr() + "满意度");
                } else {
                    holder.setText(R.id.address_text, hot.getCsr());
                }
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra("shop_spot_id", hot.getShop_spot_id());
                        startActivity(intent);
                    }
                });

            }
        };
        mLayoutManage = new LinearLayoutManager(getActivity());
        mLayoutManage.setOrientation(LinearLayoutManager.HORIZONTAL);//设置滚动方向，横向滚动
        hot_recy.setLayoutManager(mLayoutManage);
        hot_recy.setAdapter(adapter);
    }

    /*周边
    * */
    private void setlist() {
        jdapter = new CommonAdapter<TickCircum>(getActivity(), R.layout.item_tourist_point, jData) {
            @Override
            protected void convert(ViewHolder holder, final TickCircum ircum, int position) {
                holder.setBitmapWithUrl(R.id.tourist_view, ircum.getThumb());
                holder.setText(R.id.tourist_area, ircum.getTitle());
                holder.setText(R.id.tourist_ms, ircum.getDesc());
                holder.setText(R.id.tourist_place, ircum.getCity());
                holder.setText(R.id.tourist_place_score, ircum.getCsr());
                holder.setText(R.id.tourist_place_jibie, ircum.getLevel());
                holder.setText(R.id.tourist_tag, "风景名胜");
                holder.setTextColor(R.id.tourist_price, Color.parseColor("#e11818"));
                holder.setText(R.id.tourist_price, "￥" + ircum.getShop_price() + "起");
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra("shop_spot_id", ircum.getSpot_team_id());
                        startActivity(intent);
                    }
                });
            }
        };
        jLayoutManage = new LinearLayoutManager(getActivity());
        jLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);//设置滚动方向，横向滚动
        lear_jd.setLayoutManager(jLayoutManage);
        lear_jd.setAdapter(jdapter);
    }
    private void setCity(){
        mdapter=new CommonAdapter<CityData>(getActivity(),R.layout.city_item,mcity) {
            @Override
            protected void convert(ViewHolder holder,final CityData cityData, int position) {
                holder.setText(R.id.city,cityData.getCity());
                holder.setOnClickListener(R.id.main_line, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent  intent=new Intent(getActivity(), TeamListActivity.class);
                        intent.putExtra("desCityId",cityData.getCity_id());
                        intent.putExtra("select",1);
                        startActivity(intent);
                    }
                });
            }
        };
        mLayoutManage = new LinearLayoutManager(getActivity());
        mLayoutManage.setOrientation(LinearLayoutManager.HORIZONTAL);//设置滚动方向，横向滚动
        lear_city.setLayoutManager(mLayoutManage);
        lear_city.setAdapter(mdapter);
    }



    /*门票首页
  * */
    class getData extends WorkTask<Void, Void, TickMainBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            if (tickInfo == null) {
                showAlert("..正在加载..", false);
            }
        }

        @Override
        public TickMainBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(getActivity()).getTicketInfo("ticket");
        }

        @Override
        protected void onSuccess(TickMainBean info) {
            super.onSuccess(info);
            tickInfo = info;
            dismissAlert();
            SaveObjectUtils.getInstance(getActivity()).setObject(Constants.TICK_DATA, tickInfo);
            aData = info.getBody().getHot();
            jData = info.getBody().getAround();
            mDatas = info.getBody().getTheme();
            if (!isdestory) {
                initDatas();
                setlist();
                sethot();
                setBanner();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /*周边
    * */
    class getCity extends WorkTask<Void, Void, CityInfo> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
        }

        @Override
        public CityInfo workInBackground(Void... voids) throws TaskException{
            return SDK.newInstance(getActivity()).getCityAround("113.00","28.21");
        }

        @Override
        protected void onSuccess(CityInfo info){
            super.onSuccess(info);
            mcity=info.getBody();
            if (!isdestory) {
               setCity();
            }
        }

        @Override
        protected void onFailure(TaskException exception){

        }
    }

}
