package com.zhl.huiqu.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CollectRecyclerViewAdapter extends RecyclerView.Adapter<CollectRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<CollectEntity> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CollectRecyclerViewAdapter(Context context, List<CollectEntity> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(CollectEntity collectEntity, int position) {
        dataList.add(position, collectEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView collectWhere;
        TextView collectMs;
        TextView collectAddress;
        TextView collectJb;
        TextView collectPf;
        TextView collectPrice;
        TextView collectTag;
        ImageView mHead;

        public MyViewHolder(View itemView) {
            super(itemView);
            collectWhere = (TextView) itemView.findViewById(R.id.tourist_area);
            collectMs = (TextView) itemView.findViewById(R.id.tourist_ms);
            collectAddress = (TextView) itemView.findViewById(R.id.tourist_place);
            collectJb = (TextView) itemView.findViewById(R.id.tourist_place_jibie);
            collectPf = (TextView) itemView.findViewById(R.id.tourist_place_score);
            collectPrice = (TextView) itemView.findViewById(R.id.tourist_price);
            collectTag = (TextView) itemView.findViewById(R.id.tourist_tag);
            mHead = (ImageView) itemView.findViewById(R.id.tourist_view);
        }
    }

    @Override
    public CollectRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_tourist_point, parent, false);
        CollectRecyclerViewAdapter.MyViewHolder vh = new CollectRecyclerViewAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CollectRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Log.d("dawn", "onBindViewHolder: " + dataList.get(position).getCollectAddress());
        holder.collectWhere.setText(dataList.get(position).getCollectWhere());
        holder.collectMs.setText(dataList.get(position).getCollectMs());
        holder.collectAddress.setText(dataList.get(position).getCollectAddress());
        holder.collectJb.setText(dataList.get(position).getCollectJb());
        holder.collectPf.setText(dataList.get(position).getCollectPf());
        holder.collectPrice.setText(dataList.get(position).getCollectPrice());
        holder.collectTag.setText(dataList.get(position).getCollectTag());
//        if (!TextUtils.isEmpty(dataList.get(position).getImgUrl())) {
//            Glide.with(holder.mHead.getContext()).load(dataList.get(position).getImgUrl()).apply(RequestOptions.centerCropTransform())
//                    .apply(RequestOptions.bitmapTransform(new GlideCircleTransform(holder.mHead.getContext()))).into(holder.mHead);
//        } else {
        Glide.with(holder.mHead.getContext()).load(dataList.get(position).getImgUrl()).into(holder.mHead);
//        }
        //将position保存在itemView的Tag中，以便点击时进行获取
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
