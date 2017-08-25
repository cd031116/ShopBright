package com.zhl.huiqu.base;

/**
 * 枚举常量
 *
 * Created by wangdan on 17/2/12.
 */

public class Consts {

    //品类类型 HomePage-首页类型  Hot-热门 Carousel-轮播
    public enum ProductCategoryType {
        HomePage, Hot, Carousel
    }

    // 用户登录方式
    public enum LoginType {
        phone, QQ, weChart,verifyCode
    }

    public static class ShareType {

        public static final String CHANNEL_QQ = "QQ";

        public static final String CHANNEL_WECHAT = "Wechat";

    }

    /**
     * 微信支付的类型
     */
    public enum PayType{

        Pay_Product_Buy("purchase"),   //产品购买
        Pay_Orderid_Buy("orderId");   //订单购买

        private String payTypeName;

        PayType(String payTypeName){
            this.payTypeName=payTypeName;
        }

        public String getPayTypeName() {
            return payTypeName;
        }

        public void setPayTypeName(String payTypeName) {
            this.payTypeName = payTypeName;
        }
    }

    public static class ProductItemType {

        public static final int video = 0;

        public static final int pic = 1;

    }

}
