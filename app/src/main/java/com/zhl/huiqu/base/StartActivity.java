package com.zhl.huiqu.base;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.zhl.huiqu.MainActivity;
import com.zhl.huiqu.R;
import com.zhl.huiqu.login.LoginActivity;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;

import butterknife.Bind;

/*
*开始页面 lyj
* */
public class StartActivity extends BaseActivity {
    @Bind(R.id.start_bg)
    ImageView start_bg;
    @Override
    protected int getLayoutId(){
        return R.layout.activity_start;
    }

    @Override
    public void initView(){

        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(500);
        contentView.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0){
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation){

            }
            @Override
            public void onAnimationStart(Animation animation){

            }
        });
    }

    @Override
    public void initData()  {
        super.initData();
    }

    private void redirectTo() {
        RegisterEntity info=  SaveObjectUtils.getInstance(StartActivity.this).getObject(Constants.USER_INFO,RegisterEntity.class);
        if(info!=null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
        }else {
            startActivity(new Intent(StartActivity.this,LoginActivity.class));
        }
        StartActivity.this.finish();

    }

}
