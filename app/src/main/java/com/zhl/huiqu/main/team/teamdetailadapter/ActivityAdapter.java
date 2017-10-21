package com.zhl.huiqu.main.team.teamdetailadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2017/9/21.
 */

public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.ActivityBean> dataList = new ArrayList<>();

    private CommonAdapter<TeamDetailBean.JourneyInfoBean.ActivityBean.ActivityImgBean> activityImgAdapter;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ActivityAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.ActivityBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.ActivityBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView journey_activity_list;
        TextView activity_title;
        TextView activity_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            journey_activity_list = (RecyclerView) itemView.findViewById(R.id.journey_activity_list);
            activity_title = (TextView) itemView.findViewById(R.id.activity_title);
            activity_content = (TextView) itemView.findViewById(R.id.activity_content);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_activity_item_layout, parent, false);
        ActivityAdapter.MyViewHolder vhNormal = new ActivityAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            activityImgAdapter = new CommonAdapter<TeamDetailBean.JourneyInfoBean.ActivityBean.ActivityImgBean>(mContext, R.layout.journey_activity_img_item, dataList.get(position).getActivityImg()) {

                @Override
                protected void convert(ViewHolder holder, TeamDetailBean.JourneyInfoBean.ActivityBean.ActivityImgBean journeyScenicImgBean, int pos) {
                    holder.setRunderWithUrl(R.id.journey_activity_img_item, journeyScenicImgBean.getImg());
                    holder.setText(R.id.img_title, journeyScenicImgBean.getImgName());
                }
            };
            if (dataList.get(position).getActivityImg() != null && dataList.get(position).getActivityImg().size() >= 3)
                ((MyViewHolder) holder).journey_activity_list.setLayoutManager(new MyGridLayoutManager(mContext, 3));
            else
                ((MyViewHolder) holder).journey_activity_list.setLayoutManager(new MyGridLayoutManager(mContext, 2));
            ((MyViewHolder) holder).journey_activity_list.addItemDecoration(new SimpleDividerItemDecoration(mContext, null, 1));
            ((MyViewHolder) holder).journey_activity_list.setAdapter(activityImgAdapter);
            ((MyViewHolder) holder).activity_title.setText(dataList.get(position).getActivityTitle());
            ((MyViewHolder) holder).activity_content.setText(dataList.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
