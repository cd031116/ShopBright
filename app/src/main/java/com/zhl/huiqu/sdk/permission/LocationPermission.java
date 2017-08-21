package com.zhl.huiqu.sdk.permission;

import android.Manifest;
import android.app.Activity;

import org.aisen.android.support.action.IAction;
import org.aisen.android.support.permissions.APermissionGroupAction;
import org.aisen.android.support.permissions.APermissionsAction;
import org.aisen.android.support.permissions.IPermissionsSubject;
import org.aisen.android.ui.activity.basic.BaseActivity;

/**
 * Created by lyj on 2017/8/21.
 */

public class LocationPermission extends APermissionGroupAction {
    String[] permissio={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    public LocationPermission(Activity context, IAction parent, IPermissionsSubject subject, String[] permissions) {
        super(context, parent, subject,permissions);
    }

    @Override
    protected void onPermissionDenied(String[] permissions, int[] grantResults, boolean[] alwaysDenied) {
        super.onPermissionDenied(permissions, grantResults, alwaysDenied);




    }

    //    public LocationPermission(BaseActivity context, IAction parent) {
//        super(context, parent, context.getActivityHelper(), Manifest.permission.CAMERA);
//    }



}
