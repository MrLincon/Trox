package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class PrivacyPolicyActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    CoordinatorLayout privacy, faq;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        privacy = findViewById(R.id.privacy_policy);
        faq = findViewById(R.id.faq);

        tools = new Tools();

        tools.setLightStatusBar(main,this);


        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacyPolicyActivity.this,PrivacyActivity.class));
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacyPolicyActivity.this,FAQActivity.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}