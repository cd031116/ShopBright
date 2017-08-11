package com.zhl.huiqu.personal;

import android.view.ViewGroup;

import com.zhl.huiqu.base.BaseFragment;
import com.zhl.huiqu.main.MainTabFragment;
import com.zhl.huiqu.utils.SupportMultipleScreensUtil;

/**
 * Created by Administrator on 2017/8/11.
 */

public class PersonalFragment extends BaseFragment{

    public static PersonalFragment newInstance(){
        return new PersonalFragment();
    }
    @Override
    public int inflateContentView() {
        return 0;
    }

    @Override
    public void setContentView(ViewGroup view) {
        super.setContentView(view);
        SupportMultipleScreensUtil.init(getActivity().getApplication());
        SupportMultipleScreensUtil.scale(view);
    }
}
