package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.CityEvent;
import com.zhl.huiqu.sdk.eventbus.CitySubscriber;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.GlideImageLoader;

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


    private CommonAdapter<TickMianHot> adapter;//热点
    private CommonAdapter<TickCircum> mdapter;//附近

    private CommonAdapter<TickCircum> jdapter;//热点
    private List<TickCircum> jData = new ArrayList<>();
    private List<TickMianHot> aData = new ArrayList<>();
    private LinearLayoutManager mLayoutManage;
    private LinearLayoutManager jLayoutManage;
    private  LayoutInflater minflater;
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
        new getData().execute();
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_ticket_main;
    }


    CitySubscriber cityEvent = new CitySubscriber() {
        @Override
        public void onEvent(CityEvent v) {
            BaseConfig bg = new BaseConfig(getActivity());
            String addre = bg.getStringValue(Constants.Address, "");
            top_text.setText(addre);
        }
    };

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        this.minflater=inflater;
        setBanner();
    }

    @Override
    public void onDestroy() {
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
                    intent.putExtra("title", mDatas.get(position).getName());
                    intent.putExtra("theme_id",mDatas.get(position).getShop_spot_attr_id());
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
        imaged.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        imaged.add("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        imaged.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
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

    }

    @OnClick({R.id.btnBack, R.id.line_back, R.id.searh_line, R.id.editSearch})
    void onBtnBackClicked(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.searh_line:
            case R.id.editSearch:
                startActivity(new Intent(getActivity(), TixkSearchActivity.class));
                break;
        }
    }

    /*热点*/
    private void sethot() {
        adapter = new CommonAdapter<TickMianHot>(getActivity(), R.layout.item_hot, aData) {
            @Override
            protected void convert(ViewHolder holder, TickMianHot hot, int position){
            holder.setVisible(R.id.ur_like_tag,false);
                holder.setVisible(R.id.ur_like_dp,false);
                holder.setBitmapWithUrl(R.id.ur_like_img,hot.getThumb());
                holder.setText(R.id.ms_tourist_text,hot.getTitle());
                holder.setText(R.id.price_text,"￥"+hot.getShop_price());
                holder.setText(R.id.price_ms_text,"起");
                holder.setText(R.id.address_text,hot.getCsr()+"满意度");
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),ProductDetailActivity.class));
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
            protected void convert(ViewHolder holder, TickCircum ircum, int position) {
                holder.setBitmapWithUrl(R.id.tourist_view, ircum.getThumb());
                holder.setText(R.id.tourist_area,ircum.getTitle());
                holder.setText(R.id.tourist_ms,ircum.getDesc());
                holder.setText(R.id.tourist_place,ircum.getCity());
                holder.setText(R.id.tourist_place_score,ircum.getCsr());
                holder.setText(R.id.tourist_place_jibie,ircum.getLevel());
                holder.setText(R.id.tourist_tag,"风景名胜");
                holder.setTextColor(R.id.tourist_price, Color.parseColor("#e11818"));
                holder.setText(R.id.tourist_price,"￥"+ircum.getShop_price()+"起");
                holder.setOnClickListener(R.id.main_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),ProductDetailActivity.class));
                    }
                });
            }
        };
        jLayoutManage = new LinearLayoutManager(getActivity());
        jLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);//设置滚动方向，横向滚动
        lear_jd.setLayoutManager(jLayoutManage);
        lear_jd.setAdapter(jdapter);
    }


    /*门票首页
  * */
    class getData extends WorkTask<Void, Void, TickMainBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", false);
        }

        @Override
        public TickMainBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(getActivity()).getTicketInfo("ticket");
        }

        @Override
        protected void onSuccess(TickMainBean info) {
            super.onSuccess(info);
            dismissAlert();
            aData = info.getData().getHot();
            jData=info.getData().getAround();
            mDatas=info.getData().getTheme();
            Log.i("mmmm","jData="+jData.size());
            initDatas();
            setlist();
            sethot();
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }


}
