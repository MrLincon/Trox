package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.netro.trox.R;
import com.netro.trox.fragment.internationalOrder.FragmentIntSendParcel;
import com.netro.trox.util.Tools;

public class InternationalOrderDetailsActivity extends AppCompatActivity {

    LinearLayout main;


    String type;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international_order_details);

        main = findViewById(R.id.main);

        type = getIntent().getStringExtra("type");

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentIntSendParcel());
        fragmentTransaction.commit();
    }

}