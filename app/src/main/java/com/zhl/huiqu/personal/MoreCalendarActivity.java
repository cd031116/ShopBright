package com.zhl.huiqu.personal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dw on 2017/8/19. 日历页面
 */

public class MoreCalendarActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {


    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;
    @Bind(R.id.top_title)
    TextView top_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_calendar;
    }

    @Override
    public void initView() {
        super.initView();
        top_title.setText(getResources().getString(R.string.choose_time));
        widget.addDecorator(new PrimeDayDisableDecorator());

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 2, Calendar.DECEMBER, 30);

        widget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Log.e("ttt", "onDateSelected: " + getSelectedDatesString());
        Intent intent = new Intent();
        intent.putExtra("time", getSelectedDatesString());
        setResult(100, intent);
        finish();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return date.getYear()+"-"+(date.getMonth() + 1) + "-" + date.getDay();
    }

    private static class PrimeDayDisableDecorator implements DayViewDecorator {

        Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int month = calendar.getTime().getMonth();
            int year = calendar.getTime().getYear() + 1900;
            int date = calendar.getTime().getDate()+1;
            Log.e("tttt", "shouldDecorate: " + day.getDay() + "--:" + date
                    + "\n--:" + day.getMonth() + "--:" + month
                    + "\n--:" + day.getYear() + "--:" + year);

            if (day.getYear() < year)
                return true;
            else if (day.getYear() == year) {
                if (day.getMonth() < month)
                    return true;
                else if (day.getMonth() == month) {
                    if (day.getDay() < date)
                        return true;
                    else
                        return false;
                } else
                    return false;
            } else
                return false;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }

    @OnClick(R.id.top_left)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
                break;
        }
    }
}
