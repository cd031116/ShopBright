package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by Administrator on 2017/8/15.
 */

public class TickItemView extends ARecycleViewItemView<TickInfo> {
    public static final int LAYOUT_RES = R.layout.item_product_part;



    //------------------------------------------
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
    @ViewInject(id = R.id.manyidu)
    TextView manyidu;
    @ViewInject(id = R.id.neirong)
    TextView neirong;
    @ViewInject(id = R.id.nei_line)
    LinearLayout nei_line;

    @ViewInject(id = R.id.arrow)
    ImageView arrow;
    @ViewInject(id = R.id.u_click)
    RelativeLayout u_click;

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
    public void onBindData(View view, TickInfo bean, int i) {

        title.setText(bean.getTitle());
        desc.setText(bean.getDesc());
        price.setText("￥" +bean.getShop_price());
        manyidu.setText(bean.getCsr());


//        String textStr = "<strong><font color='#e11818'>" + "￥"+bean.getShop_price() + "</font></strong>起";
//        String charSequence="";
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            charSequence = Html.fromHtml(textStr, Html.FROM_HTML_MODE_LEGACY).toString();
//        } else {
//            charSequence = Html.fromHtml(textStr).toString();
//        }
//        tourist_price.setText(charSequence);

        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(activity,8));
        Glide.with(activity)
                .load(bean.getThumb())
                .apply(myOptions)
                .into(photo);

        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
