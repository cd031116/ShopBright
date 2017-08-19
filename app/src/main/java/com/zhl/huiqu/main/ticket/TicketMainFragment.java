package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.zhl.huiqu.main.bean.MainBean;
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
    private String[] titles = {"全部", "浪漫海景", "风景名胜", "水上乐园", "城市观光", "游乐世界", "历史人文", "健康养生", "影视基地", "民俗风情",
            "古镇水乡", "演出表演", "动植物园", "休闲娱乐", "宗教寺庙", "户外探险", "拓展培训", "飞行培训"};
    private int[] images = {R.drawable.jdmp_all, R.drawable.jdmp_qhj, R.drawable.jdmp_fenjing, R.drawable.jdmp_ssyd, R.drawable.jdmp_csgg, R.drawable.jdmp_yly
            , R.drawable.jdmp_ls, R.drawable.jdmp_yangsheng, R.drawable.jdmp_ys, R.drawable.jdmp_all, R.drawable.jdmp_gzms, R.drawable.jdmp_yc, R.drawable.jdmp_dzw, R.drawable.jdmp_ylxx,
            R.drawable.jdmp_sm, R.drawable.jdmp_fw, R.drawable.jdmp_px, R.drawable.jdmp_feiji};
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
            BaseConfig bg=new BaseConfig(getActivity());
            String addre= bg.getStringValue(Constants.Address,"");
            top_text.setText(addre);
        }
    };

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        initDatas();
        inflater_d = LayoutInflater.from(getActivity());
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, viewpager, false);
            gridView.setAdapter(new GridViewAdapter(getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Intent intent=new Intent(getActivity(),TicketListActivity.class);
                    intent.putExtra("title",mDatas.get(position).getName());
                    startActivity(intent);
                }
            });
        }
        //设置适配器
        viewpager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
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
        mDatas = new ArrayList<Model>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            mDatas.add(new Model(titles[i], images[i]));
        }
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

    /*门票首页
  * */
    class getData extends WorkTask<Void, Void, TickBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("..正在加载..", false);
        }

        @Override
        public TickBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(getActivity()).getTicketInfo("");
        }

        @Override
        protected void onSuccess(TickBean info) {
            super.onSuccess(info);
            dismissAlert();

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }



}
