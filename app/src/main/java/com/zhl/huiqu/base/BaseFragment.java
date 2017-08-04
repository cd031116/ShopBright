package com.zhl.huiqu.base;

import android.content.DialogInterface;
import android.view.KeyEvent;

import com.zhl.huiqu.widget.ShowMsgDialog;

import org.aisen.android.ui.fragment.ABaseFragment;

/**
 * Created by lyj on 2017/8/4.
 */

public abstract  class BaseFragment extends ABaseFragment {

    private ShowMsgDialog progressDialog;

    /**
     * 显示加载图标
     * @param txt
     */
    public void showAlert(String txt,final boolean isCancel){
        if(!"".equals(txt)&&txt!=null){
            if(progressDialog==null){
                progressDialog=new ShowMsgDialog(getActivity(),isCancel);
            }
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if(isCancel){

                        }
                    }
                    return false;
                }
            });
            progressDialog.show();
            progressDialog.showText(txt);
        }
    }
    /**
     * 关闭加载图标
     */
    public void dismissAlert(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
