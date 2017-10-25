package com.zhl.huiqu.main.team.teamdetailadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2017/9/21.
 */

public class  ReminderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.ReminderBean> dataList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ReminderAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.ReminderBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.ReminderBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reminder_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            reminder_content = (TextView) itemView.findViewById(R.id.reminder_content);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_reminder_item_layout, parent, false);
        ReminderAdapter.MyViewHolder vhNormal = new ReminderAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            if (!TextUtils.isEmpty(dataList.get(position).getContent()))
                ((MyViewHolder) holder).reminder_content.setText(dataList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
