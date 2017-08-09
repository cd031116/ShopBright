package com.zhl.huiqu.base;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.zhl.huiqu.R;
import com.zhl.huiqu.login.LoginFragment;

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
         BaseConfig bg=BaseConfig.getInstance(this);
        LoginFragment.launch(this);
        StartActivity.this.finish();
    }

}
