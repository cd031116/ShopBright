package com.zhl.huiqu.main.team;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.main.bean.SearchTickEntity;
import com.zhl.huiqu.main.search.SearchListFragment;
import com.zhl.huiqu.main.ticket.TickInfo;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.activity.container.FragmentArgs;
import org.aisen.android.ui.fragment.APagingFragment;
import org.aisen.android.ui.fragment.ARecycleViewSwipeRefreshFragment;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamListSearchFragment extends ARecycleViewSwipeRefreshFragment<TickInfo, SearchTickEntity, Serializable> {


    public static void launch(Activity from, String content) {
        FragmentArgs args = new FragmentArgs();
        args.add("content", content);
        ContainerActivity.launch(from, TeamListSearchFragment.class, args);
    }

    @ViewInject(id = R.id.editSearch)
    EditText editSearch;
    @ViewInject(id = R.id.error_text)
    TextView error_text;
    @ViewInject(id = R.id.error_image)
    ImageView error_image;
    @ViewInject(id = R.id.recycleview)
    RecyclerView recycleview;
    @ViewInject(id = R.id.list_layout)
    RelativeLayout list_layout;
    @ViewInject(id = R.id.scan)
    ImageView scan;

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }

    @Override
    public int inflateContentView() {
        return R.layout.ui_search_list;
    }


    @Override
    public IItemViewCreator<TickInfo> configItemViewCreator() {
        return null;
    }

    @Override
    public void requestData(RefreshMode refreshMode) {

    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        scan.setVisibility(View.GONE);

    }
}
