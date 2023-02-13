package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.RelativeLayout;

import com.netro.trox.R;

public class ActiveOrdersActivity extends AppCompatActivity {

    RelativeLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_orders);

        cardLayout = findViewById(R.id.card_layout);

        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActiveOrdersActivity.this, TrackOrdersActivity.class));
            }
        });
    }
}