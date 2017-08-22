package com.zhl.huiqu.utils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhl.huiqu.BuildConfig;
import com.zhl.huiqu.base.BaseConfig;
import com.zhl.huiqu.widget.FooterItemView;

import org.aisen.android.ui.fragment.itemview.AFooterItemView;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by wangdan on 17/2/18.
 */

public class Utils {

    static final DecimalFormat priceFormat_01 = new DecimalFormat("00.00");

    static final DecimalFormat priceFormat_02 = new DecimalFormat("0.00");

    public static String formatPrice(float price) {
        if (price > 10.0f) {
            return priceFormat_01.format(price);
        }

        return priceFormat_02.format(price);
    }

    public static String formatDuration(long duration) {
        if (duration < 60) {
            return duration + "分钟";
        }
        else {
            return duration / 60 + "小时";
        }
    }

    public static  <T extends Serializable> IItemViewCreator<T> configFooterViewCreator(final Activity activity, final AFooterItemView.OnFooterViewCallback callback) {
        return new IItemViewCreator<T>() {

            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(FooterItemView.LAYOUT_RES, parent, false);
            }

            @Override
            public IITemView<T> newItemView(View convertView, int viewType) {
                return new FooterItemView<T>(activity, convertView, callback);
            }

        };
    }

    public static String splitt(String sd){
        String[] sArray=sd.split(":");
        return sArray[0];
    }
    public static String splitt1(String sd){
        String[] sArray=sd.split(":");
        return sArray[1];
    }

    public static long  getcuo(String sd){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= null;
        try {
            date = sdf .parse(sd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeStemp = date.getTime();
        return timeStemp;
    }

    public static long  gettcuo(String sd){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date= null;
        try {
            date = sdf .parse(sd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeStemp = date.getTime();
        return timeStemp;
    }



    public static JSONObject tojson(Map<String,Object> map1){
        JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(map1));
        return  itemJSONObj;
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getImageUrl(String url){
        if(TextUtils.isEmpty(url)){
            return "";
        }
        if(url.contains("http://")){
            return url;
        }else {
            url= BuildConfig.BASE_URL+url;
            return url;
        }
    }
}
