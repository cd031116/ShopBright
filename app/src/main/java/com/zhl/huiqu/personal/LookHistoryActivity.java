package com.zhl.huiqu.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.BuildConfig;
import com.zhl.huiqu.R;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.bean.SearchBean;
import com.zhl.huiqu.main.bean.SearchEntity;
import com.zhl.huiqu.main.bean.SearchInfo;
import com.zhl.huiqu.main.search.SearchListFragment;
import com.zhl.huiqu.main.ticket.TickInfo;
import com.zhl.huiqu.personal.api.BaseApi;
import com.zhl.huiqu.personal.api.BaseModel;
import com.zhl.huiqu.personal.bean.CollectEntity;
import com.zhl.huiqu.pull.BaseSectionListActivity;
import com.zhl.huiqu.pull.BaseViewHolder;
import com.zhl.huiqu.pull.PullRecycler;
import com.zhl.huiqu.pull.layoutmanager.ILayoutManager;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.pull.section.SectionData;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.widget.TagCloudView;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 浏览记录
 * Created by dw on 2017/8/11.
 */

public class LookHistoryActivity extends BaseSectionListActivity<TickInfo> {
    private ImageView backBtn;
    private ImageView image;
    private TextView titleText;
    private String deviceId;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        backBtn = (ImageView) findViewById(R.id.top_image);
        image = (ImageView) findViewById(R.id.image);
        titleText = (TextView) findViewById(R.id.top_title);
        titleText.setText(getResources().getString(R.string.personal_look_history));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourist_point, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(getApplicationContext());
        return layoutManager;
    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApi api = retrofit.create(BaseApi.class);
        Call<BaseModel> call = api.getbrowserhistory(deviceId, page++);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                    mDataList.clear();
                }
                if (response.body().getBody() == null || response.body().getBody().size() == 0) {
                    recycler.enableLoadMore(false);
                } else {
                    recycler.enableLoadMore(true);
                    Map<Integer, Boolean> stateMap = new HashMap<Integer, Boolean>();
                    stateMap.put(0, false);
                    for (int i = 0; i < response.body().getBody().size(); i++) {
                        String addtime = response.body().getBody().get(i).getAdd_time();
                        Date obtaindata = obtaindata(addtime);
                        int month = obtaindata.getMonth() + 1;
                        int date = obtaindata.getDate();
                        if (!stateMap.get(i)) {
                            mDataList.add(new SectionData(true, i, timedate(addtime)));
                            mDataList.add(new SectionData(response.body().getBody().get(i)));
                            Log.e("ttt", "onResponse1: " + response.body().getBody().get(i).getTitle());
                            for (int j = i + 1; j < response.body().getBody().size(); j++) {
                                String addtimes = response.body().getBody().get(j).getAdd_time();
                                Date obtaindatas = obtaindata(addtimes);
                                int months = obtaindatas.getMonth() + 1;
                                int dates = obtaindatas.getDate();
                                if (month == months) {
                                    if (date == dates) {
                                        mDataList.add(new SectionData(response.body().getBody().get(j)));
                                        Log.e("ttt", "onResponse2: " + j + "--" + response.body().getBody().get(j).getTitle());
                                        stateMap.put(j, true);
                                    } else {
                                        stateMap.put(j, false);
                                        break;
                                    }
                                } else {
                                    stateMap.put(j, false);
                                    break;
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                recycler.onRefreshCompleted();
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {

            }
        });
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mHead;
        TextView touristPrice;
        TextView touristMs;
        TextView touristPlace;
        TextView touristPlaceScore;
        TextView touristPlaceJibie;
        TextView touristTag;
        TextView touristArea;


        public SampleViewHolder(View itemView) {
            super(itemView);
            touristArea = (TextView) itemView.findViewById(R.id.tourist_area);
            touristMs = (TextView) itemView.findViewById(R.id.tourist_ms);
            touristPlace = (TextView) itemView.findViewById(R.id.tourist_place);
            touristPlaceScore = (TextView) itemView.findViewById(R.id.tourist_place_score);
            touristPlaceJibie = (TextView) itemView.findViewById(R.id.tourist_place_jibie);
            touristTag = (TextView) itemView.findViewById(R.id.tourist_tag);
            touristPrice = (TextView) itemView.findViewById(R.id.tourist_price);
            mHead = (ImageView) itemView.findViewById(R.id.tourist_view);
        }

        @Override
        public void onBindViewHolder(int position) {
            touristArea.setText(mDataList.get(position).t.getTitle());
            touristMs.setText(mDataList.get(position).t.getDesc());
            touristPlace.setText(mDataList.get(position).t.getCity());
            touristPlaceScore.setText(mDataList.get(position).t.getCsr());
            touristPlaceJibie.setText(mDataList.get(position).t.getGrade());
            touristTag.setText(mDataList.get(position).t.getTheme());
            touristPrice.setText(mDataList.get(position).t.getShop_price());
            Glide.with(mHead.getContext()).load(mDataList.get(position).t.getThumb()).into(mHead);
//            Glide.with(LookHistoryActivity.this.getApplicationContext()).load(mDataList.get(position).t.getImgUrl()).into(mHead);
        }

        @Override
        public void onItemClick(View view, final int position) {
            Intent intent = new Intent(LookHistoryActivity.this, ProductDetailActivity.class);
            intent.putExtra("shop_spot_id", mDataList.get(position).t.getShop_spot_id() + "");
            startActivity(intent);
        }

    }

    public String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd ");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public Date obtaindata(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        try {
            Date date = format.parse(timedate(time));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}



