package com.zhl.huiqu;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.base.DoubleClickExitHelper;
import com.zhl.huiqu.base.MyApplication;
import com.zhl.huiqu.main.MainTabFragment;
import com.zhl.huiqu.main.location.LocationService;
import com.zhl.huiqu.main.ticket.TickListFragment;
import com.zhl.huiqu.personal.KefuCenterFragment;
import com.zhl.huiqu.personal.PersonalFragment;
import com.zhl.huiqu.sdk.eventbus.CityEvent;
import com.zhl.huiqu.utils.Constants;

import org.aisen.android.common.utils.Logger;
import org.aisen.android.component.eventbus.NotificationCenter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
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
    private LocationService locationService;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(){
        super.initView();
        mFragmentMan = getFragmentManager();
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
                        mCurrentFragment = MainTabFragment.newInstance();
                        break;
                    case 1:
                       mCurrentFragment = KefuCenterFragment.newInstance(1);//子Fragment实例
                        break;
                    case 2:
                        mCurrentFragment = TickListFragment.newInstance("0");;//子Fragment实例
                        break;
                    case 3:
                        mCurrentFragment = PersonalFragment.newInstance();//子Fragment实例
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
            mText.get(0).setTextColor(Color.parseColor("#e11818"));
            changeFrament("aFragment",0);
            Logger.d("select==0");
        }else if(select==1){
            mTabs.get(1).setSelected(true);
            mText.get(1).setTextColor(Color.parseColor("#e11818"));
            changeFrament("bFragment",1);
        }else if(select==2){
            mTabs.get(2).setSelected(true);
            mText.get(2).setTextColor(Color.parseColor("#e11818"));
            changeFrament("cFragment",2);
        }else if(select==3){
            mTabs.get(3).setSelected(true);
            mText.get(3).setTextColor(Color.parseColor("#e11818"));
            changeFrament("dFragment",3);
            Logger.d("select==3");
        }
    }

    private void initFoot(){
        mTabs.get(0).setSelected(false);
        mTabs.get(1).setSelected(false);
        mTabs.get(2).setSelected(false);
        mTabs.get(3).setSelected(false);
        mText.get(0).setTextColor(Color.parseColor("#333333"));
        mText.get(1).setTextColor(Color.parseColor("#333333"));
        mText.get(2).setTextColor(Color.parseColor("#333333"));
        mText.get(3).setTextColor(Color.parseColor("#333333"));
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
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        requireSomePermission();
        // -----------location config ------------
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer();
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());

                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                BaseConfig bg=new BaseConfig(MainActivity.this);
                bg.setStringValue(Constants.Address,location.getCity());
                NotificationCenter.defaultCenter().publish(new CityEvent());
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append(poi.getName() + ";");
//                    }
//                }

            }
        }

    };
    private static final int num = 123;//用于验证获取的权
    @AfterPermissionGranted(num)
    private void requireSomePermission() {
        String[] perms = {
                // 把你想要申请的权限放进这里就行，注意用逗号隔开
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            locationService = ((MyApplication) getApplication()).locationService;
            //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
            locationService.registerListener(mListener);
            //注册监听
            int type = getIntent().getIntExtra("from", 0);
            if (type == 0) {
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            } else if (type == 1) {
                locationService.setLocationOption(locationService.getOption());
            }

            locationService.start();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要使用您定位权限!",
                    num, perms);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        locationService.start();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
