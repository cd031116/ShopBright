package com.zhl.huiqu.main.team.teamdetailadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class JourneyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean> dataList = new ArrayList<>();

    private TrafficAdapter trafficAdapter;
    private ScenicAdapter scenicAdapter;
    private ActivityAdapter activityAdapter;
    private HotelAdapter hotelAdapter;
    private ShoppingAdapter shoppingAdapter;
    private ReminderAdapter reminderAdapter;
    private FoodAdapter foodAdapter;
    private OnDrawListener mOnDrawListener;

    public interface OnDrawListener {
        void onDrawFinish(int position);
    }

    public JourneyAdapter(Context context, List<TeamDetailBean.JourneyInfoBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView journey_traffic_list;
        RecyclerView journey_scenic_list;
        RecyclerView journey_activity_list;
        RecyclerView journey_food_list;
        RecyclerView journey_hotel_list;
        RecyclerView journey_shopping_list;
        RecyclerView journey_reminder_list;
        TextView j_day;

        public MyViewHolder(View itemView) {
            super(itemView);
            journey_traffic_list = (RecyclerView) itemView.findViewById(R.id.journey_traffic_list);
            journey_scenic_list = (RecyclerView) itemView.findViewById(R.id.journey_scenic_list);
            journey_activity_list = (RecyclerView) itemView.findViewById(R.id.journey_activity_list);
            journey_food_list = (RecyclerView) itemView.findViewById(R.id.journey_food_list);
            journey_hotel_list = (RecyclerView) itemView.findViewById(R.id.journey_hotel_list);
            journey_shopping_list = (RecyclerView) itemView.findViewById(R.id.journey_shopping_list);
            journey_reminder_list = (RecyclerView) itemView.findViewById(R.id.journey_reminder_list);
            j_day = (TextView) itemView.findViewById(R.id.j_day);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_day_item, parent, false);
        JourneyAdapter.MyViewHolder vhNormal = new JourneyAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((MyViewHolder) holder).j_day.setText("第" + (position + 1) + "天");

        trafficAdapter = new TrafficAdapter(mContext, dataList.get(position).getTraffic());
        ((MyViewHolder) holder).journey_traffic_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_traffic_list.setAdapter(trafficAdapter);

        scenicAdapter = new ScenicAdapter(mContext, dataList.get(position).getScenic());
        ((MyViewHolder) holder).journey_scenic_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_scenic_list.setAdapter(scenicAdapter);

        activityAdapter = new ActivityAdapter(mContext, dataList.get(position).getActivity());
        ((MyViewHolder) holder).journey_activity_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_activity_list.setAdapter(activityAdapter);

        reminderAdapter = new ReminderAdapter(mContext, dataList.get(position).getReminder());
        ((MyViewHolder) holder).journey_reminder_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_reminder_list.setAdapter(reminderAdapter);

        shoppingAdapter = new ShoppingAdapter(mContext, dataList.get(position).getShopping());
        ((MyViewHolder) holder).journey_shopping_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_shopping_list.setAdapter(shoppingAdapter);

        hotelAdapter = new HotelAdapter(mContext, dataList.get(position).getHotel());
        ((MyViewHolder) holder).journey_hotel_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_hotel_list.setAdapter(hotelAdapter);

        foodAdapter = new FoodAdapter(mContext, dataList.get(position).getFood());
        ((MyViewHolder) holder).journey_food_list.setLayoutManager(new MyLinearLayoutManager(mContext));
        ((MyViewHolder) holder).journey_food_list.setAdapter(foodAdapter);

        if (position == (dataList.size()-1)) {
            mOnDrawListener.onDrawFinish(position);
        }
    }

    public void setOnDrawFinishListener(OnDrawListener listener) {
        this.mOnDrawListener = listener;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
