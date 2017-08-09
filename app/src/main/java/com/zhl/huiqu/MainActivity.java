package com.zhl.huiqu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.DoubleClickExitHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private DoubleClickExitHelper mDoubleClickExit;
    @Bind(R.id.home_line)
    LinearLayout home_line;
    @Bind(R.id.service_line)
    LinearLayout service_line;
    @Bind(R.id.rim_line)
    LinearLayout rim_line;
    @Bind(R.id.my_line)
    LinearLayout my_line;
    @Bind({R.id.image_home,R.id.image_service,R.id.image_rim,R.id.image_my})
    List<ImageButton> mTabs;
    @Bind({R.id.text_home,R.id.service_text,R.id.text_rim,R.id.text_my})
    List<TextView> mText;
    FragmentManager mFragmentMan;
    int selected=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(){
        super.initView();
        mDoubleClickExit = new DoubleClickExitHelper(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    // 3. 先进第二个或第三个的子模块，再返回首页，加载不出数据
    private String lastFragmentTag = null;
    private void changeFrament(String tag,int index){
        if (mFragmentMan != null) {
            // Add default fragments to view. Try to reuse old fragments or create new ones
            FragmentTransaction transaction = mFragmentMan.beginTransaction();
            // 当前的Fragment
            Fragment mCurrentFragment = mFragmentMan.findFragmentByTag(tag);
            // 前一次Fragment
            Fragment mLastFragment = null;
            if (!TextUtils.isEmpty(lastFragmentTag) && !lastFragmentTag.equals(tag)){
                mLastFragment = mFragmentMan.findFragmentByTag(lastFragmentTag);
            }
            // 构造当前Fragment
            if (mCurrentFragment == null){
                switch (index){
                    case 0:
//                        mCurrentFragment = MainProductTabFragment.newInstance();
                        break;
                    case 1:
//                       mCurrentFragment =StartScanActivity.launch(MainActivity.this);//子Fragment实例
                        break;
                    case 2:
//                        mCurrentFragment =FindFragment.newInstance();//子Fragment实例
                        break;
                    case 3:
//                        mCurrentFragment =IsMeFragment.newInstance();//子Fragment实例
                        break;
                }
                transaction.add(R.id.id_content, mCurrentFragment, tag);
            }
            // 显示当前Fragment
            else {
                transaction.show(mCurrentFragment);
            }
            // 隐藏前一次Fragment
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            transaction.commit();

            lastFragmentTag = tag;
        }
    }



    @OnClick({R.id.home_line, R.id.service_line, R.id.rim_line, R.id.my_line,R.id.image_home,R.id.image_service,R.id.image_rim,R.id.image_my})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.home_line:
            case  R.id.image_home:
                if(selected==0){
                    break;
                }
                selected=0;
                switchContent(selected);
                break;
            case R.id.service_line:
            case  R.id.image_service:
                if(selected==1){
                    break;
                }
                selected=1;
                switchContent(selected);
                break;
            case R.id.rim_line:
            case  R.id.image_rim:
                if(selected==2){
                    break;
                }
                selected=2;
                switchContent(selected);
                break;
            case R.id.my_line:
            case  R.id.image_my:
                if(selected==3){
                    break;
                }
                selected=3;
                switchContent(selected);
                break;
        }
    }

    void switchContent(int select){
        initFoot();
        if(select==0){
            mTabs.get(0).setSelected(true);
            mText.get(0).setTextColor(Color.parseColor("#00d2ec"));
            changeFrament("pFragment",0);
        }else if(select==1){
            mTabs.get(1).setSelected(true);
            mText.get(1).setTextColor(Color.parseColor("#00d2ec"));
            changeFrament("dFragment",1);
        }else if(select==2){
            mTabs.get(2).setSelected(true);
            mText.get(2).setTextColor(Color.parseColor("#00d2ec"));
            changeFrament("dFragment",2);
        }else if(select==3){
            mTabs.get(3).setSelected(true);
            mText.get(3).setTextColor(Color.parseColor("#00d2ec"));
            changeFrament("fFragment",3);
        }
    }

    private void initFoot(){
        mTabs.get(0).setSelected(false);
        mTabs.get(1).setSelected(false);
        mTabs.get(2).setSelected(false);
        mTabs.get(3).setSelected(false);
        mText.get(0).setTextColor(Color.parseColor("#a1b0b2"));
        mText.get(1).setTextColor(Color.parseColor("#999999"));
        mText.get(2).setTextColor(Color.parseColor("#a1b0b2"));
        mText.get(3).setTextColor(Color.parseColor("#a1b0b2"));
    }
    @Override
    public void onResume(){
        super.onResume();
        switchContent(selected);
    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i=0;i<mTabs.size();i++){
            if(mTabs.get(i).isSelected()){
                selected=i;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
