package com.zhl.huiqu.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.zhl.huiqu.main.location.LocationService;
import com.zhl.huiqu.main.search.SearchHistoryManager;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.common.context.GlobalContext;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by lyj on 17/8/2.
 */

public class MyApplication extends GlobalContext {
    public static MyApplication instance;
    private ActivityManagerd activityManager = null;
    public LocationService locationService;
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        SDKInitializer.initialize(getApplicationContext());
        locationService = new LocationService(getApplicationContext());
        activityManager = ActivityManagerd.getScreenManager();
        SearchHistoryManager.setup(this);
        getOkHttpClient().setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));

    }


    public static MyApplication getInstance(){
        return instance;
    }

    public ActivityManagerd getActivityManager(){
        return activityManager;
    }

    public boolean isPublic(){

        return true;
    }

    /**
     * d
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }
}