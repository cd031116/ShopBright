package com.zhl.huiqu.widget.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;

import java.util.Calendar;

/**
 * Created by dw on 2017/9/23.
 */

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private MyRecylerView riliList;
    private TextView frontMonthTv;
    private TextView nextMonthTv;
    private TextView currentDateTv;
    private MonthAdapter adapter;
    private TextView ok;
    private String date;
    private int year;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rili);
        initView();
    }

    private void initView() {
        riliList = (MyRecylerView) findViewById(R.id.list);
        frontMonthTv = (TextView) findViewById(R.id.front_month);
        frontMonthTv.setOnClickListener(this);
        nextMonthTv = (TextView) findViewById(R.id.next_month);
        nextMonthTv.setOnClickListener(this);
        ok = (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        gestureDetector = new GestureDetector(this, onGestureListener);
        currentDateTv = (TextView) findViewById(R.id.now_month);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.getTime().getMonth() + 1;
        year = calendar.getTime().getYear() + 1900;
        int day = calendar.getTime().getDate();
        this.date = year + "-" + month + "-" + day;

        if (TextUtils.isEmpty(date)) {
            this.date = DataUtils.getCurrDate("yyyy-MM-dd");
        }
        currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
        adapter = new MonthAdapter(this, DataUtils.getMonth(date));
        adapter.setDateString(date);
        adapter.setSelectedPosition(DataUtils.getSelectPosition());
        riliList.setLayoutManager(new RiliGridLayoutManager(this, 7));
        riliList.setAdapter(adapter);
        onitemClick();
        riliList.setGestureDetector(gestureDetector);
    }

    private void onitemClick() {
        adapter.setOnItemClickListener(new MonthAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelectedPosition(position);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == frontMonthTv.getId()) {
            date = DataUtils.getSomeMonthDay(date, -1);
            if (Integer.parseInt(date.substring(0, 4)) != (year - 1)) {
                adapter = new MonthAdapter(this, DataUtils.getMonth(date));
                adapter.setDateString(date);
                Log.e("ddd", "onClick: " + date);
                adapter.notifyDataSetChanged();
                onitemClick();
                riliList.setAdapter(adapter);
                currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
            } else
                date = year + "-" + 1 + "-" + 1;
        } else if (id == nextMonthTv.getId()) {
            date = DataUtils.getSomeMonthDay(date, +1);
            Log.e("ddd", "onClick: " + date.substring(0, 4));
            if (Integer.parseInt(date.substring(0, 4)) != (year + 2)) {
                adapter = new MonthAdapter(this, DataUtils.getMonth(date));
                adapter.setDateString(date);
                Log.e("ddd", "onClick: " + date);
                adapter.notifyDataSetChanged();
                onitemClick();
                riliList.setAdapter(adapter);
                currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
            } else
                date = (year + 1) + "-" + 12 + "-" + 1;
        } else if (id == ok.getId()) {
            if (onItemClick != null) {
                onItemClick.onItemClick(date);
            }
        }
    }


    public OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    /**
     * 点击回调接口
     */
    public interface OnItemClick {
        void onItemClick(String date);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 手势监听是否是左右滑动
     */
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();
                    if (x > 100) {
                        doResult(RIGHT);
                    } else if (x < -100) {
                        doResult(LEFT);
                    }
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return super.onSingleTapUp(e);
                }
            };

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                date = DataUtils.getSomeMonthDay(date, -1);
                if (Integer.parseInt(date.substring(0, 4)) != (year - 1)) {
                    adapter = new MonthAdapter(this, DataUtils.getMonth(date));
                    adapter.setDateString(date);
                    adapter.notifyDataSetChanged();
                    onitemClick();
                    riliList.setAdapter(adapter);
                    currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
                } else
                    date = year + "-" + 1 + "-" + 1;
                break;
            case LEFT:
                date = DataUtils.getSomeMonthDay(date, +1);
                if (Integer.parseInt(date.substring(0, 4)) != (year + 2)) {
                    adapter = new MonthAdapter(this, DataUtils.getMonth(date));
                    adapter.setDateString(date);
                    adapter.notifyDataSetChanged();
                    onitemClick();
                    riliList.setAdapter(adapter);
                    currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
                } else
                    date = (year + 1) + "-" + 12 + "-" + 1;
                break;

        }
    }
}
