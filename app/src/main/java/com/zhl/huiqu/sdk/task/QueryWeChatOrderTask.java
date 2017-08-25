package com.zhl.huiqu.sdk.task;

import android.app.Activity;
import android.widget.Toast;

import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.bean.BOrderBean;
import com.zhl.huiqu.interfaces.ITaskResultListener;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.MapUtil;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;


/**
 *
 * Created by  on 2017/2/18.
 */
public class QueryWeChatOrderTask extends WorkTask<Void, Void, BaseInfo> {

    private Activity context;

    private ITaskResultListener resultListener;

    public QueryWeChatOrderTask(Activity context,ITaskResultListener resultListener){
        this.context=context;
        this.resultListener=resultListener;
    }

    @Override
    public BaseInfo workInBackground(Void... params) throws TaskException {
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //产品编号
        String orderId= MapUtil.sharedInstance().getDefaultValue(Constants.PAY_PRODUCT_ID).toString();
        String orderType=MapUtil.sharedInstance().getDefaultValue(Constants.ORDER_TYPE).toString();
        return SDK.newInstance(context).getQueryOrder(orderId);
    }

    @Override
    protected void onSuccess(BaseInfo orderInfo) {
        super.onSuccess(orderInfo);
        resultListener.onSuccessResult(orderInfo);
//        if (orderInfo!=null&&resultListener!=null){
//            if (orderInfo.getTradeState().equals(String.valueOf(1))){
//            }else{
//                resultListener.onFailedResult(orderInfo);
//            }
//        }
    }

    @Override
    protected void onFailure(TaskException exception) {
        super.onFailure(exception);
        if (resultListener!=null){
            resultListener.onFailedResult(exception);
        }
    }

}
