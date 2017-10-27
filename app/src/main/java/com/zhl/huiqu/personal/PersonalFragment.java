package com.zhl.huiqu.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.login.RegisterActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.ticket.ViewPagerAdapter;
import com.zhl.huiqu.personal.bean.UrLikeBean;
import com.zhl.huiqu.personal.bean.UrLikeEntity;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.ToastUtils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.activity.container.FragmentArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心
 * Created by dw on 2017/8/11.
 */

public class PersonalFragment extends BaseFragment {

    private List<UrLikeEntity> mDatas;
    private LayoutInflater inflater_d;
    private List<View> mPagerList;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 2;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    @ViewInject(id = R.id.your_like_layout)
    RelativeLayout urLikeLayout;
    @ViewInject(id = R.id.personal_msg_layout)
    RelativeLayout urMsgLayout;
    @ViewInject(id = R.id.personal_login_layout)
    RelativeLayout urLoginLayout;
    @ViewInject(id = R.id.viewpager)
    ViewPager viewpager;
    @ViewInject(id = R.id.ll_dot)
    LinearLayout ll_dot;

    @ViewInject(id = R.id.personal_head_img)
    ImageView headImg;
    @ViewInject(id = R.id.personal_tel_text)
    TextView nameText;

    private RegisterEntity account;
    private GridView gridView;
    private LayoutInflater inflater;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }


    @Override
    public int inflateContentView() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        account = SaveObjectUtils.getInstance(getActivity()).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (account != null) {
            nameText.setText(account.getBody().getNickname());
            urMsgLayout.setVisibility(View.VISIBLE);
            urLoginLayout.setVisibility(View.GONE);
        } else {
            urLoginLayout.setVisibility(View.VISIBLE);
            urMsgLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        this.inflater = inflater;
        inflater_d = LayoutInflater.from(getActivity());
        initDatas();

    }

    @OnClick({R.id.row_collect_layout, R.id.row_look_his_layout, R.id.row_normal_msg_layout, R.id.row_kefu_center_layout,
            R.id.refund_order_btn, R.id.goout_order_btn, R.id.pay_order_btn, R.id.all_order_btn, R.id.personal_set,
            R.id.personal_login_btn, R.id.personal_register_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.row_kefu_center_layout:
                FragmentArgs args = new FragmentArgs();
                args.add("tag", 0);
                KefuCenterFragment.launch(getActivity(), args);
                break;
            case R.id.personal_set:
                if (account != null) {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    intent.putExtra("memberId", account.getBody().getMember_id());
                    startActivity(intent);
                } else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.row_look_his_layout:
                startActivity(new Intent(getActivity(), LookHistoryActivity.class));
                break;
            case R.id.personal_login_btn:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.personal_register_btn:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            default:
                otherClickEvent(view);
                break;
        }
    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        new obtainUrLiskeData().execute(deviceId);
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

    //猜你喜欢
    class obtainUrLiskeData extends WorkTask<String, Void, UrLikeBean> {

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("", false);
        }

        @Override
        public UrLikeBean workInBackground(String... params) throws TaskException {
            return SDK.newInstance(getActivity()).getYouLike(params[0]);
        }

        @Override
        protected void onSuccess(UrLikeBean info) {
            super.onSuccess(info);
            dismissAlert();
            mDatas = new ArrayList<UrLikeEntity>();
            if (info.getCode().equals("1")) {
                mDatas.addAll(info.getBody());
                urLikeLayout.setVisibility(View.VISIBLE);
                setUrLikeView();
            } else {
                urLikeLayout.setVisibility(View.GONE);
            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            urLikeLayout.setVisibility(View.GONE);
            dismissAlert();
        }
    }

    private void setUrLikeView() {
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.urlike_gridview, viewpager, false);
            gridView.setAdapter(new UrLikeGridViewAdapter(getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("shop_spot_id", mDatas.get(position).getShop_spot_id() + "");
                    startActivity(intent);
                }
            });
        }
        //设置适配器
        viewpager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }

    private void otherClickEvent(View view) {

        FragmentArgs args = new FragmentArgs();
        if (account == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            ToastUtils.showShortToast(getActivity(), getResources().getString(R.string.should_account_login));
        } else {
            switch (view.getId()) {
                case R.id.row_collect_layout:
                    Intent intent = new Intent(getActivity(), MyCollectAcitivity.class);
                    intent.putExtra("memberId", account.getBody().getMember_id());
                    startActivity(intent);
                    break;
                case R.id.row_normal_msg_layout:
//                    startActivity(new Intent(getActivity(), UseInfoActivity.class));
                    startActivity(new Intent(getActivity(), ChooseTourerActivity.class));
                    break;
                //所有订单
                case R.id.all_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_all_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //待付款
                case R.id.pay_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_pay_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //待出行
                case R.id.goout_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_out_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
                //退款
                case R.id.refund_order_btn:
                    args.add("productId", getResources().getString(R.string.personal_tuikuan_order));
                    OrderAllListFragment.launch(getActivity(), args);
                    break;
            }
        }
    }
}
