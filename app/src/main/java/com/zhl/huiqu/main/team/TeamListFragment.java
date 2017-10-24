package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.interfaces.ItemCallback;
import com.zhl.huiqu.interfaces.ItemTeamCallback;
import com.zhl.huiqu.main.MainProductListFragment;
import com.zhl.huiqu.main.ProductDetailActivity;
import com.zhl.huiqu.main.ProductPartBean;
import com.zhl.huiqu.main.ProductPartItemView;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.main.team.bean.GroupListBase;
import com.zhl.huiqu.main.team.bean.TeamListInfo;
import com.zhl.huiqu.main.team.itemview.TeamPartItemView;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.TeamEvent;
import com.zhl.huiqu.sdk.eventbus.TeamEventSubscriber;
import com.zhl.huiqu.sdk.eventbus.TickSearchEvent;
import com.zhl.huiqu.sdk.eventbus.TickSearchSubscriber;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.TLog;
import com.zhl.huiqu.utils.Utils;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.support.paging.IPaging;
import org.aisen.android.support.paging.PageIndexPaging;
import org.aisen.android.ui.fragment.APagingFragment;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.List;

/*
*
* @author lyj
* @describe  跟团游列表价筛选
* @data 2017/10/23
* */


public class TeamListFragment extends ARecycleViewSwipeRefreshFragment<TeamListInfo, GroupListBase, Serializable> implements ItemTeamCallback {
    private String themeId="";
    private String gradeId="";
    private String desCityId="";
    private String price="";
    private String sales="";

    public static TeamListFragment newInstance(String desCityId) {
        Bundle args = new Bundle();
        args.putString("desCityId", desCityId);
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

    @ViewInject(id = R.id.xiaoliang)
    LinearLayout xiaoliang;
    @ViewInject(id = R.id.jiage)
    LinearLayout jiage;
    @ViewInject(id = R.id.down_image)
    ImageView down_image;
    @ViewInject(id = R.id.up_image)
    ImageView up_image;

    @ViewInject(id = R.id.xiao_up)
    ImageView xiao_up;
    @ViewInject(id = R.id.xiao_down)
    ImageView xiao_down;
    private String type="team";//ticket

    @Override
    public int inflateContentView() {
        return R.layout.ui_team_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationCenter.defaultCenter().subscriber(TeamEvent.class, teamEvent);
    }



    TeamEventSubscriber teamEvent = new TeamEventSubscriber(){
        @Override
        public void onEvent(TeamEvent info){
            Log.i("dddd","TeamEventSubscriber");
            desCityId=info.getDesCityId();
            themeId=info.getThemeId();
            requestData(RefreshMode.reset);
        }
    };



    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        super.onItemClick(parent, view, position, id);
        String a = getAdapterItems().get(position).getProductId();
        if (position < getAdapterItems().size()) {
            Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
            intent.putExtra("spot_team_id", a + "");
            startActivity(intent);
        }
    }

    @OnClick({R.id.xiaoliang,R.id.down_image,R.id.up_image,R.id.jiage,R.id.xiao_up,R.id.xiao_down,R.id.t_shaixuan,R.id.i_shaixuan})
    void onlciu(View v) {
        switch (v.getId()) {
            case R.id.jiage:
            case R.id.down_image:
            case R.id.up_image:
                changeview(1);
                break;
            case R.id.xiaoliang:
            case R.id.xiao_up:
            case R.id.xiao_down:
                changeview(0);
                break;
            case R.id.t_shaixuan:
            case R.id.i_shaixuan:
                Intent intent=new Intent(getActivity(), SelecTeamActivity.class);
                intent.putExtra("type","team");
                startActivity(intent);
                break;
        }
    }

    private void changeview(int index) {
        if (index == 0) {
            down_image.setSelected(false);
            up_image.setSelected(false);
            if (xiao_down.isSelected()) {
                xiao_up.setSelected(true);
                xiao_down.setSelected(false);
                price="";
                sales = "1";

            } else {
                xiao_down.setSelected(true);
                xiao_up.setSelected(false);
                price="";
                sales = "2";

            }
        } else {
            xiao_up.setSelected(false);
            xiao_down.setSelected(false);
            if (up_image.isSelected()) {
                sales="";
                price = "1";
                up_image.setSelected(false);
                down_image.setSelected(true);
            } else {
                down_image.setSelected(false);
                up_image.setSelected(true);
                sales="";
                price = "2";
            }
        }
        requestData(RefreshMode.reset);
    }


    @Override
    public IItemViewCreator<TeamListInfo> configItemViewCreator() {
        return new IItemViewCreator<TeamListInfo>() {
            @Override
            public View newContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
                return layoutInflater.inflate(TeamPartItemView.LAYOUT_RES, viewGroup, false);
            }

            @Override
            public IITemView<TeamListInfo> newItemView(View view, int i) {
                return new TeamPartItemView(getActivity(), view, TeamListFragment.this);
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
    public void onResume() {
        super.onResume();
        requestData(RefreshMode.reset);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        desCityId=getArguments().getString("desCityId");
//        recycleview.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), null, 1));
//        scrollView.setFillViewport(true);
    }

    @Override
    public void requestData(RefreshMode refreshMode) {
        new Task(refreshMode != RefreshMode.update ? RefreshMode.reset : RefreshMode.update).execute();
    }


    @Override
    public void onClickItemBean(TeamListInfo bean, int position) {
//        if (bean.isup()) {
//            bean.setIsup(false);
//        } else {
//            bean.setIsup(true);
//        }
        recycleview.getAdapter().notifyItemChanged(position);
    }

    class Task extends APagingTask<Void, Void, GroupListBase> {
        public Task(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TeamListInfo> parseResult(GroupListBase productPartListBean) {
            return productPartListBean.getBody();
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

            if (beans != null && beans.getBody() != null) {
                beans.setEndPaging(beans.getBody().size() <= 5);
            }
            return beans;
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            error_text.setText(exception.getMessage());
//            if ("noneNetwork".equals(exception.getCode())) {
//                error_image.setImageResource(R.mipmap.no_net);
//            }
        }
    }

    protected GroupListBase queryList(int start) throws TaskException {
        return SDK.newInstance(getActivity()).getProductByCondition(type,themeId, gradeId,desCityId,price,sales,start+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter().unsubscribe(TeamEvent.class, teamEvent);
    }

}
