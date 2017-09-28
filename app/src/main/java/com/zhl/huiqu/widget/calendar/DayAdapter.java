package com.zhl.huiqu.widget.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.calendar.bean.DateEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dw on 2017/9/21.
 */

public class DayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<DateEntity> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public DayAdapter(Context context, List<DateEntity> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    private int selectedPosition = -1;// 选中的位置

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        Log.e("ddd", "setSelectedPosition: "+selectedPosition );
        notifyDataSetChanged();
    }

    public void add(DateEntity dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recommendtTitle;
        TextView recommendPrice;
        View bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            recommendtTitle = (TextView) itemView.findViewById(R.id.data);
            recommendPrice = (TextView) itemView.findViewById(R.id.luna);
            bg = itemView.findViewById(R.id.bg);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_month_data, parent, false);
        DayAdapter.MyViewHolder vhNormal = new DayAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        view.setOnClickListener(this);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
        ((MyViewHolder) holder).recommendPrice.setText(dataList.get(position).luna);

        if (TextUtils.isEmpty(dataList.get(position).date)) {
            ((MyViewHolder) holder).recommendtTitle.setText("");
            ((MyViewHolder) holder).recommendPrice.setText("");
            ((MyViewHolder) holder).bg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
        } else {

            Calendar calendar = Calendar.getInstance();
            int month = calendar.getTime().getMonth();
            int year = calendar.getTime().getYear() + 1900;
            int day = calendar.getTime().getDate();
            if (obtaindata(dataList.get(position).million / 1000 + "").getYear() + 1900 == year) {
                if (obtaindata(dataList.get(position).million / 1000 + "").getMonth() == (month)) {
                    if (obtaindata(dataList.get(position).million / 1000 + "").getDate() >= day) {
                        ((MyViewHolder) holder).recommendPrice.setVisibility(View.VISIBLE);
                        ((MyViewHolder) holder).recommendPrice.setText(dataList.get(position).luna);
                        ((MyViewHolder) holder).recommendPrice.setTextColor(Color.RED);
                        ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                    } else {
                        ((MyViewHolder) holder).recommendPrice.setVisibility(View.GONE);
                        ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                        ((MyViewHolder) holder).recommendtTitle.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                    }
                } else if (obtaindata(dataList.get(position).million / 1000 + "").getMonth() > (month)) {
                    ((MyViewHolder) holder).recommendPrice.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).recommendPrice.setText(dataList.get(position).luna);
                    ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                    ((MyViewHolder) holder).recommendPrice.setTextColor(Color.RED);
                } else {
                    ((MyViewHolder) holder).recommendPrice.setVisibility(View.GONE);
                    ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                    ((MyViewHolder) holder).recommendtTitle.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                }
            } else if (obtaindata(dataList.get(position).million / 1000 + "").getYear() + 1900 > (year)) {
                ((MyViewHolder) holder).recommendPrice.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).recommendPrice.setText(dataList.get(position).luna);
                ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                ((MyViewHolder) holder).recommendPrice.setTextColor(Color.RED);
            } else {
                ((MyViewHolder) holder).recommendPrice.setVisibility(View.GONE);
                ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).day);
                ((MyViewHolder) holder).recommendtTitle.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            }
            holder.itemView.setTag(position);
        }
        Log.e("ddd", "onBindViewHolder: "+selectedPosition+"\t,position:" +position);
        if (selectedPosition == position) {
            if (!TextUtils.isEmpty(dataList.get(position).date)) {
                ((MyViewHolder) holder).bg.setBackgroundResource(R.drawable.select_bg);
                ((MyViewHolder) holder).recommendtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
                ((MyViewHolder) holder).recommendPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.getTime().getMonth();
        int year = calendar.getTime().getYear() + 1900;
        int day = calendar.getTime().getDate();

        int position = (int) v.getTag();
        if (mOnItemClickListener != null) {
            if (obtaindata(dataList.get(position).million / 1000 + "").getYear() + 1900 == (year)) {
                if (obtaindata(dataList.get(position).million / 1000 + "").getMonth() == (month)) {
                    if (obtaindata(dataList.get(position).million / 1000 + "").getDate() >= day) {
                        mOnItemClickListener.onItemClick(v, (int) v.getTag());
                    }
                } else if (obtaindata(dataList.get(position).million / 1000 + "").getMonth() > (month)) {
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            } else if (obtaindata(dataList.get(position).million / 1000 + "").getYear() + 1900 > (year)) {
                mOnItemClickListener.onItemClick(v, (int) v.getTag());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd ");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public Date obtaindata(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        try {
            Date date = format.parse(timedate(time));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
