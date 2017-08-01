package com.zhl.huiqu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhl.huiqu.base.Consts;
import com.zhl.huiqu.bean.BRewardBean;


/**
 * Created by lyj on 2017/8/1.
 */

public class ToBuyUtils {
    /**
     * 调起微信支付
     * @param info
     */
    public static void lunchWeChat(Context context, Consts.PayType payType, BRewardBean info){
        //获取到打赏订单等信息
        if (String.valueOf("success").equals(info.getMessage())){
            IWXAPI msgApi = WXAPIFactory.createWXAPI(context,null);
            msgApi.registerApp(Constants.WE_CHAT_APP_ID);
            PayReq req = ToBuyUtils.getWeChatPayReq(info);
            msgApi.sendReq(req);
        }else {
            Toast.makeText(context,"支付失败,请重试!",Toast.LENGTH_SHORT).show();
        }
        //存入订单编号，供后面查询订单支付情况
        MapUtil.sharedInstance().putDefaultValue(Constants.ORDER_ID_KEY,info.getOutTradeNo());
        //存入微信支付类型
        MapUtil.sharedInstance().putDefaultValue(Constants.ORDER_TYPE,payType.getPayTypeName());
    }

    /**
     * 获取微信支付的PayReq
     *
     * @param bean
     * @return
     */
    public static PayReq getWeChatPayReq(BRewardBean bean) {
        PayReq req = new PayReq();
        req.appId = bean.getAppId();//微信ID
        req.partnerId = bean.getPartnerId();//商户ID
        req.prepayId = bean.getPrepayId();//预支付订单编号
        req.packageValue =bean.getWechatPackage();
        req.nonceStr= bean.getNonceStr();
        req.timeStamp = bean.getTimeStamp();
        req.sign = bean.getSign();
        return req;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String data) {
        return data == null || data.trim().length() < 1 || "null".equals(data.toLowerCase()) || data.equals("false");
    }

    public static String formatString(String str, String defaultStr) {
        if (null == str || str.equals("") || str.length()<1) {
            return defaultStr;
        }
        return str;
    }

    public static String formatObject(Object object, String defaultStr) {
        if (object == null) {
            return defaultStr;
        }
        return object.toString();
    }

    public static int formatNum(String intStr, int defaultValue) {
        int value;
        if (TextUtils.isEmpty(intStr) || intStr.equals("") || intStr.equals("null"))
            return defaultValue;
        try {
            value = Integer.parseInt(intStr);
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    public static double formatDoubleNum(String intStr, double defaultValue) {
        double value;
        if (TextUtils.isEmpty(intStr) || intStr.equals("") || intStr.equals("null"))
            return defaultValue;
        try {
            value = Double.parseDouble(intStr);
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

}
