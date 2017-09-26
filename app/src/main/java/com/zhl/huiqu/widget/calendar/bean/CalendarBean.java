package com.zhl.huiqu.widget.calendar.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CalendarBean {
    private String month;
    private ArrayList<DateEntity> dateList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ArrayList<DateEntity> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<DateEntity> dateList) {
        this.dateList = dateList;
    }
}
