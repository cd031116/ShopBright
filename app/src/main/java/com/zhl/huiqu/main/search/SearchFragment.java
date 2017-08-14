package com.zhl.huiqu.main.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;


import com.zhl.huiqu.R;
import com.zhl.huiqu.base.ContainerActivity;
import com.zhl.huiqu.scan.CaptureActivity;
import com.zhl.huiqu.sdk.eventbus.ISearchSubscriber;
import com.zhl.huiqu.sdk.eventbus.SearchEvent;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.widget.TagCloudView;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.support.inject.OnClick;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.ABaseFragment;
import org.aisen.android.ui.fragment.AGridFragment;
import org.aisen.android.ui.fragment.ARecycleViewFragment;
import org.aisen.android.ui.fragment.itemview.IITemView;
import org.aisen.android.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 17/2/19.
 */

public class SearchFragment extends ABaseFragment  implements ISearchSubscriber {

    public static void launch(Activity from) {
        ContainerActivity.launch(from, SearchFragment.class, null);
    }

    // BJ-北京 GZ-广州 SZ-深圳 SH-上海 HZ-杭州
    private static final String[][] SCHOOL_TAGS = new String[][]{
            { "BJ", "北京" },
            { "GZ", "广州" },
            { "SZ", "深圳" },
            { "SH", "上海" },
            { "HZ", "杭州" }
    };

    @ViewInject(id = R.id.layHistory)
    View layHistory;
    @ViewInject(id = R.id.tagsHistory)
    TagCloudView tagsHisgory;
    @ViewInject(id = R.id.tagsSchool)
    TagCloudView tagsSchool;
    @ViewInject(id = R.id.editSearch)
    EditText editSearch;
    @ViewInject(id = R.id.text_school)
    TextView text_school;
    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity());
        SupportMultipleScreensUtil.scale(view);
    }
    @Override
    public int inflateContentView() {
        return R.layout.ui_search;
    }

    @OnClick({R.id.btnBack,R.id.line_back,R.id.scan})
    void onBtnBackClicked(View v) {
        switch (v.getId()){
            case R.id.btnBack:
            case R.id.line_back:
                getActivity().finish();
                break;
            case R.id.scan:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
        }
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);


        List<String> tags = new ArrayList<>();
        for (int i = 0; i < SCHOOL_TAGS.length; i++) {
            tags.add(SCHOOL_TAGS[i][1]);
        }
        tagsSchool.setTags(tags);
        tagsSchool.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
//                SchoolSearchResultFragment.launch(getActivity(), SCHOOL_TAGS[position][1], SCHOOL_TAGS[position][0]);
//                Intent intent=new Intent(getActivity(),SchoolSearchActivity.class);
//                intent.putExtra("code", SCHOOL_TAGS[position][0]);
//                intent.putExtra("title", SCHOOL_TAGS[position][1]);
//                startActivity(intent);
            }
        });
        //热门
        tagsHisgory.setOnTagClickListener(new TagCloudView.OnTagClickListener() {

            @Override
            public void onTagClick(int position) {
                List<SearchHistoryBean> list = SearchHistoryManager.getHistory();

//                InputSearchResultFragment.launch(getActivity(), list.get(position).getText());
            }

        });


    }


    @OnClick(R.id.txtClearHistory)
    void onClearHistory(View view) {
        SearchHistoryManager.clear();
    }

    @OnClick(R.id.go_search)
    void onSearchClicked(View view) {
        String text = editSearch.getText().toString();

        if (!TextUtils.isEmpty(text)) {
            SearchHistoryManager.addHistory(text);

//            InputSearchResultFragment.launch(getActivity(), text);
        }
    }

    private void onSearchItemClicked(String text) {
        SearchHistoryManager.addHistory(text);
    }



    @Override
    public void onResume() {
        super.onResume();
        NotificationCenter.defaultCenter().subscriber(SearchEvent.class, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationCenter.defaultCenter().unsubscribe(SearchEvent.class, this);
    }

    @Override
    public void onEvent(SearchEvent searchEvent){
        List<SearchHistoryBean> list = SearchHistoryManager.getHistory();
        if (list.size() == 0){
            layHistory.setVisibility(View.GONE);
        }
        else {
            layHistory.setVisibility(View.VISIBLE);
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < list.size(); i++){
                tags.add(list.get(i).getText());
            }
            tagsHisgory.setTags(tags);
        }
    }

}
