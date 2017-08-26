package com.zhl.huiqu.main;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.base.MyApplication;
import com.zhl.huiqu.main.bean.HotInfo;
import com.zhl.huiqu.main.bean.HotelInfo;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.bean.MainTopInfo;
import com.zhl.huiqu.main.bean.TicketsInfo;
import com.zhl.huiqu.main.search.SearchFragment;
import com.zhl.huiqu.main.ticket.TicketListActivity;
import com.zhl.huiqu.main.ticket.TicketMainFragment;
import com.zhl.huiqu.main.ticket.TixkSearchActivity;
import com.zhl.huiqu.scan.CaptureActivity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.CityEvent;
import com.zhl.huiqu.sdk.eventbus.CitySubscriber;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.GlideCircleTransform;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.GlideImageLoader;
import com.zhl.huiqu.widget.ShowMsgDialog;

import org.aisen.android.common.utils.Logger;
import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.bean.TabItem;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/*
*
* @author lyj
* @describe 首页
* @data 2017/8/12
* */

public class MainTabFragment extends ATabsTabLayoutFragment<TabItem> {
    @ViewInject(id = R.id.banner)
    Banner banner;
    @ViewInject(id = R.id.mp_1)
    TextView mp_1;
    @ViewInject(id = R.id.mp_2)
    TextView mp_2;
    @ViewInject(id = R.id.mp_3)
    TextView mp_3;
    @ViewInject(id = R.id.mp_4)
    TextView mp_4;

    @ViewInject(id = R.id.jd_1)
    TextView jd_1;
    @ViewInject(id = R.id.jd_2)
    TextView jd_2;
    @ViewInject(id = R.id.jd_3)
    TextView jd_3;
    @ViewInject(id = R.id.jd_4)
    TextView jd_4;

    @ViewInject(id = R.id.address)
    TextView address;

    @ViewInject(id = R.id.hot_1)
    ImageView hot_1;
    @ViewInject(id = R.id.hot_2)
    ImageView hot_2;
    @ViewInject(id = R.id.hot_3)
    ImageView hot_3;

    @ViewInject(id = R.id.layoutContentd)
    CoordinatorLayout scro;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ShowMsgDialog progressDialog;
    private MainTopInfo mainInfo;
    public static MainTabFragment newInstance() {
        return new MainTabFragment();
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView() {
        return R.layout.main_tab_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationCenter.defaultCenter().subscriber(CityEvent.class, cityEvent);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);
        int width = SystemUtils.getScreenWidth(getActivity());
        mainInfo=   SaveObjectUtils.getInstance(getActivity()).getObject(Constants.MAIN_DATA,null);
        settopView(mainInfo);
        setBanner();
    }

    CitySubscriber cityEvent = new CitySubscriber() {
        @Override
        public void onEvent(CityEvent v) {
            BaseConfig bg = new BaseConfig(getActivity());
            String addre = bg.getStringValue(Constants.Address, "");
            address.setText(addre);

        }
    };


    @Override
    public void onPause() {
        super.onPause();
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

    @Override
    public void onDestroy() {
        NotificationCenter.defaultCenter().unsubscribe(CityEvent.class, cityEvent);
        super.onDestroy();
    }


    @Override
    protected boolean asyncTabInit() {
        return true;
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem("1", "景区门票"));
        tabs.add(new TabItem("2", "跟团游"));
        tabs.add(new TabItem("3", "酒店+景点"));
        return tabs;
    }

    @Override
    protected void setupTabLayout(Bundle savedInstanceSate) {
        super.setupTabLayout(savedInstanceSate);
        if (getTabItems().size() <= 3) {
            getTablayout().setTabMode(TabLayout.MODE_FIXED);
        }
        getTablayout().setTabTextColors(Color.parseColor("#333333"), Color.parseColor("#333333"));
    }


    @Override
    protected Fragment newFragment(TabItem tabItem) {
        TLog.log("mmmm","tabItem="+tabItem.getType());
        return MainProductListFragment.newInstance(tabItem.getType());
    }

    @Override
    public void requestData() {
//        throw new TaskException(TaskException.TaskError.resultIllegal + "");
        new getTopTask().execute();
        setTabItems(generateTabs());
        setTabInit(null);
    }

    private void setBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
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
                intent.putExtra("shop_spot_id",mainInfo.getNav().get(position).getShop_spot_id());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.scan, R.id.searh_line, R.id.main_mp, R.id.mp_1, R.id.mp_2, R.id.mp_3, R.id.mp_4, R.id.hot_1, R.id.hot_2, R.id.hot_3})
    void onclik(View v) {
        switch (v.getId()) {
            case R.id.scan:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.searh_line:
//                SearchFragment.launch(getActivity());
                startActivity(new Intent(getActivity(), TixkSearchActivity.class));
                break;
            case R.id.main_mp:
                TicketMainFragment.launch(getActivity());
                break;
            case R.id.mp_1:
                Intent intent = new Intent(getActivity(), TicketListActivity.class);
                intent.putExtra("title", mp_1.getText().toString());
                intent.putExtra("theme_id", mp_1.getTag().toString());
                startActivity(intent);
                break;
            case R.id.mp_2:
                Intent intent2 = new Intent(getActivity(), TicketListActivity.class);
                intent2.putExtra("title", mp_2.getText().toString());
                intent2.putExtra("theme_id", mp_2.getTag().toString());
                startActivity(intent2);
                break;
            case R.id.mp_3:
                Intent intent3 = new Intent(getActivity(), TicketListActivity.class);
                intent3.putExtra("title", mp_3.getText().toString());
                intent3.putExtra("theme_id", mp_3.getTag().toString());
                startActivity(intent3);
                break;

            case R.id.mp_4:
                Intent intent4 = new Intent(getActivity(), TicketListActivity.class);
                intent4.putExtra("title", mp_3.getText().toString());
                intent4.putExtra("theme_id", mp_3.getTag().toString());
                startActivity(intent4);
                break;
            case R.id.hot_1:
                Intent intent5 = new Intent(getActivity(), ProductDetailActivity.class);
                if(mainInfo!=null){
                    intent5.putExtra("shop_spot_id",mainInfo.getHot().get(0).getShop_spot_id());
                }
                startActivity(intent5);
                break;
            case R.id.hot_2:
                Intent intent6 = new Intent(getActivity(), ProductDetailActivity.class);
                if(mainInfo!=null){
                    intent6.putExtra("shop_spot_id",mainInfo.getHot().get(1).getShop_spot_id());
                }
                startActivity(intent6);
                break;
            case R.id.hot_3:
                Intent intent7 = new Intent(getActivity(), ProductDetailActivity.class);
                if(mainInfo!=null){
                    intent7.putExtra("shop_spot_id",mainInfo.getHot().get(2).getShop_spot_id());
                }
                startActivity(intent7);
                break;
        }
    }

    @OnClick({R.id.imageView2,R.id.jd_1,R.id.jd_2,R.id.jd_3,R.id.jd_4})
    void jiudian(View v){
            switch (v.getId()){
                case R.id.imageView2:
                    ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                    break;
                case R.id.jd_1:
                    ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                    break;
                case R.id.jd_2:
                    ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                    break;
                case R.id.jd_3:
                    ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                    break;
                case R.id.jd_4:
                    ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                    break;
            }
    }


    @OnClick({R.id.gentuan_image,R.id.gt_one,R.id.gt_two,R.id.gt_three,R.id.gt_four})
    void gentuan(View v){
        switch (v.getId()){
            case R.id.gentuan_image:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.gt_one:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.gt_two:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.gt_three:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.gt_four:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
        }
    }

    @OnClick({R.id.jfsc_ima,R.id.xrzq_ima,R.id.yjgl_ima,R.id.rmtj_ima,R.id.yyqg_ima,R.id.djms_ima,R.id.hdmk_ima})
    void genClick(View v){
        switch (v.getId()){
            case R.id.jfsc_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.xrzq_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.yjgl_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.rmtj_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.yyqg_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.djms_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
            case R.id.hdmk_ima:
                ToastUtils.showLongToast(getActivity(), "正在开发中,敬请期待下一个版本");
                break;
        }
    }



    /*上部分数据
    * */
    class getTopTask extends WorkTask<Void, Void, MainBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            if(mainInfo==null){
                showAlert("..正在加载..", false);
            }
        }

        @Override
        public MainBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(getActivity()).getMainTop();
        }

        @Override
        protected void onSuccess(MainBean info) {
            super.onSuccess(info);
            dismissAlert();
            mainInfo=info.getBody();
            SaveObjectUtils.getInstance(getActivity()).setObject(Constants.MAIN_DATA,mainInfo);
            settopView(mainInfo);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    private void settopView(MainTopInfo info) {
        if (info == null) {
            return;
        }
        if(images!=null){
            images.clear();
        }
        for (int i = 0; i < info.getNav().size(); i++) {
            images.add(info.getNav().get(i).getBig_img());
        }
        setBanner();
        List<HotInfo> list = info.getHot();
        if (!TextUtils.isEmpty(list.get(0).getThumb())) {
            Glide.with(getActivity())
                    .load(list.get(0).getThumb())
                    .into(hot_1);
        }
        if (!TextUtils.isEmpty(list.get(1).getThumb())) {
            Glide.with(getActivity())
                    .load(list.get(1).getThumb())
                    .into(hot_2);
        }
        if (!TextUtils.isEmpty(list.get(2).getThumb())) {
            Glide.with(getActivity())
                    .load(list.get(2).getThumb())
                    .into(hot_3);
        }
        List<TicketsInfo> tlist = info.getTicket();
        if (tlist != null) {
            mp_1.setText(tlist.get(0).getName());
            mp_1.setTag(tlist.get(0).getShop_spot_attr_id());
            mp_2.setText(tlist.get(1).getName());
            mp_2.setTag(tlist.get(1).getShop_spot_attr_id());
            mp_3.setText(tlist.get(2).getName());
            mp_3.setTag(tlist.get(2).getShop_spot_attr_id());
            mp_4.setText(tlist.get(3).getName());
            mp_4.setTag(tlist.get(3).getShop_spot_attr_id());
        }

        List<HotelInfo> hlist = info.getHotel();
        if (hlist != null) {
            jd_1.setText(hlist.get(0).getType());
            jd_1.setTag(hlist.get(0).getShop_hotel_type_id());
            jd_2.setText(hlist.get(1).getType());
            jd_2.setTag(hlist.get(1).getShop_hotel_type_id());
            jd_3.setText(hlist.get(2).getType());
            jd_3.setTag(hlist.get(2).getShop_hotel_type_id());
            jd_4.setText(hlist.get(3).getType());
            jd_4.setTag(hlist.get(3).getShop_hotel_type_id());
        }
    }


    /**
     * 显示加载图标
     *
     * @param txt
     */
    public void showAlert(String txt, final boolean isCancel) {
        if (!"".equals(txt) && txt != null) {
            if (progressDialog == null) {
                progressDialog = new ShowMsgDialog(getActivity(), isCancel);
            }
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (isCancel) {

                        }
                    }
                    return false;
                }
            });
            progressDialog.show();
            progressDialog.showText(txt);
        }
    }

    /**
     * 关闭加载图标
     */
    public void dismissAlert() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
