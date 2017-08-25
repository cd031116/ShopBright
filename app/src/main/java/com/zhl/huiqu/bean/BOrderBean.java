package com.zhl.huiqu.bean;

import com.zhl.huiqu.base.BaseInfo;

import java.io.Serializable;



/**
 * 订单相关
 * Created by 张中伟 on 2017/2/18.
 */

public class BOrderBean extends BaseInfo implements Serializable{

    private static final long serialVersionUID = 791543850563782898L;

    /*商户订单号*/
    private String outTradeNo;

    /*支付状态 1-支付成功 0-未支付或支付失败*/
    private String tradeState;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }
}
