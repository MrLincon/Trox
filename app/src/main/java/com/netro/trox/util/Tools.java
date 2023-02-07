package com.netro.trox.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.netro.trox.R;

public class Tools {

    public void  setLightStatusBar(View view, Activity activity){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(view.getResources().getColor(R.color.colorWhiteHighEmp));
        }
    }
}
