package com.zhl.huiqu.main.team.teamdetailadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class TrafficAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.SmallTrafficBean> dataList = new ArrayList<>();

    private CommonAdapter<TeamDetailBean.JourneyInfoBean.SmallTrafficBean.SmallTrafficImgBean> trafficImgBeanCommonAdapter;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TrafficAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.SmallTrafficBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.SmallTrafficBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView journey_traffic_list;
        TextView traffic_from;
//        TextView traffic_to;
        TextView traffic_content;
//        ImageView traffic_means;

        public MyViewHolder(View itemView) {
            super(itemView);
            journey_traffic_list = (RecyclerView) itemView.findViewById(R.id.journey_traffic_list);
            traffic_from = (TextView) itemView.findViewById(R.id.traffic_from);
//            traffic_to = (TextView) itemView.findViewById(R.id.traffic_to);
//            traffic_means = (ImageView) itemView.findViewById(R.id.traffic_means);
            traffic_content = (TextView) itemView.findViewById(R.id.traffic_content);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_traficc_item_layout, parent, false);
        TrafficAdapter.MyViewHolder vhNormal = new TrafficAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            trafficImgBeanCommonAdapter = new CommonAdapter<TeamDetailBean.JourneyInfoBean.SmallTrafficBean.SmallTrafficImgBean>(mContext, R.layout.journey_traficc_img_item, dataList.get(position).getTrafficImg()) {

                @Override
                protected void convert(ViewHolder holder, TeamDetailBean.JourneyInfoBean.SmallTrafficBean.SmallTrafficImgBean journeyScenicImgBean, int pos) {
                    holder.setRunderWithUrl(R.id.journey_traffic_img_item, journeyScenicImgBean.getImg());
                    holder.setText(R.id.img_title, journeyScenicImgBean.getImgName());
                }
            };
            if (dataList.get(position).getTrafficImg() != null && (dataList.get(position).getTrafficImg().size() >= 3&&dataList.get(position).getTrafficImg().size()!=4))
                ((MyViewHolder) holder).journey_traffic_list.setLayoutManager(new MyGridLayoutManager(mContext, 3));
            else
                ((MyViewHolder) holder).journey_traffic_list.setLayoutManager(new MyGridLayoutManager(mContext, 2));
            ((MyViewHolder) holder).journey_traffic_list.addItemDecoration(new SimpleDividerItemDecoration(mContext, null, 1));
            ((MyViewHolder) holder).journey_traffic_list.setAdapter(trafficImgBeanCommonAdapter);

            if (!TextUtils.isEmpty(dataList.get(position).getFrom())) {
                ((MyViewHolder) holder).traffic_from.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).traffic_from.setText("交通：" + dataList.get(position).getFrom());
            } else
                ((MyViewHolder) holder).traffic_from.setVisibility(View.GONE);

//            if (!TextUtils.isEmpty(dataList.get(position).getMeans())) {
//                if ("汽车".equals(dataList.get(position).getMeans()))
//                    ((MyViewHolder) holder).traffic_means.setBackgroundResource(R.drawable.dabache);
//                else if ("火车".equals(dataList.get(position).getMeans()))
//                    ((MyViewHolder) holder).traffic_means.setBackgroundResource(R.drawable.huoche);
//                else if ("飞机".equals(dataList.get(position).getMeans()))
//                    ((MyViewHolder) holder).traffic_means.setBackgroundResource(R.drawable.feiji);
//                else if ("轮船".equals(dataList.get(position).getMeans()))
//                    ((MyViewHolder) holder).traffic_means.setBackgroundResource(R.drawable.lunc);
//            } else
//                ((MyViewHolder) holder).traffic_means.setBackgroundResource(R.drawable.dabache);
//
//            if (!TextUtils.isEmpty(dataList.get(position).getFrom())) {
//                ((MyViewHolder) holder).traffic_to.setVisibility(View.VISIBLE);
//                ((MyViewHolder) holder).traffic_to.setText(dataList.get(position).getTo());
//            } else
//                ((MyViewHolder) holder).traffic_to.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(dataList.get(position).getDescription())) {
                ((MyViewHolder) holder).traffic_content.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).traffic_content.setText(dataList.get(position).getDescription());
            } else
                ((MyViewHolder) holder).traffic_content.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
