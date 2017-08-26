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
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.base.Consts;
import com.zhl.huiqu.interfaces.ITaskResultListener;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.MapUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.TaskUtil;
import com.zhl.huiqu.widget.ShowMsgDialog;

import org.aisen.android.component.eventbus.NotificationCenter;

import java.util.ArrayList;

/**
 * 微信支付返回
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    private TextView tvPayCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID, true);
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
            String orderId = "";
            //产品订单编号
            if (MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_ID_KEY) instanceof String) {
                orderId = MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_ID_KEY).toString();
            }
            if (resp.errCode == 0) {
//                //成功了
                TLog.log("tttt", "--成功了:" );
                tvPayCallBack.setText("正在查询支付状态");
                TaskUtil.queryWeChatOrderTask(WXPayEntryActivity.this, new ITaskResultListener() {
                    @Override
                    public void onSuccessResult(Object result) {
                        //支付成功了
                        dialogs("支付成功");
                    }

                    @Override
                    public void onFailedResult(Object result) {
                        //支付失败了
                        tvPayCallBack.setText("支付失败");
                        dialog("支付失败", "");
                    }

                    @Override
                    public void onPrepare(){

                    }
                });
            } else if (resp.errCode == -2) {
                //用户没有操作
                tvPayCallBack.setText("支付取消");
                this.finish();
            } else {
                TLog.log("tttt", "--支付失败:" );
                dialog("签名问题,支付失败", resp.errCode + "");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapUtil.sharedInstance().clear();
    }

    private void dialog(String title, String dex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setMessage(title + "==" + dex); //设置内容
        builder.setCancelable(false);
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

    private void dialogs(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setMessage(title); //设置内容
        builder.setCancelable(false);
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

}