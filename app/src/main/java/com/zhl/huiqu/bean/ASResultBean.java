package com.zhl.huiqu.bean;

import com.zhl.huiqu.base.BaseBean;
import com.zhl.huiqu.base.BaseInfo;

import org.aisen.android.network.biz.IResult;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ASResultBean extends BaseInfo implements IResult {
    private static final long serialVersionUID = -6216660700053567496L;

    private boolean outofdate;
    private boolean fromCache;
    private boolean endPaging;
    private String[] pagingIndex;

    public ASResultBean() {
    }

    public boolean outofdate() {
        return this.isOutofdate();
    }

    public boolean fromCache() {
        return this.isFromCache();
    }

    public boolean endPaging() {
        return this.isEndPaging();
    }

    public String[] pagingIndex() {
        return this.getPagingIndex();
    }

    public boolean isOutofdate() {
        return this.outofdate;
    }

    public void setOutofdate(boolean outofdate) {
        this.outofdate = outofdate;
    }

    public boolean isFromCache() {
        return this.fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    public boolean isEndPaging() {
        return this.endPaging;
    }

    public void setEndPaging(boolean endPaging) {
        this.endPaging = endPaging;
    }

    public String[] getPagingIndex() {
        return this.pagingIndex;
    }

    public void setPagingIndex(String[] pagingIndex) {
        this.pagingIndex = pagingIndex;
    }
}
