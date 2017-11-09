package com.zhl.huiqu.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.R;
import com.zhl.huiqu.personal.bean.CollectEntity;
import com.zhl.huiqu.personal.bean.CollectTick;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CollectRecyclerViewAdapter extends RecyclerView.Adapter<CollectRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<CollectTick> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public CollectRecyclerViewAdapter(Context context, List<CollectTick> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(CollectTick collectEntity, int position) {
        dataList.add(position, collectEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

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

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    @Override
    public CollectRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_tourist_point, parent, false);
        SupportMultipleScreensUtil.scale(view);
        CollectRecyclerViewAdapter.MyViewHolder vh = new CollectRecyclerViewAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CollectRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Log.d("dawn", "onBindViewHolder: " + dataList.get(position).getTitle());
        holder.collectWhere.setText(dataList.get(position).getTitle());
        holder.collectMs.setText(dataList.get(position).getDesc());
        holder.collectAddress.setText(dataList.get(position).getCity());
        holder.collectJb.setText(dataList.get(position).getGrade());
//        holder.collectPf.setText(dataList.get(position).getCollectPf());
        holder.collectPrice.setText("￥" + dataList.get(position).getShop_price());
        holder.collectTag.setText(dataList.get(position).getTheme());
//        if (!TextUtils.isEmpty(dataList.get(position).getImgUrl())) {
//            Glide.with(holder.mHead.getContext()).load(dataList.get(position).getImgUrl()).apply(RequestOptions.centerCropTransform())
//                    .apply(RequestOptions.bitmapTransform(new GlideCircleTransform(holder.mHead.getContext()))).into(holder.mHead);
//        } else {
        Glide.with(holder.mHead.getContext()).load(dataList.get(position).getThumb()).into(holder.mHead);
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

    @Override
    public boolean onLongClick(View view) {
        Log.e("ttt", "onLongClick: "+ (int) view.getTag());
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取position
            Log.e("ttt", "onLongClick: "+ (int) view.getTag());
            mOnItemLongClickListener.onItemLongClick(view, (int) view.getTag());
            return true;
        }
        return false;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
