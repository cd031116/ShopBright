package com.zhl.huiqu.main.team;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.popupWindow.DetailWindow;
import com.zhl.huiqu.main.popupWindow.SelectTourWindow;
import com.zhl.huiqu.main.team.bean.InsuranData;
import com.zhl.huiqu.main.team.bean.InsuranceBase;
import com.zhl.huiqu.main.team.bean.OrderCountBase;
import com.zhl.huiqu.main.team.bean.OrderPut;
import com.zhl.huiqu.main.team.bean.TeamBase;
import com.zhl.huiqu.main.team.bean.TeamOrderPriceBean;
import com.zhl.huiqu.main.ticket.TixkSearchActivity;
import com.zhl.huiqu.personal.ChooseTourerActivity;
import com.zhl.huiqu.personal.bean.UsePerList;
import com.zhl.huiqu.recyclebase.CommonAdapter;
import com.zhl.huiqu.recyclebase.ViewHolder;
import com.zhl.huiqu.sdk.SDK;
import com.zhl.huiqu.sdk.eventbus.TickSearchEvent;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.utils.isIdNum;
import com.zhl.huiqu.widget.SimpleDividerItemDecoration;

import org.aisen.android.component.eventbus.NotificationCenter;
import org.aisen.android.network.task.TaskException;
import org.aisen.android.network.task.WorkTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamOrderActivity extends BaseActivity {

    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.outing_time)
    TextView outing_time;
    @Bind(R.id.outing_num)
    TextView outing_num;
    @Bind(R.id.b_list)
    RecyclerView b_list;
    @Bind(R.id.out_list)
    RecyclerView out_list;
    @Bind(R.id.commit_pay)
    TextView commit_pay;
    @Bind(R.id.hetong_choose)
    CheckBox hetong_choose;
    @Bind(R.id.order_pay_price)
    TextView order_pay_price;
    @Bind(R.id.room_charge_price)
    TextView room_charge_price;
    @Bind(R.id.room_charge_layout)
    RelativeLayout room_charge_layout;

    @Bind(R.id.name_text)
    TextView name_text;//取票人姓名
    @Bind(R.id.phone_text)
    TextView phone_text;//取票人号码
    @Bind(R.id.idcard_text)
    TextView idcard_text;//取票人身份证
    @Bind(R.id.order_pay_layout)
    RelativeLayout bottom_r;//取票人身份证

    private DetailWindow mopupWindow;
    private boolean isshow = false;
    private CommonAdapter<InsuranData> mAdapter;
    private List<InsuranData> mList = new ArrayList<>();
    private CommonAdapter<UsePerList> oAdapter;
    private List<UsePerList> oList = new ArrayList<>();
    private String productId = "";
    private String departDate = "";
    private String childTicketPrice = "";
    private String childCount = "0";
    private String adultTicketPrice = "";
    private String adultCount = "0";
    private String roomChargePrice = "";
    private TeamOrderPriceBean teamOrderPriceBean;
    private String insuranceId = "";
    private String orderCount = "";
    private int REQUEST_CODE = 101;
    private RegisterEntity account;
    private String insuranprice = "";
    private String roomPrice="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_order;
    }

    @Override
    public void initView() {
        super.initView();
        account = SaveObjectUtils.getInstance(TeamOrderActivity.this).getObject(Constants.USER_INFO, RegisterEntity.class);
        teamOrderPriceBean = (TeamOrderPriceBean) getIntent().getSerializableExtra("t_order_price");
        if (!TextUtils.isEmpty(teamOrderPriceBean.getProductTitle())) {
            desc.setText(teamOrderPriceBean.getProductTitle());
        }

        outing_time.setText(teamOrderPriceBean.getProductTime());
        if (teamOrderPriceBean.getProductChildNum() != 0)
            outing_num.setText("成人：" + teamOrderPriceBean.getProductAdultNum() + "人,儿童:" + teamOrderPriceBean.getProductChildNum() + "人");
        else
            outing_num.setText("成人：" + teamOrderPriceBean.getProductAdultNum() + "人");
        if (teamOrderPriceBean.getProductAdultNum() % 2 != 0) {
            roomPrice=teamOrderPriceBean.getRoomChargeprice()+"";
            room_charge_layout.setVisibility(View.VISIBLE);
            room_charge_price.setText("￥" + teamOrderPriceBean.getRoomChargeprice());
        } else
            room_charge_layout.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();
        setadapter();
        setOutadapter();
        if (teamOrderPriceBean != null) {
            productId = teamOrderPriceBean.getProductId();
            departDate = teamOrderPriceBean.getProductTime();
            childTicketPrice = teamOrderPriceBean.getProductChildPrice() + "";
            childCount = teamOrderPriceBean.getProductChildNum() + "";
            adultTicketPrice = teamOrderPriceBean.getProductAdultPrice() + "";
            adultCount = teamOrderPriceBean.getProductAdultNum() + "";
            roomChargePrice = teamOrderPriceBean.getRoomChargeprice() + "";
            new getListTask().execute();
        }
    }


    @OnClick({R.id.top_main, R.id.commit_pay, R.id.outing_people, R.id.detail,R.id.order_pay_arrow})
    void onclicj(View v) {
        switch (v.getId()) {
            case R.id.top_main:
                TeamOrderActivity.this.finish();
                break;
            case R.id.commit_pay:
                if (hetong_choose.isSelected()) {

                } else {
                    String name_text_t = name_text.getText().toString();
                    String phone_text_t = phone_text.getText().toString();
                    String idcard_text_t = idcard_text.getText().toString();
                    if (TextUtils.isEmpty(name_text_t)) {
                        ToastUtils.showShortToast(TeamOrderActivity.this, "请输入取票人姓名");
                        return;
                    }
                    if (TextUtils.isEmpty(phone_text_t)) {
                        ToastUtils.showShortToast(TeamOrderActivity.this, "请输入取票人电话号码");
                        return;
                    }
                    if (TextUtils.isEmpty(idcard_text_t)) {
                        ToastUtils.showShortToast(TeamOrderActivity.this, "请输入取票人身份证号码");
                        return;
                    }
                    if (!isIdNum.isIdNum(idcard_text_t)) {
                        ToastUtils.showShortToast(TeamOrderActivity.this, "身份证号码格式不正确");
                        return;
                    }
                    if (oList.size() <= 0) {
                        ToastUtils.showShortToast(TeamOrderActivity.this, "请选择出游人");
                        return;
                    }

                    OrderPut info = new OrderPut();
                    info.setProductId(productId);
                    info.setOrderCount(order_pay_price.getText().toString());
                    info.setAdultCount(adultCount);
                    info.setAdultTicketPrice(adultTicketPrice);
                    info.setChildCount(childCount);
                    info.setChildTicketPrice(childTicketPrice);
                    info.setContactIds(getchuyou());
                    info.setDepartDate(departDate);
                    info.setGetTicketCard(idcard_text_t);
                    info.setGetTicketMobile(phone_text_t);
                    info.setGetTicketName(name_text_t);
                    info.setRoomChargePrice(roomChargePrice);
                    info.setInsuranceIdList(getbaoxian());
                    info.setInsurancePriceCount(getbaoprice()*((Integer.parseInt(adultCount)+Integer.parseInt(childCount))) + "");
                    info.setMemberId(account.getBody().getMember_id());
                    Intent mIntent = new Intent(this, SignTeamActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("info", info);
                    mIntent.putExtras(mBundle);

                    startActivity(mIntent);
                }
                break;
            case R.id.outing_people:
                int total = 0;
                if (!TextUtils.isEmpty(adultCount)) {
                    total = total + Integer.parseInt(adultCount);
                }
                if (!TextUtils.isEmpty(childCount)) {
                    total = total + Integer.parseInt(childCount);
                }
                if (oList.size() >= total) {
                    Toast.makeText(TeamOrderActivity.this, "已选" + total + "人", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("olist", (Serializable) oList);
                intent.setClass(TeamOrderActivity.this, ChooseTourerActivity.class);
                intent.putExtras(bundle);
                if (TextUtils.isEmpty(adultCount)) {
                    intent.putExtra("adultCount", 0);
                } else {
                    intent.putExtra("adultCount", adultCount);
                }
                if (TextUtils.isEmpty(childCount)) {
                    intent.putExtra("childCount", 0);
                } else {
                    intent.putExtra("childCount", childCount);
                }
                intent.putExtra("chose", 1);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.detail:
            case R.id.order_pay_arrow:
                if (!isshow) {
                    mopupWindow = new DetailWindow(TeamOrderActivity.this, adultTicketPrice, adultCount, childTicketPrice, childCount, insuranprice, order_pay_price.getText().toString(),roomPrice, itemsOnClick);
                    mopupWindow.showAtLocation(TeamOrderActivity.this.findViewById(R.id.detail),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                    mopupWindow.showUp2(bottom_r, 1080, 142);
                    isshow = true;
                } else {
                    mopupWindow.dismiss();
                    isshow = false;
                }
                break;
        }
    }

    private DetailWindow.ItemInclick itemsOnClick = new DetailWindow.ItemInclick() {
        @Override
        public void ItemClick() {
            commit_pay.performClick();
        }
    };

    private void setadapter() {
        mAdapter = new CommonAdapter<InsuranData>(this, R.layout.team_insuran_item, mList) {
            @Override
            protected void convert(ViewHolder holder, final InsuranData info, int position) {
                holder.setText(R.id.baoxian_name, info.getRealType());
                holder.setText(R.id.baoxian_price, info.getPrice());
                holder.setText(R.id.n_text, info.getResName());
                if (info.ischeck()) {
                    holder.setSesect(R.id.baoxian_choose, true);
                } else {
                    holder.setSesect(R.id.baoxian_choose, false);
                }

                holder.setOnClickListener(R.id.main_line, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (info.ischeck()) {
                            info.setIscheck(false);
                        } else {
                            info.setIscheck(true);
                        }
                        insuranceId = info.getResId();
                        mAdapter.notifyDataSetChanged();
                        new getPriceTask().execute();
                    }
                });
            }
        };
        b_list.setLayoutManager(new LinearLayoutManager(TeamOrderActivity.this));
        b_list.setAdapter(mAdapter);
        b_list.setNestedScrollingEnabled(false);
    }


    private String getchuyou() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < oList.size(); i++) {
            if (oList.get(i).isCheck()) {
                sb.append(oList.get(i).getContact_id() + ",");
            }
        }
        return sb.substring(0, sb.toString().length() - 1);
    }

    private String getbaoxian() {
        if (mList.size() <= 0) {
            return "";
        }


        Log.i("hhhh", "mList=" + mList.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).ischeck()) {
                sb.append(mList.get(i).getResId() + ",");
            }
        }
        if (sb.toString().length() <= 0) {
            return "";
        }

        return sb.substring(0, sb.toString().length() - 1);
    }

    private float getbaoprice() {
        if (mList.size() <= 0) {
            return 0;
        }
        float price = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).ischeck()) {
                price = price + Float.valueOf(mList.get(i).getPrice());
            }
        }
        if (price <= 0) {
            return 0;
        }
        return price;
    }


    private void setOutadapter() {
        oAdapter = new CommonAdapter<UsePerList>(this, R.layout.team_order_item, oList) {
            @Override
            protected void convert(ViewHolder holder, final UsePerList info, final int position) {
                if ("1".equals(info.getTypes())) {
                    holder.setText(R.id.type, "成人");
                } else {
                    holder.setText(R.id.type, "儿童");
                }
                holder.setText(R.id.id_card, "姓名:" + info.getName());

                holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oList.remove(position);
                        oAdapter.notifyDataSetChanged();
                    }
                });

            }
        };
        out_list.setLayoutManager(new LinearLayoutManager(TeamOrderActivity.this));
        out_list.setAdapter(oAdapter);
        out_list.setNestedScrollingEnabled(false);
    }


    /*
     * */
    class getListTask extends WorkTask<Void, Void, InsuranceBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载...", true);
        }

        @Override
        public InsuranceBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamOrderActivity.this).getInsuranceInfo(productId, departDate, childTicketPrice, childCount, adultTicketPrice, adultCount, roomChargePrice);
        }

        @Override
        protected void onSuccess(InsuranceBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody().getInsuranceInfo() != null) {
                mList.addAll(info.getBody().getInsuranceInfo());
                mAdapter.notifyDataSetChanged();
                orderCount = info.getBody().getOrderCount();
            }
            order_pay_price.setText("" + orderCount);
        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    /*
       * */
    class getPriceTask extends WorkTask<Void, Void, OrderCountBase> {
        @Override
        protected void onPrepare() {
            super.onPrepare();
            showAlert("正在加载...", true);
        }

        @Override
        public OrderCountBase workInBackground(Void... voids) throws TaskException {
            return SDK.newInstance(TeamOrderActivity.this).changeOrderCount(orderCount, insuranceId, childTicketPrice, childCount, adultTicketPrice, adultCount, roomChargePrice);
        }

        @Override
        protected void onSuccess(OrderCountBase info) {
            super.onSuccess(info);
            dismissAlert();
            if (info.getBody() != null) {
                order_pay_price.setText("" + info.getBody().getOrderCount());
                insuranprice = info.getBody().getInsuranceInfo().getInsuranceCount();
            }

        }

        @Override
        protected void onFailure(TaskException exception) {
            dismissAlert();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                oList.addAll((ArrayList<UsePerList>) data.getSerializableExtra("plist"));
                removeDuplicateUser();
                oAdapter.notifyDataSetChanged();
            }
        }
    }

    private void removeDuplicateUser() {
        for (int i = 0; i < oList.size() - 1; i++) {
            for (int j = oList.size() - 1; j > i; j--) {
                if (oList.get(j).getContact_id().equals(oList.get(i).getContact_id())) {
                    oList.remove(j);
                }
            }
        }
    }


}
