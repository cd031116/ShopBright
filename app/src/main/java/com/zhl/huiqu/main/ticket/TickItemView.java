package com.zhl.huiqu.main.ticket;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ProductPartBean;
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
    public static final int LAYOUT_RES = R.layout.item_tourist_point;

    @ViewInject(id = R.id.tourist_view)
    ImageView image;
    @ViewInject(id = R.id.tourist_area)
    TextView tname;

    @ViewInject(id = R.id.tourist_area)
    TextView tourist_area;
    @ViewInject(id = R.id.tourist_ms)
    TextView tourist_ms;

    @ViewInject(id = R.id.tourist_place)
    TextView tourist_place;
    @ViewInject(id = R.id.tourist_place_score)
    TextView tourist_place_score;
    @ViewInject(id = R.id.tourist_place_jibie)
    TextView tourist_place_jibie;

    @ViewInject(id = R.id.tourist_tag)
    TextView tourist_tag;
    @ViewInject(id = R.id.tourist_price)
    TextView tourist_price;

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

        tourist_area.setText(bean.getTitle());
        tourist_ms.setText(bean.getDesc());
        tourist_place.setText(bean.getCity());
        tourist_place_score.setText(bean.getCsr());
        tourist_place_jibie.setText(bean.getGrade());
        tourist_tag.setText("风景名胜");

        String textStr = "<strong><font color='#e11818'>" + "￥"+bean.getShop_price() + "</font></strong>起";
        String charSequence="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(textStr, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            charSequence = Html.fromHtml(textStr).toString();
        }
        tourist_price.setText(charSequence);

        try {
            tname.setText(bean.getTitle() + "");
        } catch (Exception e) {
            Log.i("tttt", "tname=" + e.toString());
        }

        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(activity,8));
        Glide.with(activity)
                .load(bean.getThumb())
                .apply(myOptions)
                .into(image);

        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
