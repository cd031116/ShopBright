package com.zhl.huiqu.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseActivity;
import com.zhl.huiqu.widget.TinyWebView;

import butterknife.Bind;
import butterknife.OnClick;

public class WebviewActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView top_title;
    @Bind(R.id.webview)
    TinyWebView webView;

    private String title,content="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        super.initView();
      Bundle bd=getIntent().getExtras();
        if(bd!=null){
            title=bd.getString("title");
            content=bd.getString("content");
        }
        top_title.setText(title);


    }

    @Override
    public void initData() {
        super.initData();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setDefaultFontSize(16);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        content = content.replace("<img", "<img style=\"display:        ;max-width:100%;\"");
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    @OnClick({R.id.top_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                WebviewActivity.this.finish();
                break;

        }
    }



}
