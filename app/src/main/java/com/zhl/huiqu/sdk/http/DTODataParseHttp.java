package com.zhl.huiqu.sdk.http;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhl.huiqu.base.BaseBean;

import org.aisen.android.network.http.DefHttpUtility;
import org.aisen.android.network.task.TaskException;

/**
 * @author lyj
 * @description 将服务端的接口数据中的Data包装体转换成DTO
 * @date 2017/7/26
 */
public class DTODataParseHttp extends DefHttpUtility {
    private Activity context;

    public DTODataParseHttp(Activity context) {
        this.context = context;
    }

    @Override
    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {
        Log.i("tttt", "resultStr=" + resultStr);
        try {
            if(TextUtils.isEmpty(resultStr)){
                throw new TaskException("数据为空");
            }
            JSONObject jsonObject = JSON.parseObject(resultStr);
            T result;

            String code = jsonObject.getString("code");
            if("1".equals(code)){
                result = super.parseResponse(resultStr, responseCls);
            }else {
                String codes= jsonObject.getString("code");
                if(jsonObject.containsKey("message")){
                    String message= jsonObject.getString("message");
                    throw new TaskException(codes,message);
                }else {
                    String msg= jsonObject.getString("msg");
                    throw new TaskException(codes,msg);
                }
            }
//            BaseBean bean = null;
//            if (code.equals("3")) {
//                result = super.parseResponse(resultStr, responseCls);
//            } else {
//                if (jsonObject.containsKey("data")) {
//                    result = super.parseResponse(jsonObject.getString("data"), responseCls);
//                    if (result instanceof BaseBean) {
//                        bean = (BaseBean) result;
//                    }
//                } else {
//                    bean = new BaseBean();
//                    result = (T) bean;
//                }
//                if (bean != null) {
//                    bean.setCode(jsonObject.getString("code"));
//                    String msg="";
//                    if(jsonObject.containsKey("message")){
//                        msg=jsonObject.getString("message");
//                    }else {
//                        msg=jsonObject.getString("msg");
//                    }
//                    bean.setMessage(msg);
//                }
//            }
            return result;

        } catch (Throwable e) {
            if (e instanceof TaskException) {
                throw e;
            }
            throw new TaskException(TaskException.TaskError.resultIllegal.toString());
        }
    }
}
