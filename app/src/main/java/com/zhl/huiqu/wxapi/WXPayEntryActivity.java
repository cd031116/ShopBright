package com.zhl.huiqu.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.Constants;

import org.aisen.android.component.eventbus.NotificationCenter;

import java.util.ArrayList;

/**
 * 微信支付返回
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    private TextView tvPayCallBack;
//    private ShowMsgDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID,true);
        api.handleIntent(getIntent(), this);
        tvPayCallBack = (TextView) findViewById(R.id.tvPayCallBack);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(final BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            String orderId="";
//            //产品订单编号
//            if (MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_ID_KEY) instanceof String){
//                orderId=MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_ID_KEY).toString();
//            }
//            if (resp.errCode==0){
//                //成功了
//                TaskUtil.queryWeChatOrderTask(WXPayEntryActivity.this, new ITaskResultListener() {
//                    @Override
//                    public void onSuccessResult(Object result) {
//                        dismissAlert();
//                        //支付成功了
//                        if (result instanceof BOrderBean){
//                            if (MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_TYPE) instanceof String){
//                                //总的金额
//                                String money = "0";
//                                if (MapUtil.sharedInstance().getDefaultValue(Constants.PAY_MONEY) instanceof String){
//                                    money = MapUtil.sharedInstance().getDefaultValue(Constants.PAY_MONEY).toString();
//                                }
//
//                                String orderType=MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_TYPE).toString();
//                                if (orderType.equals(Consts.PayType.Pay_Product_Buy.getPayTypeName())){
//                                    //产品购买
//                                    if (MapUtil.sharedInstance().getDefaultValue(Constants.PAY_PRODUCTS_ARRAY) instanceof ArrayList){
//                                        //产品集合
//                                        ArrayList<BProductItemBean> products= (ArrayList<BProductItemBean>) MapUtil.sharedInstance()
//                                                .getDefaultValue(Constants.PAY_PRODUCTS_ARRAY);
//                                        ArrayList<ProductBean> list = new ArrayList<>();
//                                        for (BProductItemBean bean:products){
//                                            ProductBean product = new ProductBean();
//                                            product.setId(bean.getProductId());
//                                            list.add(product);
//                                        }
//                                        ShoppingCart.getInstance().productClear(list);
//                                    }
//                                    NotificationCenter.defaultCenter().publish(new BuyProductEvent());
//                                    dialog("购买成功",1);
//                                }else if(orderType.equals(Consts.PayType.Pay_Orderid_Buy.getPayTypeName())){
//                                    //订单购买
//                                    dialog("购买成功",2);
//                                    NotificationCenter.defaultCenter().publish(new OrderBuyEvent());
//                                } else {
//                                    //打赏
//                                    if (MapUtil.sharedInstance().getDefaultValue(Constants.PAY_PRODUCT_ID) instanceof String){
//                                        //产品编号
//                                        String PAY_PRODUCT_ID=MapUtil.sharedInstance().getDefaultValue(Constants.PAY_PRODUCT_ID).toString();
//
//                                        NotificationCenter.defaultCenter().publish(new ProductRewardEvent(PAY_PRODUCT_ID, money));
//                                    }
//                                    dialog("打赏成功",3);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailedResult(Object result) {
//                        dismissAlert();
//                        //支付失败了
//                        if (result instanceof BOrderBean){
//                            tvPayCallBack.setText("支付失败");
//                            dialog("支付失败",-1);
//                        }
//                    }
//
//                    @Override
//                    public void onPrepare() {
//                        showAlert("正在查询支付状态",true);
//                    }
//                });
//            }else if (resp.errCode==-2){
//                //用户没有操作
//                this.finish();
//            }else{
//                dialog("签名问题,支付失败",-1);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MapUtil.sharedInstance().clear();
    }

    private void dialog(String title,int dex){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setMessage(title); //设置内容
        builder .setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
             WXPayEntryActivity.this.finish();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
//    /**
//     * 显示加载图标
//     * @param txt
//     */
//    public void showAlert(String txt,final boolean isCancel){
//        if(!"".equals(txt)&&txt!=null){
//            if(progressDialog==null){
//                progressDialog=new ShowMsgDialog(this,isCancel);
//            }
//            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){
//                @Override
//                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        if(isCancel){
//                            progressDialog.dismiss();
//                        }
//                    }
//                    return false;
//                }
//            });
//            progressDialog.show();
//            progressDialog.showText(txt);
//        }
//    }
//    /**
//     * 关闭加载图标
//     */
//    public void dismissAlert(){
//        if(progressDialog!=null&&progressDialog.isShowing()){
//            progressDialog.dismiss();
//        }
//    }
}