package com.zhl.huiqu.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.aisen.android.common.context.GlobalContext;


/**
 * Created by lyj on 17/8/2.
 */

public class MyApplication extends GlobalContext {
    public static MyApplication instance;
    private ActivityManagerd activityManager = null;



    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        activityManager = ActivityManagerd.getScreenManager();
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
//        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }
}