package com.zhl.huiqu.bean;

import com.zhl.huiqu.base.BaseBean;

import java.io.Serializable;


/**
 *
 * Created by  on 17/2/17.
 */
public class BRewardBean extends BaseBean implements Serializable{
    private static final long serialVersionUID = -7236002082515439085L;
    /**
     * "payChannel": "apple", //支付模式 apple-苹果支付  other-其他方式
     "tradeType": null,
     "appId": "wx51721c0537b87ebe",
     "partnerId": "1383085302",
     "prepayId": "wx201702101925500aed998ef40754361952",
     "wechatPackage": "Sign=WXPay",
     "nonceStr": "b82b06038b5646e78790a7247fe394df",
     "timeStamp": "1486725936",
     "signType": null,
     "sign": "0777956251E7A1FE692D580B1051BFA7",
     "codeUrl": null
     */
    private String payChannel;//支付模式 apple-苹果支付  other-其他方式

    private String tradeType;

    private String appId;

    private String partnerId;

    private String prepayId;

    private String wechatPackage;

    private String nonceStr;

    private String timeStamp;

    private String signType;

    private String sign;

    private String codeUrl;

    private String outTradeNo;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getWechatPackage() {
        return wechatPackage;
    }

    public void setWechatPackage(String wechatPackage) {
        this.wechatPackage = wechatPackage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
