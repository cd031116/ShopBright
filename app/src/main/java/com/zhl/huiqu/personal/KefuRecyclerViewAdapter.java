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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class KefuRecyclerViewAdapter extends RecyclerView.Adapter<KefuRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> dataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public KefuRecyclerViewAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(String collectEntity, int position) {
        dataList.add(position, collectEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView collectWhere;


        public MyViewHolder(View itemView) {
            super(itemView);
            collectWhere = (TextView) itemView.findViewById(R.id.kefu_phone_text);

        }
    }

    @Override
    public KefuRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_kefu_center_list, parent, false);
        KefuRecyclerViewAdapter.MyViewHolder vh = new KefuRecyclerViewAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final KefuRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Log.d("dawn", "onBindViewHolder: " + dataList.get(position).toString());
        holder.collectWhere.setText(dataList.get(position).toString());

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
