package com.zhl.huiqu.main.popupWindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ticket.SpotThemeInfo;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 2017/8/19.
 */

public class TickBottomWindow extends PopupWindow{
    private Activity mContext;
    private View view;
    private TextView text1;
    private TextView di,di_2;//二栏

    private TextView gao,gao_2;//三栏
    private TextView ju,ju_2;//四栏
    private ImageView one,two,three,four;
    private  RelativeLayout item1,item2,item3,item4;
    private ItemInclick itemsOnClick;
    private int select=1;
    public TickBottomWindow(Activity mContext, ItemInclick itemsOnClickd) {
        this.mContext=mContext;
        this.itemsOnClick=itemsOnClickd;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.tick_bottom_window, null);

        text1= (TextView) view.findViewById(R.id.text1);

        di= (TextView) view.findViewById(R.id.di);
        di_2= (TextView) view.findViewById(R.id.di_2);

        gao= (TextView) view.findViewById(R.id.gao);
        gao_2= (TextView) view.findViewById(R.id.gao_2);

        ju= (TextView) view.findViewById(R.id.ju);
        ju_2= (TextView) view.findViewById(R.id.ju_2);
        one= (ImageView) view.findViewById(R.id.one);
        two= (ImageView) view.findViewById(R.id.two);
        three= (ImageView) view.findViewById(R.id.three);
        four= (ImageView) view.findViewById(R.id.four);

        item1= (RelativeLayout) view.findViewById(R.id.item1);
        item2= (RelativeLayout) view.findViewById(R.id.item2);
        item3= (RelativeLayout) view.findViewById(R.id.item3);
        item4= (RelativeLayout) view.findViewById(R.id.item4);
//        btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
        changeview(select);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select==1){
                    dismiss();
                }else {
                    select=1;
                    changeview(select);
                    itemsOnClick.ItemClick("1");
                    dismiss();
                }
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select==2){
                    dismiss();
                }else {
                    select=2;
                    changeview(select);
                    itemsOnClick.ItemClick("2");
                    dismiss();
                }
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select==3){
                    dismiss();
                }else {
                    select=3;
                    changeview(select);
                    itemsOnClick.ItemClick("1");
                    dismiss();
                }
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select==4){
                    dismiss();
                }else {
                    select=4;
                    changeview(select);
                    itemsOnClick.ItemClick("2");
                    dismiss();
                }
            }
        });


        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.select_anim);
    }




    public interface ItemInclick{
        void ItemClick(String item);
    }



        private void changeview(int index){
            text1.setTextColor(Color.parseColor("#5a5963"));//ff1e1e
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
            four.setVisibility(View.GONE);
            di.setTextColor(Color.parseColor("#5a5963"));
            di_2.setTextColor(Color.parseColor("#5a5963"));

            gao.setTextColor(Color.parseColor("#5a5963"));
            gao_2.setTextColor(Color.parseColor("#5a5963"));

            ju.setTextColor(Color.parseColor("#5a5963"));
            ju_2.setTextColor(Color.parseColor("#5a5963"));
            if(index==1){
                text1.setTextColor(Color.parseColor("#ff1e1e"));//
                one.setVisibility(View.VISIBLE);
            }else if(index==2){
                di.setTextColor(Color.parseColor("#ff1e1e"));
                di_2.setTextColor(Color.parseColor("#ff1e1e"));
                two.setVisibility(View.VISIBLE);
            }else if(index==3){
                gao.setTextColor(Color.parseColor("#ff1e1e"));
                gao_2.setTextColor(Color.parseColor("#ff1e1e"));
                three.setVisibility(View.VISIBLE);
            }else {
                ju.setTextColor(Color.parseColor("#ff1e1e"));
                ju_2.setTextColor(Color.parseColor("#ff1e1e"));
                four.setVisibility(View.VISIBLE);
            }
        }



}
