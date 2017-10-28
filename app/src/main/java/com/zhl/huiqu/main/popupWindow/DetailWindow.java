package com.zhl.huiqu.main.popupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;

/**
 * Created by Administrator on 2017/10/27.
 */

public class DetailWindow extends PopupWindow {
    private Activity mContext;
    private View view;
    private TextView chengr;
    private TextView er_tong;
    private TextView baoxian;
    private TextView order_pay_price,commit_pay;
    private ItemInclick itemsOnClick;
    private LinearLayout main_top;
    public DetailWindow(Activity mContext,String cr,String rt,String bx,String total,ItemInclick itemsOnClickd) {
        this.mContext=mContext;
        this.itemsOnClick = itemsOnClickd;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.detail_window, null);
        chengr= (TextView) view.findViewById(R.id.chengr);
        er_tong= (TextView) view.findViewById(R.id.er_tong);
        baoxian= (TextView) view.findViewById(R.id.baoxian);
        order_pay_price= (TextView) view.findViewById(R.id.order_pay_price);
        commit_pay= (TextView) view.findViewById(R.id.commit_pay);
        main_top= (LinearLayout) view.findViewById(R.id.main_top);
        chengr.setText(cr);
        er_tong.setText(rt);
        baoxian.setText(bx);
        order_pay_price.setText(total);
        main_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        commit_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.ItemClick();
                dismiss();
            }
        });



    /* 设置弹出窗口特征 */
        // 设置视图
        this.setOutsideTouchable(true);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setContentView(view);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.select_anim);
        this.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int with=this.getContentView().getWidth();
        int hight=this.getContentView().getHeight();
        Log.i("bbbb","hight="+hight);
    }

    public interface ItemInclick {
        void ItemClick();
    }

    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     * @param v
     */
    public void showUp2(View v,int popupWidth,int popupHeights) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
         int   popupHeight=  px2dip(mContext,popupHeights);
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
