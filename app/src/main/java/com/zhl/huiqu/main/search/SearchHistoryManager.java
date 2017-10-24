package com.zhl.huiqu.main.search;

import android.content.Context;

import com.zhl.huiqu.sdk.eventbus.SearchEvent;

import org.aisen.android.component.eventbus.NotificationCenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lyj on 17/2/19.
 */

public class SearchHistoryManager {

    private static final int MAX_COUNT = 10;

    private static final List<SearchHistoryBean> mHistoryList = new ArrayList<>();

    public static void setup(Context context) {
        SearchHistoryDB.setup(context);

        mHistoryList.clear();
        mHistoryList.addAll(SearchHistoryDB.getDB().select(null, SearchHistoryBean.class));
    }

    public static List<SearchHistoryBean> getHistory() {
        return mHistoryList;
    }

    // 最多存10个历史记录
    public static void addHistory(String text) {
        for (int i = 0; i < mHistoryList.size(); i++) {
            SearchHistoryBean bean = mHistoryList.get(i);

            if (text.equals(bean.getText())) {
                mHistoryList.remove(bean);

                break;
            }
        }

        SearchHistoryBean bean = new SearchHistoryBean();
        bean.setText(text);
        mHistoryList.add(0, bean);
        if (mHistoryList.size() > MAX_COUNT) {
            mHistoryList.remove(mHistoryList.size() - 1);
        }
        SearchHistoryDB.getDB().deleteAll(null, SearchHistoryBean.class);
        SearchHistoryDB.getDB().insert(null, mHistoryList);
        NotificationCenter.defaultCenter().publish(new SearchEvent());
    }

    public static void clear() {
        mHistoryList.clear();
        SearchHistoryDB.getDB().deleteAll(null, SearchHistoryBean.class);
        NotificationCenter.defaultCenter().publish(new SearchEvent());
    }

}
