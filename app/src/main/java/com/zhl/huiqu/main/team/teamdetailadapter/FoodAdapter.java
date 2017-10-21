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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dw on 2017/9/21.
 */

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.FoodBean> dataList = new ArrayList<>();
    private Map<String, Boolean> foodMap = new HashMap<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FoodAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.FoodBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.FoodBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bre_text;
        TextView lun_text;
        TextView din_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            bre_text = (TextView) itemView.findViewById(R.id.bre_text);
            lun_text = (TextView) itemView.findViewById(R.id.lun_text);
            din_text = (TextView) itemView.findViewById(R.id.din_text);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_food_item_layout, parent, false);
        FoodAdapter.MyViewHolder vhNormal = new FoodAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {

            for (TeamDetailBean.JourneyInfoBean.FoodBean foodBean : dataList) {
                if (foodBean.getHasBre() == 1) {
                    foodMap.put("bre", true);
                } else {
                    foodMap.put("bre", false);
                }
                if (foodBean.getHasLun() == 1) {
                    foodMap.put("lun", true);
                } else {
                    foodMap.put("lun", false);
                }
                if (foodBean.getHasDin() == 1) {
                    foodMap.put("din", true);
                } else {
                    foodMap.put("din", false);
                }
            }

            for (String str : foodMap.keySet()) {
                if ("bre".equals(str)) {
                    boolean isHasBre = foodMap.get(str);
                    if (isHasBre)
                        ((MyViewHolder) holder).bre_text.setText("早餐：包含");
                    else
                        ((MyViewHolder) holder).bre_text.setText("早餐：敬请自理");
                }
                if ("lun".equals(str)) {
                    boolean isHasLun = foodMap.get(str);
                    if (isHasLun)
                        ((MyViewHolder) holder).lun_text.setText("午餐：包含");
                    else
                        ((MyViewHolder) holder).lun_text.setText("午餐：敬请自理");
                }
                if ("din".equals(str)) {
                    boolean isHasDin = foodMap.get(str);
                    if (isHasDin)
                        ((MyViewHolder) holder).din_text.setText("晚餐：包含");
                    else
                        ((MyViewHolder) holder).din_text.setText("晚餐：敬请自理");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
