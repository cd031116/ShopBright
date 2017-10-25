package com.zhl.huiqu.widget.calendar.bean;

/**
 * Created by dw on 2017/9/21.
 */
public class DateEntity {
    public long million; //时间戳
    public String weekName;  //周几
    public int weekNum;  //一周中第几天，非中式
    public String date; //日期
    public boolean isToday;  //是否今天
    public String day;  //天
    public int luna;  //jiage
    public int childLuna;  //jiage
    public boolean isHasPrice;  //jiage
    public boolean ischeck;
    @Override
    public String toString() {
        return "DateEntity{" +
                "million=" + million +
                ", weekName='" + weekName + '\'' +
                ", weekNum=" + weekNum +
                ", date='" + date + '\'' +
                ", isToday=" + isToday +
                ", day='" + day + '\'' +
                ", luna='" + luna + '\'' +
                '}';
    }


}
