package com.zhl.huiqu.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.pull.BaseSectionListActivity;
import com.zhl.huiqu.pull.BaseViewHolder;
import com.zhl.huiqu.pull.PullRecycler;
import com.zhl.huiqu.pull.layoutmanager.ILayoutManager;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.pull.section.SectionData;

import java.util.ArrayList;

/**
 * 浏览记录
 * Created by dw on 2017/8/11.
 */

public class LookHistoryActivity extends BaseSectionListActivity<CollectEntity> {
    private ImageView backBtn;
    private TextView titleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backBtn = (ImageView) findViewById(R.id.top_image);
        titleText = (TextView) findViewById(R.id.top_title);
        titleText.setText(getResources().getString(R.string.personal_look_history));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        recycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                    mDataList.clear();
                }
                int size = mDataList.size();
                mDataList.add(new SectionData(true, size, "header " + size));
                for (int i = size; i < size + 10; i++) {
                    CollectEntity collectEntity = new CollectEntity();
                    collectEntity.setCollectAddress("cs");
                    collectEntity.setCollectJb("4a");
                    collectEntity.setCollectMs("最美石燕湖");
                    collectEntity.setCollectPf("4.4fen");
                    collectEntity.setCollectPrice("$50");
                    collectEntity.setCollectTag("风景名胜");
                    collectEntity.setCollectWhere("石燕湖");
                    collectEntity.setImgUrl("http://wx.qlogo.cn/mmhead/MhptZYyBT66XRj8aYSynIHRaGGIo7LCZvcUk7QBGXrU/0");
                    mDataList.add(new SectionData(collectEntity));
                }
                adapter.notifyDataSetChanged();
                recycler.onRefreshCompleted();
                if (mDataList.size() < 100) {
                    recycler.enableLoadMore(true);
                } else {
                    recycler.enableLoadMore(false);
                }
            }
        }, 3000);
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mHead;
        TextView mSampleListDelete;
        TextView mSampleListItemLabel;


        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.tourist_area);
            mSampleListDelete = (TextView) itemView.findViewById(R.id.order_delete_text);
            mHead = (ImageView) itemView.findViewById(R.id.tourist_view);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setText(mDataList.get(position).t.getCollectWhere());

            Glide.with(mHead.getContext()).load(mDataList.get(position).t.getImgUrl()).into(mHead);
//            Glide.with(LookHistoryActivity.this.getApplicationContext()).load(mDataList.get(position).t.getImgUrl()).into(mHead);
        }

        @Override
        public void onItemClick(View view, final int position) {

        }

    }
}



