package com.zhl.huiqu.utils;

import android.app.Activity;
import android.content.Context;

import com.zhl.huiqu.interfaces.ITaskResultListener;
import com.zhl.huiqu.sdk.task.QueryWeChatOrderTask;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * 任务操作类
 * Created by lyj on 2017/2/18.
 */
public class TaskUtil {

    /**
     * 查询微信支付订单状态
     * @param context
     * @param resultListener
     */
    public static void queryWeChatOrderTask(Activity context,ITaskResultListener resultListener){
        try{
            new QueryWeChatOrderTask(context,resultListener).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
