package com.zhl.huiqu.main.hotelTour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.R;
import com.zhl.huiqu.main.bean.HotelTourEntity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HotelRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<HotelTourEntity> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public HotelRecommendAdapter(Context context, List<HotelTourEntity> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(HotelTourEntity hotelTourEntity, int position) {
        dataList.add(position, hotelTourEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView NormalInfoImg;
        TextView recommendtTitle;
        TextView recommendPrice;
        TextView recommendPl;
        TextView recommendMy;

        public MyViewHolder(View itemView) {
            super(itemView);
            NormalInfoImg = (ImageView) itemView.findViewById(R.id.recommend_img);
            recommendtTitle = (TextView) itemView.findViewById(R.id.recommend_title);
            recommendPrice = (TextView) itemView.findViewById(R.id.recommend_price);
            recommendPl = (TextView) itemView.findViewById(R.id.recommend_pl);
            recommendMy = (TextView) itemView.findViewById(R.id.recommend_my);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.hotel_tour_item_recommend, parent, false);
        HotelRecommendAdapter.MyViewHolder vhNormal = new HotelRecommendAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        view.setOnClickListener(this);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).recommendtTitle.setText(dataList.get(position).getTitle());
        ((MyViewHolder) holder).recommendPrice.setText(dataList.get(position).getPrice());
        ((MyViewHolder) holder).recommendPl.setText(dataList.get(position).getPl_num());
        ((MyViewHolder) holder).recommendMy.setText(dataList.get(position).getSatisfied());
        if (!TextUtils.isEmpty(dataList.get(position).getImage()))
            Glide.with(((MyViewHolder) holder).NormalInfoImg.getContext()).load(dataList.get(position).getImage()).into(((MyViewHolder) holder).NormalInfoImg);
//        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
