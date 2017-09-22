package com.zhl.huiqu.main.hotelTour.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.StartActivity;
import com.zhl.huiqu.main.bean.HotelDetailChooseEntity;
import com.zhl.huiqu.main.hotelTour.HotelTourTcChooseActivity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HotelDetailChooseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<HotelDetailChooseEntity> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public HotelDetailChooseAdapter(Context context, List<HotelDetailChooseEntity> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(HotelDetailChooseEntity hotelDetailChooseEntity, int position) {
        dataList.add(position, hotelDetailChooseEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView chooseName;
        TextView chooseContent;
        TextView hotelPrice;
        TextView commitBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            chooseName = (TextView) itemView.findViewById(R.id.choose_name);
            chooseContent = (TextView) itemView.findViewById(R.id.choose_content);
            hotelPrice = (TextView) itemView.findViewById(R.id.hotel_detail_price);
            commitBtn = (TextView) itemView.findViewById(R.id.commit_btn);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.hoteldetail_item_choose, parent, false);
        HotelDetailChooseAdapter.MyViewHolder vhNormal = new HotelDetailChooseAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        view.setOnClickListener(this);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).chooseName.setText(dataList.get(position).getName());
        ((MyViewHolder) holder).hotelPrice.setText(dataList.get(position).getPrice());
        ((MyViewHolder) holder).chooseContent.setText(dataList.get(position).getContent());
        ((MyViewHolder) holder).commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotelTourTcChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailChoose", dataList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
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
