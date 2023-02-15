package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class PrivacyActivity extends AppCompatActivity {

    ImageView back;

    LinearLayout main;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        back = findViewById(R.id.back);
        main = findViewById(R.id.main);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}