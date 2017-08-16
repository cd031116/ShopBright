package com.zhl.huiqu.main.ticket;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;


public class TixkSearchActivity extends BaseActivity {
    @Bind(R.id.content)
    FrameLayout frameLayout;
    @Bind(R.id.top_title)
    TextView top_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixk_search;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText("门票搜索");
        getView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.top_left, R.id.top_image})
    void onClicked(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                TixkSearchActivity.this.finish();
                break;

        }
    }

    private void getView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //步骤二：用add()方法加上Fragment的对象rightFragment
        TickListFragment rightFragment = TickListFragment.newInstance();
        transaction.add(R.id.content, rightFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }
}
