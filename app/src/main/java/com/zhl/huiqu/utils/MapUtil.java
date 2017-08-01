package com.zhl.huiqu.utils;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 存储键值对
 * Created by lyj on 2017/8/1.
 */
public class MapUtil {

    private HashMap<String,Object> mParams;

    private static MapUtil mapUtil;

    private MapUtil(){
        mParams=new HashMap<>();
    }

    public static MapUtil sharedInstance(){
        if (mapUtil==null){
            mapUtil=new MapUtil();
        }
        return mapUtil;
    }

    /**
     * 存入值
     * @param key
     * @param value
     */
    public void putDefaultValue(String key,Object value){
        if (TextUtils.isEmpty(key)){
            return;
        }
        mParams.put(key,value);
    }

    /**
     * 取出值
     * @param key
     * @return
     */
    public Object getDefaultValue(String key){
        if (TextUtils.isEmpty(key)||!mParams.containsKey(key)){
            return "";
        }
        return mParams.get(key);
    }



    public void clear(){
        if (mParams!=null){
            mParams.clear();
        }
    }
}
