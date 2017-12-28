package com.zhl.huiqu.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;

import butterknife.OnClick;

public class GuiDeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_gui_de;
    }

    @OnClick({R.id.activity_start})
    void onxlik(){
        startActivity(new Intent(GuiDeActivity.this,MainActivity.class));
        GuiDeActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
