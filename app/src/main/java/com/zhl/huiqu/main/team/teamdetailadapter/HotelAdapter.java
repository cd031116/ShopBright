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
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2017/9/21.
 */

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.HotelBean> dataList = new ArrayList<>();

    private CommonAdapter<TeamDetailBean.JourneyInfoBean.HotelBean.HotelImgBean> hotelImgAdapter;
    private CommonAdapter<TeamDetailBean.JourneyInfoBean.HotelBean.AlternativeHotelBean> alternativeHotelAdapter;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public HotelAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.HotelBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.HotelBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView journey_hotel_img;
        RecyclerView journey_alternative_hotel;
        TextView hotel_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            journey_hotel_img = (RecyclerView) itemView.findViewById(R.id.journey_hotel_img);
            journey_alternative_hotel = (RecyclerView) itemView.findViewById(R.id.journey_alternative_hotel);
            hotel_title = (TextView) itemView.findViewById(R.id.hotel_title);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_hotel_item_layout, parent, false);
        HotelAdapter.MyViewHolder vhNormal = new HotelAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            hotelImgAdapter = new CommonAdapter<TeamDetailBean.JourneyInfoBean.HotelBean.HotelImgBean>(mContext, R.layout.journey_hotel_img_item, dataList.get(position).getHotelImg()) {

                @Override
                protected void convert(ViewHolder holder, TeamDetailBean.JourneyInfoBean.HotelBean.HotelImgBean journeyScenicImgBean, int pos) {
                    holder.setRunderWithUrl(R.id.journey_hotel_img_item, journeyScenicImgBean.getImg());
                    holder.setText(R.id.img_title, journeyScenicImgBean.getImgName());
                }
            };
            if (dataList.get(position).getHotelImg() != null && dataList.get(position).getHotelImg().size() >= 3)
                ((MyViewHolder) holder).journey_hotel_img.setLayoutManager(new MyGridLayoutManager(mContext, 3));
            else
                ((MyViewHolder) holder).journey_hotel_img.setLayoutManager(new MyGridLayoutManager(mContext, 2));
            ((MyViewHolder) holder).journey_hotel_img.addItemDecoration(new SimpleDividerItemDecoration(mContext, null, 1));
            ((MyViewHolder) holder).journey_hotel_img.setAdapter(hotelImgAdapter);

            alternativeHotelAdapter = new CommonAdapter<TeamDetailBean.JourneyInfoBean.HotelBean.AlternativeHotelBean>(mContext, R.layout.journey_alternative_hotel_item, dataList.get(position).getAlternativeHotel()) {

                @Override
                protected void convert(ViewHolder holder, TeamDetailBean.JourneyInfoBean.HotelBean.AlternativeHotelBean alternativeHotelBean, int pos) {
                    if (!TextUtils.isEmpty(alternativeHotelBean.getTitle())) {
                        holder.setVisible(R.id.hotel_title, true);
                        holder.setText(R.id.hotel_title, alternativeHotelBean.getTitle());
                    } else {
                        holder.setVisible(R.id.hotel_title, false);
                    }
                    if (!TextUtils.isEmpty(alternativeHotelBean.getStarName())) {
                        holder.setVisible(R.id.hotel_star, true);
                        holder.setText(R.id.hotel_star, alternativeHotelBean.getStarName());
                    } else {
                        holder.setVisible(R.id.hotel_star, false);
                    }
                }
            };
            ((MyViewHolder) holder).journey_alternative_hotel.setLayoutManager(new MyLinearLayoutManager(mContext));
            ((MyViewHolder) holder).journey_alternative_hotel.setAdapter(alternativeHotelAdapter);

            if (!TextUtils.isEmpty(dataList.get(position).getDescription()))
                ((MyViewHolder) holder).hotel_title.setText("住宿：" + dataList.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
