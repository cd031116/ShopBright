package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.interfaces.ItemCallback;
import com.zhl.huiqu.main.MainProductListFragment;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.main.ProductPartItemView;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.main.team.bean.GroupListBase;
import com.zhl.huiqu.main.team.bean.TeamListInfo;
import com.zhl.huiqu.main.team.itemview.TeamPartItemView;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.utils.Utils;

import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.support.paging.IPaging;
import org.aisen.android.support.paging.PageIndexPaging;
import org.aisen.android.ui.fragment.APagingFragment;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class TeamListFragment extends ARecycleViewSwipeRefreshFragment<TeamListInfo, GroupListBase, Serializable> implements ItemCallback {

    public static TeamListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        TeamListFragment fragment = new TeamListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewInject(id = R.id.error_text)
    TextView error_text;
    @ViewInject(id = R.id.error_image)
    ImageView error_image;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;
    private  String type;
    @Override
    public int inflateContentView(){
        return R.layout.ui_recycle;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        super.onItemClick(parent, view, position, id);
        String a = getAdapterItems().get(position).getSpot_team_id();
        if (position < getAdapterItems().size()) {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("shop_spot_id",a+"");
            startActivity(intent);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
    }

    @Override
    public IItemViewCreator<TeamListInfo> configItemViewCreator() {
        return new IItemViewCreator<TeamListInfo>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i){
                return layoutInflater.inflate(TeamPartItemView.LAYOUT_RES, viewGroup, false);
            }

            @Override
            public IITemView<TeamListInfo> newItemView(View view, int i) {
                return new TeamPartItemView(getActivity(), view,TeamListFragment.this);
            }
        };
    }

    @Override
    protected IItemViewCreator<TeamListInfo> configFooterViewCreator() {
        return Utils.configFooterViewCreator(getActivity(), this);
    }

    @Override
    protected IPaging<TeamListInfo, GroupListBase> newPaging() {
        return new PageIndexPaging<>();
    }

//
//    @Override
//    protected RecyclerView.LayoutManager configLayoutManager() {
//        return linearLayoutManager;
//    }

    @Override
    protected void setupRefreshConfig(RefreshConfig config) {
        super.setupRefreshConfig(config);
        config.footerMoreEnable = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        requestData(RefreshMode.reset);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate){
        super.layoutInit(inflater, savedInstanceSate);
//        recycleview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), null, 1));
//        scrollView.setFillViewport(true);
    }

    @Override
    public void requestData(RefreshMode refreshMode) {
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }

    @Override
    public void onClickItem(int position) {


    }

    @Override
    public void onClickItemBean(ProductPartBean bean,int position) {
        if(bean.isup()){
            bean.setIsup(false);
        }else {
            bean.setIsup(true);
        }
        recycleview.getAdapter().notifyItemChanged(position);
    }

    class Task extends APagingTask<Void, Void, GroupListBase> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TeamListInfo> parseResult(GroupListBase productPartListBean) {
            return productPartListBean.getBody().getData();
        }

        @Override
        protected GroupListBase workInBackground(RefreshMode refreshMode, String s, String nextPage, Void... voids) throws TaskException {
            int start = 1;
            if (mode == RefreshMode.update && !TextUtils.isEmpty(nextPage)) {
                try {
                    start = Integer.parseInt(nextPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            GroupListBase beans = queryList(start);

            if (beans != null && beans.getBody() != null){
                beans.setEndPaging(beans.getBody().getData().size() <= 5);
            }
            return beans;
        }

        @Override
        protected void onFailure(TaskException exception){
            super.onFailure(exception);
            error_text.setText(exception.getMessage());
//            if ("noneNetwork".equals(exception.getCode())) {
//                error_image.setImageResource(R.mipmap.no_net);
//            }
        }
    }

    protected GroupListBase queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getPackList(type,start+"","");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
