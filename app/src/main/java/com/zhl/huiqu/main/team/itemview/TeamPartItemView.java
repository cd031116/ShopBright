package com.zhl.huiqu.main.team.itemview;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhl.huiqu.R;
import com.zhl.huiqu.interfaces.ItemTeamCallback;
import com.zhl.huiqu.main.team.bean.TeamListInfo;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.GlideRoundTransform;

import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.common.utils.Utils;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;

/**
 * Created by Administrator on 2017/9/28.
 */

public class TeamPartItemView extends ARecycleViewItemView<TeamListInfo> {
    public static final int LAYOUT_RES = R.layout.team_list_item;

    @ViewInject(id = R.id.photo)
    ImageView photo;
    @ViewInject(id = R.id.title)
    TextView title;
    @ViewInject(id = R.id.comment)
    TextView comment;
    @ViewInject(id = R.id.price)
    TextView price;
    @ViewInject(id = R.id.address)
    TextView address;
    @ViewInject(id = R.id.manyidu)
    TextView manyidu;
    @ViewInject(id = R.id.day_time)
    TextView day_time;
    @ViewInject(id = R.id.nei_line)
    LinearLayout nei_line;

    @ViewInject(id = R.id.arrow)
    ImageView arrow;
    @ViewInject(id = R.id.u_click)
    RelativeLayout u_click;

    private ItemTeamCallback callback;
    private Activity activity;
    private int posiiton=0;
    //
    public TeamPartItemView(Activity context, View itemView, ItemTeamCallback callback) {
        super(context, itemView);
        this.activity = context;
        this.callback = callback;
    }

    @OnClick({R.id.u_click})
    void onclick(View v) {
        callback.onClickItemBean((TeamListInfo)v.getTag(),posiiton);
    }

    @OnClick(R.id.neirong)
    void onclickd(View v) {
        callback.onClickItemBean((TeamListInfo)v.getTag(),posiiton);
    }


    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        SupportMultipleScreensUtil.scale(convertView);
        int width = SystemUtils.getScreenWidth(getContext());
//        imgCover.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Math.round(360 * 1.0f / 750 * width)));
    }

    @Override
    public void onBindData(View view, TeamListInfo bean, int i) {
        this.posiiton=i;
        u_click.setTag(bean);
//        neirong.setTag(bean);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(activity, 8));
        String ut=(String)photo.getTag(R.id.indexTag);
        if(TextUtils.isEmpty(ut)||!ut.equals(bean.getThumb())){
            Glide.with(activity)
                    .load(bean.getThumb())
                    .apply(myOptions)
                    .into(photo);
            photo.setTag(R.id.indexTag,bean.getThumb());
        }
        price.setText("￥"+bean.getPriceAdultMin());

        manyidu.setText(bean.getCsr());
        address.setText(bean.getDepartCitysName());
        day_time.setText(bean.getDuration()+"日游");
         String stitle=bean.getProductName();
        if(!TextUtils.isEmpty(stitle)&&stitle.contains("<")){
            title.setText(stitle.substring(stitle.indexOf("<")+1,stitle.indexOf(">")));
        }else {
            title.setText(stitle);
        }
        if (itemPosition() == 0) {
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setPadding(0, Utils.dip2px(getContext(), 6), 0, 0);
        }
    }
}
