package com.zhl.huiqu.main.popupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.bean.DetailMainBean;
import com.zhl.huiqu.main.bean.GradeBean;
import com.zhl.huiqu.main.bean.GradeInfo;
import com.zhl.huiqu.main.ticket.SpotTBean;
import com.zhl.huiqu.main.ticket.SpotThemeInfo;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.TLog;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SelectTourWindow extends PopupWindow {
    private Activity mContext;
    private View view;
    private RecyclerView recycle;
    private RecyclerView gra_recy;
    private TextView zhuti, jibie;
    private ItemInclick itemsOnClick;
    private TextView cancel, summit;
    private CommonAdapter<SpotThemeInfo> mAdapter;
    private List<SpotThemeInfo> mData = new ArrayList<>();


    private CommonAdapter<GradeInfo> gAdapter;
    private List<GradeInfo> gData = new ArrayList<>();

    private String getId = "", garadeId = "";
    private int select = 1;

    public SelectTourWindow(Activity mContext, ItemInclick itemsOnClickd) {
        this.mContext = mContext;
        this.itemsOnClick = itemsOnClickd;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.select_window, null);
        recycle = (RecyclerView) view.findViewById(R.id.recycleview);
        gra_recy = (RecyclerView) view.findViewById(R.id.gra_recy);
        cancel = (TextView) view.findViewById(R.id.cancel);
        summit = (TextView) view.findViewById(R.id.sure);
        zhuti = (TextView) view.findViewById(R.id.zhuti);
        jibie = (TextView) view.findViewById(R.id.jibie);
        new getInfoTask().execute();
        new getGradeTask().execute();
        changeview(select);
        //
        zhuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {
                    return;
                } else {
                    select = 1;
                    changeview(select);
                }
            }
        });

        jibie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 2) {
                    return;
                } else {
                    select = 2;
                    changeview(select);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.ItemClick(String.valueOf(select), getId, garadeId);
                dismiss();
            }
        });
//        btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
//        btn_pick_photo = (Button) view.findViewById(R.id.btn_pick_photo);
        // 设置按钮监听
//        btn_pick_photo.setOnClickListener(itemsOnClick);
//        btn_take_photo.setOnClickListener(itemsOnClick);

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


    private void changeview(int index) {
        if (index == 2) {
            gra_recy.setVisibility(View.VISIBLE);
            recycle.setVisibility(View.GONE);
            jibie.setSelected(true);
            jibie.setTextColor(Color.parseColor("#ffffff"));
            zhuti.setSelected(false);
            zhuti.setTextColor(Color.parseColor("#5A5863"));
        } else {
            recycle.setVisibility(View.VISIBLE);
            gra_recy.setVisibility(View.GONE);
            zhuti.setSelected(true);
            zhuti.setTextColor(Color.parseColor("#ffffff"));
            jibie.setSelected(false);
            jibie.setTextColor(Color.parseColor("#5A5863"));
        }
    }


    class getInfoTask extends WorkTask<Void, Void, SpotTBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();

        }

        @Override
        public SpotTBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(mContext).getSpotTheme("theme");
        }

        @Override
        protected void onSuccess(SpotTBean infot) {
            super.onSuccess(infot);
            mData = infot.getData();
            setThmeview();
        }

        @Override
        protected void onFailure(TaskException exception) {

        }
    }

    class getGradeTask extends WorkTask<Void, Void, GradeBean> {
        @Override
        protected void onPrepare() {
            super.onPrepare();

        }

        @Override
        public GradeBean workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(mContext).getSpotTheme1("grade");
        }

        @Override
        protected void onSuccess(GradeBean infot) {
            super.onSuccess(infot);
            gData = infot.getBody();
            setGradeview();
        }

        @Override
        protected void onFailure(TaskException exception) {

        }
    }

    public interface ItemInclick {
        void ItemClick(String tab, String theme_id, String grade);
    }

    private void setThmeview() {
        mAdapter = new CommonAdapter<SpotThemeInfo>(mContext, R.layout.select_window_item, mData) {
            @Override
            protected void convert(ViewHolder holder, final SpotThemeInfo info, final int position) {
                holder.setText(R.id.content, info.getName());

                if (info.isselect()) {
                    holder.setSesect(R.id.content, true);

                    holder.setTextColor(R.id.content, Color.parseColor("#ffffff"));
                } else {
                    holder.setSesect(R.id.content, false);
                    holder.setTextColor(R.id.content, Color.parseColor("#5A5863"));
                }
                holder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < mData.size(); i++) {
                            mData.get(i).setIsselect(false);
                        }
                        getId = info.getShop_spot_attr_id();
                        info.setIsselect(true);
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        };
        recycle.setAdapter(mAdapter);
    }

    private void setGradeview() {
        gAdapter = new CommonAdapter<GradeInfo>(mContext, R.layout.select_window_item, gData) {
            @Override
            protected void convert(ViewHolder holder, final GradeInfo info, final int position) {
                holder.setText(R.id.content, info.getName());

                if (info.isselect()) {
                    holder.setSesect(R.id.content, true);

                    holder.setTextColor(R.id.content, Color.parseColor("#ffffff"));
                } else {
                    holder.setSesect(R.id.content, false);
                    holder.setTextColor(R.id.content, Color.parseColor("#5A5863"));
                }
                holder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < gData.size(); i++) {
                            gData.get(i).setIsselect(false);
                        }
                        garadeId = info.getTheme_id();
                        info.setIsselect(true);
                        gAdapter.notifyDataSetChanged();
                    }
                });

            }
        };
        gra_recy.setAdapter(gAdapter);

    }


}
