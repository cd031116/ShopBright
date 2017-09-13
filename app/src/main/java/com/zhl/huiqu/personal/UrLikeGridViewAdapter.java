package com.zhl.huiqu.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.R;
import com.zhl.huiqu.personal.bean.UrLikeEntity;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import java.util.List;

/**
 * Created by lijuan on 2016/9/12.
 */
public class UrLikeGridViewAdapter extends BaseAdapter {
    private List<UrLikeEntity> mDatas;
    private LayoutInflater inflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    public UrLikeGridViewAdapter(Context context, List<UrLikeEntity> mDatas, int curIndex, int pageSize) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ur_like, parent, false);
            SupportMultipleScreensUtil.scale(convertView);
            viewHolder = new ViewHolder();
            viewHolder.tag = (TextView) convertView.findViewById(R.id.ur_like_tag);
            viewHolder.dpNum = (TextView) convertView.findViewById(R.id.ur_like_dp);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price_text);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address_text);
            viewHolder.touristMs = (TextView) convertView.findViewById(R.id.ms_tourist_text);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.ur_like_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        int pos = position + curIndex * pageSize;
        viewHolder.tag.setText(mDatas.get(pos).getTheme());
        viewHolder.dpNum.setText(mDatas.get(pos).getComment_num()+"条点评");
        viewHolder.price.setText(mDatas.get(pos).getShop_price());
        viewHolder.address.setText(mDatas.get(pos).getCity());
        viewHolder.touristMs.setText(mDatas.get(pos).getDesc());
        Glide.with(viewHolder.iv.getContext()).load(mDatas.get(position).getThumb()).into(viewHolder.iv);
        return convertView;
    }


    class ViewHolder {
        public TextView tag;
        public TextView dpNum;
        public TextView price;
        public TextView address;
        public TextView touristMs;
        public ImageView iv;
    }
}