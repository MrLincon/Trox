package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netro.trox.R;

public class EmergencySupportActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_support);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);

        setLightStatusBar(main, this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static void  setLightStatusBar(View view, Activity activity){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(view.getResources().getColor(R.color.colorWhiteHighEmp));
        }
    }
}