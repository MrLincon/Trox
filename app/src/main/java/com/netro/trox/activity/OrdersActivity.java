package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.netro.trox.R;
import com.netro.trox.adapter.ViewpagerAdapterOffers;
import com.netro.trox.adapter.ViewpagerAdapterOrders;
import com.netro.trox.util.Tools;

public class OrdersActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    TabLayout tabLayout;
    ViewPager viewPager;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        tabLayout.setupWithViewPager(viewPager);

        ViewpagerAdapterOrders viewPagerAdapter = new ViewpagerAdapterOrders(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0, true);

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