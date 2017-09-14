package com.zhl.huiqu.sdk.http;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.zhl.huiqu.sdk.DefHttpUtility;

import org.aisen.android.network.task.TaskException;

/**
 * Created by Administrator on 2017/9/13.
 */

public class DTOInfoParseHttp extends DefHttpUtility {
    private Activity context;

    public DTOInfoParseHttp(Activity context) {
        this.context = context;
    }

    @Override
    public synchronized OkHttpClient getOkHttpClient() {


        return super.getOkHttpClient();
    }

    public DTOInfoParseHttp() {
        super();
    }

    @Override
    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {
        Log.i("tttt", "resultStr=" + resultStr);

        try {
            if (TextUtils.isEmpty(resultStr)) {
                throw new TaskException("数据为空");
            }
            JSONObject jsonObject = JSON.parseObject(resultStr);
            T result;
            String code = jsonObject.getString("returnCode");
            if ("231000".equals(code)) {
                result = super.parseResponse(resultStr, responseCls);
            } else {
                String message = jsonObject.getString("msg");
                throw new TaskException(code, message);
            }
            return result;

        } catch (Throwable e) {
            if (e instanceof TaskException) {
                throw e;
            }
            throw new TaskException(TaskException.TaskError.resultIllegal.toString());
        }
    }
}
