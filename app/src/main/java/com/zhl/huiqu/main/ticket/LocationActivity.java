package com.zhl.huiqu.main.ticket;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.utils.ToastUtils;
import com.zhl.huiqu.widget.ShowMsgDialog;

import butterknife.Bind;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.bmapView)
    MapView bmapView;
    BaiduMap mBaiduMap;
    private String latitude;
    private String longitude;
    private String address;

    @Override
    protected int getLayoutId(){

        return R.layout.activity_location;
    }

    @Override
    public void initView(){
        super.initView();
        SDKInitializer.getCoordType();
        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            latitude=bd.getString("latitude");
            longitude=bd.getString("longitude");
            address=bd.getString("address");
        }
        mBaiduMap = bmapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).build()));
        showview();
    }

    @Override
    public void initData(){
        super.initData();
        top_title.setText(address);
    }

    @OnClick({R.id.top_left, R.id.top_image})
    void onClicked(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                LocationActivity.this.finish();
                break;
        }
    }

    private void showview() {
        if(TextUtils.isEmpty(latitude)||TextUtils.isEmpty(longitude)){

            ToastUtils.showShortToast(LocationActivity.this,"未获取到该地方的经纬度");
            return;
        }
        LatLng point = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
    //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_openmap_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);//使输入的点位于地图中心
        mBaiduMap.setMapStatus(u);
    }
    @Override
    public void onPause() {
        bmapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        bmapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        bmapView.onDestroy();
        bmapView = null;
        super.onDestroy();
    }
}
