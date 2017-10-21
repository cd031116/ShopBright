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
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2017/9/21.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TeamDetailBean.JourneyInfoBean.ShoppingBean> dataList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ShoppingAdapter(Context context, List<TeamDetailBean.JourneyInfoBean.ShoppingBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    public void add(TeamDetailBean.JourneyInfoBean.ShoppingBean dateEntity, int position) {
        dataList.add(position, dateEntity);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopping_title;
        TextView shopping_time;
        TextView shopping_product;
        TextView shopping_shuoming;

        public MyViewHolder(View itemView) {
            super(itemView);
            shopping_title = (TextView) itemView.findViewById(R.id.shopping_title);
            shopping_time = (TextView) itemView.findViewById(R.id.shopping_time);
            shopping_product = (TextView) itemView.findViewById(R.id.shopping_product);
            shopping_shuoming = (TextView) itemView.findViewById(R.id.shopping_shuoming);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.journey_shopping_item_layout, parent, false);
        ShoppingAdapter.MyViewHolder vhNormal = new ShoppingAdapter.MyViewHolder(view);
        SupportMultipleScreensUtil.scale(view);
        return vhNormal;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) != null) {
            if (!TextUtils.isEmpty(dataList.get(position).getShoppingTitle()))
                ((MyViewHolder) holder).shopping_title.setText(dataList.get(position).getShoppingTitle());

            if (dataList.get(position).getTimes() >= 60) {
                String time = "购物时长约为" + dataList.get(position).getTimes() / 60 + "小时" + dataList.get(position).getTimes() % 60 + "分钟";
                ((MyViewHolder) holder).shopping_time.setText(time);
            } else {
                String time = "购物时长约为" + dataList.get(position).getTimes() + "分钟";
                ((MyViewHolder) holder).shopping_time.setText(time);
            }
            if (!TextUtils.isEmpty(dataList.get(position).getShoppingProduct()))
                ((MyViewHolder) holder).shopping_product.setText("营业产品：" + dataList.get(position).getShoppingProduct());
            if (!TextUtils.isEmpty(dataList.get(position).getInstruction()))
                ((MyViewHolder) holder).shopping_shuoming.setText(dataList.get(position).getInstruction());
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
