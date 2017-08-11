package com.zhl.huiqu.main;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhl.huiqu.R;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;


/**
 * Created by wangdan on 17/2/12.
 */

public class ProductPartItemView extends ARecycleViewItemView<ProductPartBean> {

    public static final int LAYOUT_RES = R.layout.item_product_part;

    @ViewInject(id = R.id.main_top)
    LinearLayout main_top;

//
    public ProductPartItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);

        int width = SystemUtils.getScreenWidth(getContext());
//        imgCover.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Math.round(360 * 1.0f / 750 * width)));
    }

    @Override
    public void onBindData(View view, ProductPartBean bean, int i) {
//        txtName.setText(bean.getName());

//        Glide.with(getContext())
//                .load(bean.getCoverImageUrl())
//                .asBitmap()
//                .centerCrop()
//                .dontAnimate()
//                .placeholder(GlideLoadingBGUtils.bg_product_list)
//                .error(GlideLoadingBGUtils.bg_product_list)
//                .into(imgCover);

        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        }
        else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
