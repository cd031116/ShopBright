package com.zhl.huiqu.main.ticket;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;

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
    protected int getLayoutId() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_location;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            latitude=bd.getString("latitude");
            longitude=bd.getString("longitude");
            address=bd.getString("address");
        }
        mBaiduMap = bmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        showview();
    }

    @Override
    public void initData() {
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
        LatLng point = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
    //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
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
