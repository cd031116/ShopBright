package com.zhl.huiqu.interfaces;

/**
 * 任务回调
 * Created by lyj on 2017/8/1
 */
public interface ITaskResultListener {

    /**
     * 成功后返回
     * @param result
     */
    void onSuccessResult(Object result);

    /**
     * 失败后的回调
     * @param result
     */
    void onFailedResult(Object result);

    /**
     * 准备可以用来弹框
     */
    void onPrepare();
}
