package com.zhl.huiqu.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.zhl.huiqu.R;

import org.aisen.android.ui.activity.container.FragmentArgs;
import org.aisen.android.ui.fragment.ABaseFragment;

import java.lang.reflect.Method;


/**
 * Created by lyj on 17/8/2.  fragment启动类
 */

public class ContainerActivity extends BaseActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_CONTAINER";

    private int contentId;
    private int overrideTheme = -1;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launchForResult(Fragment fragment, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        if (fragment.getActivity() == null)
            return;
        Activity activity = fragment.getActivity();
        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launchForResult(Activity from, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        Intent intent = new Intent(from, ContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        from.startActivityForResult(intent, requestCode);
    }

    protected int defaultLayoutId() {
        return R.layout.ui_fragment_container;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contentId = savedInstanceState == null ? defaultLayoutId()
                : savedInstanceState.getInt("contentId");
        overrideTheme = savedInstanceState == null ? -1
                : savedInstanceState.getInt("overrideTheme");
        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                String className = getIntent().getStringExtra("className");
                if (TextUtils.isEmpty(className)) {
                    super.onCreate(savedInstanceState);
                    finish();
                    return;
                }
                FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra("args");
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null){
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[] { Bundle.class });
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (Exception e){
                    }
                }
                // 重写Activity的主题
                try {
                    Method method = clazz.getMethod("setActivityTheme");
                    if (method != null){
                        int theme = Integer.parseInt(method.invoke(fragment).toString());
                        if (theme > 0){
                            overrideTheme = theme;
                        }
                    }
                } catch (Exception e){
                }
                // 重写Activity的contentView
                try {
                    Method method = clazz.getMethod("inflateActivityContentView");
                    if (method != null){
                        int fragmentConfigId = Integer.parseInt(method.invoke(fragment).toString());
                        if (fragmentConfigId >0){
                            contentId = fragmentConfigId;
                        }
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.onCreate(savedInstanceState);
                finish();
                return;
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(contentId);

        if (fragment != null) {
            if (!(fragment instanceof ABaseFragment) || ((ABaseFragment) fragment).inflateContentView() > 0) {
                getFragmentManager().beginTransaction().add(org.aisen.android.R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
            }
            else {
                getFragmentManager().beginTransaction().add(fragment, FRAGMENT_TAG).commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("contentId", contentId);
        outState.putInt("overrideTheme", overrideTheme);
    }

    private Handler mHandler = new Handler();

    private Runnable dismissStatusBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideSystemUI();
            }
        }

    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mHandler.removeCallbacks(dismissStatusBarRunnable);
            mHandler.postDelayed(dismissStatusBarRunnable, 3000);
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
        }
        else {
            showSystemUI();

        }
    }
    // This snippet hides the system bars.
    @SuppressLint("NewApi")
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    @SuppressLint("NewApi")
    private void showSystemUI() {
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(0);
    }
}
