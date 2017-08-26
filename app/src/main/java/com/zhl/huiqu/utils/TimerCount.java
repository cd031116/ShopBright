package com.zhl.huiqu.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/25.
 */

public class TimerCount extends CountDownTimer {
    private TextView bnt;

    public TimerCount(long millisInFuture, long countDownInterval,
                      TextView bnt) {
        super(millisInFuture, countDownInterval);
        this.bnt = bnt;
    }

    @Override
    public void onFinish() {
        bnt.setClickable(true);
        bnt.setText("重获验证码");
        bnt.setEnabled(true);
    }

    @Override
    public void onTick(long arg0) {
        // if(bnt!=null){
        bnt.setClickable(false);
        bnt.setText("验证码" + arg0 / 1000 + "S");
        bnt.setEnabled(false);
        // }
    }
}