package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class ActiveOrdersActivity extends AppCompatActivity {

    RelativeLayout cardLayout;
    LinearLayout main;
    ImageView back;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_orders);

        main = findViewById(R.id.main);
        cardLayout = findViewById(R.id.card_layout);
        back = findViewById(R.id.back);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActiveOrdersActivity.this, MainActivity.class));
                finish();
            }
        });

        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActiveOrdersActivity.this, TrackOrdersActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActiveOrdersActivity.this, MainActivity.class));
        finish();
    }
}