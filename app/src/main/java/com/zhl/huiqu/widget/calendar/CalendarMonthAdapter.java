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
import com.zhl.huiqu.widget.calendar.bean.CalendarBean;
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

public class CalendarMonthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<CalendarBean> dataList = new ArrayList<>();

    private DayAdapter adapter;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CalendarMonthAdapter(Context context, List<CalendarBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(CalendarBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView list;
        TextView now_month;

        public MyViewHolder(View itemView) {
            super(itemView);
            list = (RecyclerView) itemView.findViewById(R.id.list);
            now_month = (TextView) itemView.findViewById(R.id.now_month);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.rili_item_layout, parent, false);
        CalendarMonthAdapter.MyViewHolder vhNormal = new CalendarMonthAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        adapter = new DayAdapter(mContext, dataList.get(position).getDateList());
        adapter.setOnItemClickListener(new DayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("ddd", "onItemClick: "+position );
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
                ((MyViewHolder) holder).list.setAdapter(adapter);
            }
        });

        ((MyViewHolder) holder).list.setLayoutManager(new RiliGridLayoutManager(mContext, 7));
        ((MyViewHolder) holder).list.setAdapter(adapter);
        ((MyViewHolder) holder).now_month.setText(dataList.get(position).getMonth());

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
