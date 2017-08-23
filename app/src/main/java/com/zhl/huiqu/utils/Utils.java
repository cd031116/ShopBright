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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        if(url.contains("http://")||url.contains("www.")){
            return url;
        }else {
            url= BuildConfig.BASE_URL+url;
            return url;
        }
    }

    public static boolean isIdNum(String idNum) {
        // 中国公民身份证格式：长度为15或18位，最后一位可以为字母
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        // 格式验证
        if (!idNumPattern.matcher(idNum).matches())
            return false;

        // 合法性验证

        int year = 0;
        int month = 0;
        int day = 0;

        if (idNum.length() == 15) {

            // 一代身份证

            System.out.println("一代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf("19" + birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));

            }

        } else if (idNum.length() == 18) {

            // 二代身份证

            System.out.println("二代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf(birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));
            }

        }

        // 年份判断，100年前至今

        Calendar cal = Calendar.getInstance();

        // 当前年份
        int currentYear = cal.get(Calendar.YEAR);

        if (year <= currentYear - 100 || year > currentYear)
            return false;

        // 月份判断
        if (month < 1 || month > 12)
            return false;

        // 日期判断

        // 计算月份天数

        int dayCount = 31;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayCount = 31;
                break;
            case 2:
                // 2月份判断是否为闰年
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    dayCount = 29;
                    break;
                } else {
                    dayCount = 28;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                dayCount = 30;
                break;
        }

        System.out.println(String.format("生日：%d年%d月%d日", year, month, day));

        System.out.println(month + "月份有：" + dayCount + "天");

        if (day < 1 || day > dayCount)
            return false;

        return true;
    }
}
