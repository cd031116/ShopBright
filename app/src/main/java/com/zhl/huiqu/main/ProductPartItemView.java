package com.zhl.huiqu.main;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.GlideRoundTransform;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;


/**
 * Created by lyj on 17/2/12.
 */

public class ProductPartItemView extends ARecycleViewItemView<ProductPartBean> {
    public static final int LAYOUT_RES = R.layout.item_product_part;

    @ViewInject(id = R.id.photo)
    ImageView photo;
    @ViewInject(id = R.id.title)
    TextView title;
    @ViewInject(id = R.id.comment)
    TextView comment;
    @ViewInject(id = R.id.price)
    TextView price;
    @ViewInject(id = R.id.desc)
    TextView desc;

    private Activity activity;
//
    public ProductPartItemView(Activity context, View itemView) {
        super(context, itemView);
        this.activity=context;
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        SupportMultipleScreensUtil.scale(convertView);
        int width = SystemUtils.getScreenWidth(getContext());
//        imgCover.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Math.round(360 * 1.0f / 750 * width)));
    }

    @Override
    public void onBindData(View view, ProductPartBean bean, int i) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(activity,8));
        Glide.with(activity)
                .load(bean.getThumb())
                .apply(myOptions)
                .into(photo);
        title.setText(bean.getTitle());
        if(bean.getComment_num()<=0){
            comment.setText("");
        }else {
            comment.setText(bean.getComment_num()+"条评论 "+bean.getCsr()+"满意");
        }
        price.setText("￥"+bean.getShop_price());
        desc.setText(bean.getDesc());
        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        }
        else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
