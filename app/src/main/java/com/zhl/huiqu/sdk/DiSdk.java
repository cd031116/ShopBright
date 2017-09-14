package com.zhl.huiqu.sdk;

import android.app.Activity;

import com.zhl.huiqu.personal.bean.UrLikeBean;
import com.zhl.huiqu.sdk.http.DTOInfoParseHttp;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.DateUtil;
import com.zhl.huiqu.utils.TLog;

import org.aisen.android.common.setting.Setting;
import org.aisen.android.network.biz.ABizLogic;
import org.aisen.android.network.http.HttpConfig;
import org.aisen.android.network.http.IHttpUtility;
import org.aisen.android.network.http.Params;
import org.aisen.android.network.task.TaskException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/13.
 */

public class DiSdk extends ABizLogic {
    private static Activity context;

    private DiSdk() {
        this(CacheMode.disable);
    }

    private DiSdk(CacheMode mode) {
        super(mode);
    }

    public static DiSdk newInstance(Activity context) {
        DiSdk.context = context;
        return newInstance(CacheMode.disable);
    }

    public static DiSdk newInstance(CacheMode mode) {
        return new DiSdk(mode);
    }

    /**
     * 封装基础参数
     *
     * @param paramsJson
     * @return
     */
    public JSONObject getBasicParams(JSONObject paramsJson) {
        try {
            Params basicParams = basicParams(null);
            for (String key : basicParams.getKeys()) {
                paramsJson.put(key, basicParams.getParameter(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsJson;
    }

    /**
     * 获取猜你喜欢
     *
     * @return
     * @throws TaskException
     */
    public UrLikeBean getYouLike(String body) throws TaskException {
        Setting action = newSetting("getYouLike", "appapi/personalcenter/getYouLike", "获取猜你喜欢");
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?device_num=" + "");
        return doPost(configHttpConfig(), action, null,null,body, UrLikeBean.class);
    }
    


    @Override
    protected IHttpUtility configHttpUtility(){
        return new DTOInfoParseHttp(context);
    }




    @Override
    protected HttpConfig configHttpConfig(){
        HttpConfig config = new HttpConfig();
        // 服务端请求地址
        config.baseUrl = Constants.Base_URL;
//        http://192.168.10.115:9100
        config.addHeader("Content-Type", "application/json");
        return config;
    }

    // 服务端参数基础封装
    private Params basicParams(Params params) {
        if (params == null) {
            params = new Params();
        }
        return params;
    }


}
