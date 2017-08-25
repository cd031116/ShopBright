package com.zhl.huiqu.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhl.huiqu.base.BaseBean;
import com.zhl.huiqu.base.BaseInfo;

import java.io.Serializable;


/**
 *
 * Created by  on 17/2/17.
 */
public class BRewardBean  implements Serializable{
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


    private String appid;

    private String partnerid;

    private String prepayid;

    @JSONField(name="package")
    private String wechatPackage;

    private String noncestr;

    private String timestamp;

    private String signType;

    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getWechatPackage() {
        return wechatPackage;
    }

    public void setWechatPackage(String wechatPackage) {
        this.wechatPackage = wechatPackage;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
}
