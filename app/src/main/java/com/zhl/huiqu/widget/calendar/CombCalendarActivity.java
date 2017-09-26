package com.zhl.huiqu.widget.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.zhl.huiqu.R;
import com.zhl.huiqu.pull.layoutmanager.MyLinearLayoutManager;
import com.zhl.huiqu.widget.calendar.bean.CalendarBean;
import com.zhl.huiqu.widget.calendar.bean.DateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CombCalendarActivity  extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView riliList;
    private CalendarMonthAdapter adapter;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rili_recy_layout);
        initView();
    }

    private void initView() {
        riliList = (RecyclerView) findViewById(R.id.rili_recy);

//        Calendar calendar = Calendar.getInstance();
//        int month = calendar.getTime().getMonth() + 1;
//        year = calendar.getTime().getYear() + 1900;
//        int day = calendar.getTime().getDate();
//        date = year + "-" + month + "-" + day;

        if (TextUtils.isEmpty(date)) {
            this.date = DataUtils.getCurrDate("yyyy-MM-dd");
        }

        List<CalendarBean> CalendarList=new ArrayList<CalendarBean>();
        CalendarBean calendarEntity=new CalendarBean();
        ArrayList<DateEntity> dataArrList= DataUtils.getMonth(date);
        calendarEntity.setDateList(dataArrList);
        calendarEntity.setMonth(DataUtils.formatDate(date, "yyyy-MM"));
        CalendarList.add(calendarEntity);

        for (int i = 0; i <11 ; i++) {
            date = DataUtils.getSomeMonthDay(date, +1);
            CalendarBean calendarEntitys=new CalendarBean();
            ArrayList<DateEntity> dataEntityList= DataUtils.getMonth(date);
            calendarEntitys.setDateList(dataEntityList);
            calendarEntitys.setMonth(DataUtils.formatDate(date, "yyyy-MM"));
            CalendarList.add(calendarEntitys);
        }

        adapter = new CalendarMonthAdapter(this, CalendarList);
        riliList.setLayoutManager(new MyLinearLayoutManager(this));
        riliList.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
    }
}
