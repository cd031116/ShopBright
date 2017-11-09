package com.zhl.huiqu.main.ticket;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.main.popupWindow.SelectTourWindow;
import com.zhl.huiqu.main.popupWindow.TickBottomWindow;
import com.zhl.huiqu.sdk.eventbus.TickSearchEvent;

import org.aisen.android.component.eventbus.NotificationCenter;

import butterknife.Bind;
import butterknife.OnClick;

/*
*
* @author lyj
* @describe 门票搜索
* @data 2017/11/9
* */

public class TixkSearchActivity extends BaseActivity {
    @Bind(R.id.content)
    FrameLayout frameLayout;
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.go_search)
    ImageView go_search;

    private SelectTourWindow mopupWindow;
    private TickBottomWindow pPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixk_search;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText("门票搜索");
        getView();
        mopupWindow = new SelectTourWindow(TixkSearchActivity.this, itemsOnClick);
        pPopupWindow = new TickBottomWindow(TixkSearchActivity.this, itemsOnslick);
    }

    @Override
    public void initData(){
        super.initData();
    }

    @OnClick({R.id.top_left, R.id.top_image, R.id.jd_zhuti, R.id.saixuan, R.id.go_search})
    void onClicked(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                TixkSearchActivity.this.finish();
                break;
            case R.id.jd_zhuti:
                mopupWindow.showAtLocation(TixkSearchActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.saixuan:
                pPopupWindow.showAtLocation(TixkSearchActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.go_search:


                break;
        }
    }

    private void getView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //步骤二：用add()方法加上Fragment的对象rightFragment
        TickSearchListFragment rightFragment = TickSearchListFragment.newInstance("");
        transaction.add(R.id.content, rightFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    private SelectTourWindow.ItemInclick itemsOnClick = new SelectTourWindow.ItemInclick(){

        @Override
        public void ItemClick(String tab, String theme_id, String grade) {
            NotificationCenter.defaultCenter().publish(new TickSearchEvent("",theme_id,grade));//主题
        }
    };

    private TickBottomWindow.ItemInclick itemsOnslick = new TickBottomWindow.ItemInclick() {
        @Override
        public void ItemClick(String order) {
            NotificationCenter.defaultCenter().publish(new TickSearchEvent(order,"",""));//主题
        }
    };
}
