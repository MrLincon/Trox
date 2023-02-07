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
import com.netro.trox.util.Tools;

public class OffersActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    TabLayout tabLayout;
    ViewPager viewPager;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        tabLayout.setupWithViewPager(viewPager);

        ViewpagerAdapterOffers viewPagerAdapter = new ViewpagerAdapterOffers(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0, true);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}