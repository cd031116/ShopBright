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
import com.zhl.huiqu.main.bean.HotelTourTcChooseEntity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HotelTourTcChooseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<HotelTourTcChooseEntity> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public HotelTourTcChooseAdapter(Context context, List<HotelTourTcChooseEntity> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(HotelTourTcChooseEntity hotelTourTcChooseEntity, int position) {
        dataList.add(position, hotelTourTcChooseEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView chooseTypeImg;
        TextView chooseTypetext;
        TextView chooseTypeMy;
        TextView chooseTypePl;
        TextView chooseTypeAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            chooseTypeImg = (ImageView) itemView.findViewById(R.id.choose_type_img);
            chooseTypetext = (TextView) itemView.findViewById(R.id.choose_type_text);
            chooseTypeMy = (TextView) itemView.findViewById(R.id.choose_type_my);
            chooseTypePl = (TextView) itemView.findViewById(R.id.choose_type_pl);
            chooseTypeAddress = (TextView) itemView.findViewById(R.id.choose_type_address);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_choose_layout, parent, false);
        HotelTourTcChooseAdapter.MyViewHolder vhNormal = new HotelTourTcChooseAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        view.setOnClickListener(this);
        return vhNormal;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).chooseTypeAddress.setText(dataList.get(position).getAddress());
        ((MyViewHolder) holder).chooseTypetext.setText(dataList.get(position).getChooseName());
        ((MyViewHolder) holder).chooseTypePl.setText(dataList.get(position).getPl_num());
        ((MyViewHolder) holder).chooseTypeMy.setText(dataList.get(position).getMy());
        if (!TextUtils.isEmpty(dataList.get(position).getImg()))
            Glide.with(((MyViewHolder) holder).chooseTypeImg.getContext()).load(dataList.get(position).getImg()).into(((MyViewHolder) holder).chooseTypeImg);
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
