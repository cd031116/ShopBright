package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;

/**
 * Created by Administrator on 2017/8/15.
 */

public class TickItemView extends ARecycleViewItemView<ProductPartBean> {
    public static final int LAYOUT_RES = R.layout.item_tourist_point;

    @ViewInject(id = R.id.tourist_view)
    ImageView image;
    @ViewInject(id = R.id.tourist_area)
    TextView tname;
    private Activity activity;

    //
    public TickItemView(Activity context, View itemView) {
        super(context, itemView);
        this.activity = context;
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);

        SupportMultipleScreensUtil.init(activity);
        SupportMultipleScreensUtil.scale(convertView);

        int width = SystemUtils.getScreenWidth(getContext());
//        imgCover.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Math.round(360 * 1.0f / 750 * width)));
    }

    @Override
    public void onBindData(View view, ProductPartBean bean, int i) {

        try {
            tname.setText(bean.getId() + "");
        } catch (Exception e) {
            Log.i("tttt", "tname=" + e.toString());
        }


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
        } else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
