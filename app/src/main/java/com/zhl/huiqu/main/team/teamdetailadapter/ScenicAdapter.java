package com.zhl.huiqu.main.team.teamdetailadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.team.TeamDetailActivity;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;
import com.zhl.huiqu.widget.calendar.RiliGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static com.zhl.huiqu.R.id.like_list;

/**
 * Created by dw on 2017/9/21.
 */

public class ScenicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.ScenicBean> dataList = new ArrayList<>();

    private CommonAdapter<TeamDetailBean.JourneyInfoBean.ScenicBean.ScenicImgBean> scenicImgAdapter;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ScenicAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.ScenicBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.ScenicBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView journey_scenic_list;
        TextView scenic_title;
        TextView scenic_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            journey_scenic_list = (RecyclerView) itemView.findViewById(R.id.journey_scenic_list);
            scenic_title = (TextView) itemView.findViewById(R.id.scenic_title);
            scenic_content = (TextView) itemView.findViewById(R.id.scenic_content);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_scenic_item_layout, parent, false);
        ScenicAdapter.MyViewHolder vhNormal = new ScenicAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            scenicImgAdapter = new CommonAdapter<TeamDetailBean.JourneyInfoBean.ScenicBean.ScenicImgBean>(mContext, R.layout.journey_scenic_img_item, dataList.get(position).getScenicImg()) {

                @Override
                protected void convert(ViewHolder holder, TeamDetailBean.JourneyInfoBean.ScenicBean.ScenicImgBean journeyScenicImgBean, int pos) {
                    holder.setRunderWithUrl(R.id.journey_scenic_img_item, journeyScenicImgBean.getImg());
                    holder.setText(R.id.img_title, journeyScenicImgBean.getImgTitle());
                }
            };
            if (dataList.get(position).getScenicImg() != null && dataList.get(position).getScenicImg().size() >= 3)
                ((MyViewHolder) holder).journey_scenic_list.setLayoutManager(new MyGridLayoutManager(mContext, 3));
            else
                ((MyViewHolder) holder).journey_scenic_list.setLayoutManager(new MyGridLayoutManager(mContext, 2));
            ((MyViewHolder) holder).journey_scenic_list.addItemDecoration(new SimpleDividerItemDecoration(mContext, null, 1));
            ((MyViewHolder) holder).journey_scenic_list.setAdapter(scenicImgAdapter);

            if (!TextUtils.isEmpty(dataList.get(position).getTitle())) {
                ((MyViewHolder) holder).scenic_title.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).scenic_title.setText("景点：" + dataList.get(position).getTitle());
            } else {
                ((MyViewHolder) holder).scenic_title.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(dataList.get(position).getContent())) {
                ((MyViewHolder) holder).scenic_content.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).scenic_content.setText(dataList.get(position).getContent());
            } else {
                ((MyViewHolder) holder).scenic_content.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
