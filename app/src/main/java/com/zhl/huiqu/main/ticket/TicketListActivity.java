package com.zhl.huiqu.main.ticket;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class TicketListActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;
    private String title;
    private String theme_id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_list;
    }

    @Override
    public void initView(){
        super.initView();
        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            title=bd.getString("title");
            theme_id=bd.getString("theme_id");
        }
        top_title.setText(title);
    }

    @Override
    public void initData() {
        super.initData();
        getView();
    }

    @OnClick({R.id.top_left, R.id.top_image})
    void onClicked(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                TicketListActivity.this.finish();
                break;

        }
    }
    private void getView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //步骤二：用add()方法加上Fragment的对象rightFragment
        TicksListFragment rightFragment = TicksListFragment.newInstance(theme_id);
        transaction.add(R.id.content, rightFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }
}
