package com.zhl.huiqu.widget.calendar;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.team.TeamOrderActivity;
import com.zhl.huiqu.main.team.bean.TeamOrderPriceBean;
import com.zhl.huiqu.main.team.bean.TeamPriceBean;
import com.zhl.huiqu.pull.layoutmanager.MyGridLayoutManager;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.calendar.bean.DateEntity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private MyRecylerView riliList;
    private TextView frontMonthTv, nextMonthTv, currentDateTv, child_price, normal_num_btn, normal_price, child_num_btn, ok;
    private LinearLayout normal_down_btn, normal_add_btn, child_down_btn, child_add_btn;
    private MonthAdapter adapter;
    public String date;
    final int RIGHT = 0;
    final int LEFT = 1;
    private GestureDetector gestureDetector;
    private int year;
    private List<TeamPriceBean> body;
    private TeamOrderPriceBean teamOrderPriceBean;
    private String spot_team_id;
    private int adultPrice, childPrice;
    private int adultNum, childNum;
    private int roomChargeprice;
    private String outingDate;
    private String title;
    private RegisterEntity account;
    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rili);
        View rootView = findViewById(android.R.id.content);
        SupportMultipleScreensUtil.init(getApplication());
        SupportMultipleScreensUtil.scale(rootView);
        body = (List<TeamPriceBean>) getIntent().getSerializableExtra("body");
        spot_team_id = getIntent().getStringExtra("spot_team_id");
        title = getIntent().getStringExtra("team_title");
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
        teamOrderPriceBean = new TeamOrderPriceBean();
        initView();
    }


    private void initView() {
        riliList = (MyRecylerView) findViewById(R.id.list);
        child_price = (TextView) findViewById(R.id.child_price);
        normal_num_btn = (TextView) findViewById(R.id.normal_num_btn);
        normal_price = (TextView) findViewById(R.id.normal_price);
        child_num_btn = (TextView) findViewById(R.id.child_num_btn);
        normal_down_btn = (LinearLayout) findViewById(R.id.normal_down_btn);
        normal_add_btn = (LinearLayout) findViewById(R.id.normal_add_btn);
        child_down_btn = (LinearLayout) findViewById(R.id.child_down_btn);
        child_add_btn = (LinearLayout) findViewById(R.id.child_add_btn);
        btnBack= (ImageView) findViewById(R.id.btnBack);
        normal_down_btn.setOnClickListener(this);
        normal_add_btn.setOnClickListener(this);
        child_down_btn.setOnClickListener(this);
        child_add_btn.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        normal_num_btn.setText(adultNum + "");
        child_num_btn.setText(childNum + "");

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
        adapter = new MonthAdapter(this, DataUtils.getMonth(date, body));
        adapter.setDateString(date);
//        adapter.setSelectedPosition(DataUtils.getSelectPosition());
        riliList.setLayoutManager(new RiliGridLayoutManager(this, 7));
        riliList.setAdapter(adapter);
        onitemClick();
        riliList.setGestureDetector(gestureDetector);
    }

    private void onitemClick() {
        adapter.setOnItemClickListener(new MonthAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<DateEntity> dataList) {
                Log.e("sss", "onItemClick: " + position);

                if (dataList.get(position).ischeck) {

                } else {
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).ischeck = false;
                    }
                    dataList.get(position).ischeck = true;
                    adultPrice = dataList.get(position).luna;
                    outingDate = dataList.get(position).date;
                    childPrice = dataList.get(position).childLuna;
                    roomChargeprice = dataList.get(position).roomChargeprice;
                    normal_price.setText("￥" + adultPrice);
                    child_price.setText("￥" + childPrice);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == frontMonthTv.getId()) {
            date = DataUtils.getSomeMonthDay(date, -1);
            if (Integer.parseInt(date.substring(0, 4)) != (year - 1)) {
                adapter = new MonthAdapter(this, DataUtils.getMonth(date, body));
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
                adapter = new MonthAdapter(this, DataUtils.getMonth(date, body));
                adapter.setDateString(date);
                Log.e("ddd", "onClick: " + date);
                adapter.notifyDataSetChanged();
                onitemClick();
                riliList.setAdapter(adapter);
                currentDateTv.setText(DataUtils.formatDate(date, "yyyy-MM"));
            } else
                date = (year + 1) + "-" + 12 + "-" + 1;
        }
        switch (id) {
            case R.id.normal_down_btn:
                if (adultNum > 1)
                    adultNum--;
                normal_num_btn.setText(adultNum + "");
                break;
            case R.id.normal_add_btn:
                adultNum++;
                normal_num_btn.setText(adultNum + "");
                break;
            case R.id.child_down_btn:
                if (childNum > 0)
                    childNum--;
                child_num_btn.setText(childNum + "");
                break;
            case R.id.child_add_btn:
                childNum++;
                child_num_btn.setText(childNum + "");
                break;
            case R.id.btnBack:
                CalendarActivity.this.finish();
                break;
            case R.id.ok:
                if (account != null) {
                    if (adultPrice != 0) {
                        if (adultNum > 0) {
                            nextStep();
                        } else
                            ToastUtils.showShortToast(CalendarActivity.this, "请选择出游人数");
                    } else
                        ToastUtils.showShortToast(CalendarActivity.this, "请选择出游日期");
                } else
                    startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        account = SaveObjectUtils.getInstance(this).getObject(Constants.USER_INFO, RegisterEntity.class);
    }

    private void nextStep() {
        teamOrderPriceBean.setProductId(spot_team_id);
        teamOrderPriceBean.setProductAdultPrice(adultPrice);
        teamOrderPriceBean.setProductAdultNum(adultNum);
        teamOrderPriceBean.setProductChildNum(childNum);
        teamOrderPriceBean.setProductChildPrice(childPrice);
        teamOrderPriceBean.setProductTime(outingDate);
        teamOrderPriceBean.setProductTitle(title);
        teamOrderPriceBean.setRoomChargeprice(roomChargeprice);
        Intent intent = new Intent(CalendarActivity.this, TeamOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("t_order_price", teamOrderPriceBean);
        intent.putExtras(bundle);
        startActivity(intent);
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
                Log.e("ttt", "doResult: " + date);
                adapter = new MonthAdapter(this, DataUtils.getMonth(date, body));
                adapter.setDateString(date);
                adapter.notifyDataSetChanged();
                onitemClick();
                riliList.setAdapter(adapter);
//                adapter.setSelectedPosition(DataUtils.getSelectPosition());
                currentDateTv.setText("当前月份：" + DataUtils.formatDate(date, "yyyy-MM"));
                break;
            case LEFT:
                date = DataUtils.getSomeMonthDay(date, +1);
                if (Integer.parseInt(date.substring(0, 4)) != (year + 2)) {
                    adapter = new MonthAdapter(this, DataUtils.getMonth(date, body));
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
