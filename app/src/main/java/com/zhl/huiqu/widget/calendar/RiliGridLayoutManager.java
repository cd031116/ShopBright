package com.zhl.huiqu.widget.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.zhl.huiqu.pull.BaseListAdapter;
import com.zhl.huiqu.pull.FooterSpanSizeLookup;
import com.zhl.huiqu.pull.layoutmanager.ILayoutManager;


/**
 * Created by dw.
 */
public class RiliGridLayoutManager extends GridLayoutManager implements ILayoutManager {

    public RiliGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RiliGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public RiliGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findLastVisiblePosition() {
        return findLastVisibleItemPosition();
    }

    @Override
    public void setUpAdapter(BaseListAdapter adapter) {
        FooterSpanSizeLookup lookup = new FooterSpanSizeLookup(adapter, getSpanCount());
        setSpanSizeLookup(lookup);
    }
}
